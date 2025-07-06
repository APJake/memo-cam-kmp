package com.logixowl.memocam.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.Image
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.view.WindowManager
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.logixowl.memocam.core.AppConstants
import com.logixowl.memocam.core.AppLogger
import com.logixowl.memocam.features.memo.take_picture.CameraFacing
import com.logixowl.memocam.features.memo.take_picture.FlashMode
import java.io.File
import java.io.FileOutputStream

@SuppressLint("MissingPermission")
@Composable
actual fun CameraPreviewNative(
    modifier: Modifier,
    flashMode: FlashMode,
    cameraFacing: CameraFacing,
    onCameraReady: () -> Unit,
    onImageCaptured: (String) -> Unit,
    onError: (String) -> Unit,
    captureImage: Boolean
) {
    val context = LocalContext.current
    var cameraDevice by remember { mutableStateOf<CameraDevice?>(null) }
    var captureSession by remember { mutableStateOf<CameraCaptureSession?>(null) }
    var imageReader by remember { mutableStateOf<ImageReader?>(null) }
    var backgroundThread by remember { mutableStateOf<HandlerThread?>(null) }
    var backgroundHandler by remember { mutableStateOf<Handler?>(null) }
    var textureView by remember { mutableStateOf<TextureView?>(null) }
    var cameraCharacteristics by remember { mutableStateOf<CameraCharacteristics?>(null) }

    val cameraManager =
        remember { context.getSystemService(Context.CAMERA_SERVICE) as CameraManager }

    // Initialize background thread
    LaunchedEffect(Unit) {
        backgroundThread = HandlerThread("CameraBackground").apply { start() }
        backgroundHandler = Handler(backgroundThread!!.looper)
    }

    // Camera initialization
    LaunchedEffect(cameraFacing) {
        try {
            val cameraId = getCameraId(cameraManager, cameraFacing)
            if (cameraId != null) {
                cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
                openCamera(cameraManager, cameraId, backgroundHandler!!) { device ->
                    cameraDevice = device
                    onCameraReady()
                }
            }
        } catch (e: Exception) {
            onError("Failed to initialize camera: ${e.message}")
        }
    }

    // Setup capture session when camera is ready
    LaunchedEffect(cameraDevice, textureView, cameraCharacteristics) {
        if (cameraDevice != null && textureView != null && cameraCharacteristics != null) {
            setupCaptureSession(
                cameraDevice!!,
                textureView!!,
                cameraCharacteristics!!,
                context,
                backgroundHandler!!,
                onSessionReady = { session, reader ->
                    captureSession = session
                    imageReader = reader
                },
                onError = onError
            )
        }
    }

    // Handle image capture
    LaunchedEffect(captureImage) {
        if (captureImage && captureSession != null && imageReader != null && cameraCharacteristics != null) {
            captureStillPicture(
                captureSession!!,
                imageReader!!,
                cameraCharacteristics!!,
                backgroundHandler!!,
                flashMode,
                context,
                onImageCaptured,
                onError
            )
        }
    }

    // Cleanup
    DisposableEffect(Unit) {
        onDispose {
            captureSession?.close()
            cameraDevice?.close()
            imageReader?.close()
            backgroundThread?.quitSafely()
        }
    }

    AndroidView(
        factory = { ctx ->
            TextureView(ctx).apply {
                surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                    override fun onSurfaceTextureAvailable(
                        surface: SurfaceTexture,
                        width: Int,
                        height: Int
                    ) {
                        textureView = this@apply
                        // Configure transform for proper preview orientation
                        if (cameraCharacteristics != null) {
                            configureTransform(this@apply, width, height, cameraCharacteristics!!, ctx)
                        }
                    }

                    override fun onSurfaceTextureSizeChanged(
                        surface: SurfaceTexture,
                        width: Int,
                        height: Int
                    ) {
                        if (cameraCharacteristics != null) {
                            configureTransform(this@apply, width, height, cameraCharacteristics!!, ctx)
                        }
                    }

                    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = true
                    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
                }
            }
        },
        modifier = modifier
    )
}

// Helper functions for Android camera implementation
private fun getCameraId(cameraManager: CameraManager, facing: CameraFacing): String? {
    return try {
        cameraManager.cameraIdList.find { id ->
            val characteristics = cameraManager.getCameraCharacteristics(id)
            val face = characteristics.get(CameraCharacteristics.LENS_FACING)
            when (facing) {
                CameraFacing.FRONT -> face == CameraCharacteristics.LENS_FACING_FRONT
                CameraFacing.BACK -> face == CameraCharacteristics.LENS_FACING_BACK
            }
        }
    } catch (e: Exception) {
        AppLogger.e("CameraPreviewNative.android", "getCameraException: $e")
        null
    }
}

@RequiresPermission(Manifest.permission.CAMERA)
private fun openCamera(
    cameraManager: CameraManager,
    cameraId: String,
    backgroundHandler: Handler,
    onCameraOpened: (CameraDevice) -> Unit
) {
    val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            onCameraOpened(camera)
        }

        override fun onDisconnected(camera: CameraDevice) {
            camera.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            camera.close()
        }
    }

    try {
        cameraManager.openCamera(cameraId, stateCallback, backgroundHandler)
    } catch (e: SecurityException) {
        AppLogger.e("CameraPreviewNative.android","openCamera permission error: $e")
    }
}

private fun setupCaptureSession(
    cameraDevice: CameraDevice,
    textureView: TextureView,
    cameraCharacteristics: CameraCharacteristics,
    context: Context,
    backgroundHandler: Handler,
    onSessionReady: (CameraCaptureSession, ImageReader) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val texture = textureView.surfaceTexture

        // Get the best preview size
        val map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
        val previewSize = chooseOptimalSize(
            map?.getOutputSizes(SurfaceTexture::class.java) ?: emptyArray(),
            textureView.width,
            textureView.height,
            Size(1920, 1080)
        )

        texture?.setDefaultBufferSize(previewSize.width, previewSize.height)
        val surface = Surface(texture)

        // Create ImageReader with proper orientation consideration
        val displayRotation = getDisplayRotation(context)
        val sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0

        val imageReader = ImageReader.newInstance(
            previewSize.width,
            previewSize.height,
            ImageFormat.JPEG,
            1
        )

        val surfaces = listOf(surface, imageReader.surface)

        cameraDevice.createCaptureSession(surfaces, object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                val captureRequestBuilder =
                    cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                captureRequestBuilder.addTarget(surface)

                session.setRepeatingRequest(captureRequestBuilder.build(), null, backgroundHandler)
                onSessionReady(session, imageReader)
            }

            override fun onConfigureFailed(session: CameraCaptureSession) {
                onError("Failed to configure camera session")
            }
        }, backgroundHandler)
    } catch (e: Exception) {
        onError("Failed to setup capture session: ${e.message}")
        AppLogger.e("CameraPreviewNative.android", "Failed to setup capture session: ${e.message}")
    }
}

private fun captureStillPicture(
    captureSession: CameraCaptureSession,
    imageReader: ImageReader,
    cameraCharacteristics: CameraCharacteristics,
    backgroundHandler: Handler,
    flashMode: FlashMode,
    context: Context,
    onImageCaptured: (String) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val captureRequestBuilder =
            captureSession.device.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        captureRequestBuilder.addTarget(imageReader.surface)

        // Set flash mode
        when (flashMode) {
            FlashMode.ON -> captureRequestBuilder.set(
                CaptureRequest.FLASH_MODE,
                CaptureRequest.FLASH_MODE_SINGLE
            )

            FlashMode.AUTO -> captureRequestBuilder.set(
                CaptureRequest.CONTROL_AE_MODE,
                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
            )

            FlashMode.OFF -> captureRequestBuilder.set(
                CaptureRequest.FLASH_MODE,
                CaptureRequest.FLASH_MODE_OFF
            )
        }

        // Set proper JPEG orientation based on device rotation and sensor orientation
        val displayRotation = getDisplayRotation(context)
        val sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0
        val jpegOrientation = getJpegOrientation(cameraCharacteristics, displayRotation)

        captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, jpegOrientation)

        imageReader.setOnImageAvailableListener({
            val image = imageReader.acquireLatestImage()
            saveImage(image, context, onImageCaptured, onError)
        }, backgroundHandler)

        captureSession.capture(captureRequestBuilder.build(), null, backgroundHandler)
    } catch (e: Exception) {
        onError("Failed to capture image: ${e.message}")
        AppLogger.e("CameraPreviewNative.android", "Failed to capture image: ${e.message}")
    }
}

private fun saveImage(
    image: Image,
    context: Context,
    onImageCaptured: (String) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)

        val file = File(
            context.getExternalFilesDir(null),
            "${AppConstants.CAPTURED_IMAGE_CACHE_PREFIX}-${System.currentTimeMillis()}.jpg"
        )
        FileOutputStream(file).use { output ->
            output.write(bytes)
        }

        image.close()
        onImageCaptured(file.absolutePath)
        AppLogger.d("CameraPreviewNative.android", "Saved captured image to ${file.absolutePath}")
    } catch (e: Exception) {
        AppLogger.e("CameraPreviewNative.android", "Failed to save image: ${e.message}")
        onError("Failed to save image: ${e.message}")
    }
}

// Helper function to get display rotation
private fun getDisplayRotation(context: Context): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return windowManager.defaultDisplay.rotation
}

// Helper function to calculate JPEG orientation
private fun getJpegOrientation(cameraCharacteristics: CameraCharacteristics, deviceRotation: Int): Int {
    val sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0
    val lensFacing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) ?: CameraCharacteristics.LENS_FACING_BACK

    val deviceOrientationDegrees = when (deviceRotation) {
        Surface.ROTATION_0 -> 0
        Surface.ROTATION_90 -> 90
        Surface.ROTATION_180 -> 180
        Surface.ROTATION_270 -> 270
        else -> 0
    }

    return if (lensFacing == CameraCharacteristics.LENS_FACING_FRONT) {
        (sensorOrientation + deviceOrientationDegrees) % 360
    } else {
        (sensorOrientation - deviceOrientationDegrees + 360) % 360
    }
}

// Helper function to configure TextureView transform for proper preview orientation
private fun configureTransform(textureView: TextureView, viewWidth: Int, viewHeight: Int,
                               cameraCharacteristics: CameraCharacteristics, context: Context) {
    val rotation = getDisplayRotation(context)
    val matrix = Matrix()
    val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
    val bufferRect = RectF(0f, 0f, 1080f, 1920f) // Your preview size
    val centerX = viewRect.centerX()
    val centerY = viewRect.centerY()

    if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
        bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
        matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
        val scale = Math.max(
            viewHeight.toFloat() / 1920f,
            viewWidth.toFloat() / 1080f
        )
        matrix.postScale(scale, scale, centerX, centerY)
        matrix.postRotate((90 * (rotation - 2)).toFloat(), centerX, centerY)
    } else if (Surface.ROTATION_180 == rotation) {
        matrix.postRotate(180f, centerX, centerY)
    }

    textureView.setTransform(matrix)
}

// Helper function to choose optimal preview size
private fun chooseOptimalSize(choices: Array<Size>, textureViewWidth: Int, textureViewHeight: Int, maxSize: Size): Size {
    // Collect the supported resolutions that are at least as big as the preview Surface
    val bigEnough = mutableListOf<Size>()
    // Collect the supported resolutions that are smaller than the preview Surface
    val notBigEnough = mutableListOf<Size>()
    val w = maxSize.width
    val h = maxSize.height

    for (option in choices) {
        if (option.width <= w && option.height <= h) {
            if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
                bigEnough.add(option)
            } else {
                notBigEnough.add(option)
            }
        }
    }

    // Pick the smallest of those big enough. If there is no one big enough, pick the
    // largest of those not big enough.
    return when {
        bigEnough.size > 0 -> bigEnough.minByOrNull { it.width * it.height } ?: choices[0]
        notBigEnough.size > 0 -> notBigEnough.maxByOrNull { it.width * it.height } ?: choices[0]
        else -> choices[0]
    }
}