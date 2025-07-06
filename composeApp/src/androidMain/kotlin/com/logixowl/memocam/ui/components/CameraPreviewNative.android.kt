package com.logixowl.memocam.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
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
import android.view.Surface
import android.view.TextureView
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
    LaunchedEffect(cameraDevice, textureView) {
        if (cameraDevice != null && textureView != null) {
            setupCaptureSession(
                cameraDevice!!,
                textureView!!,
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
        if (captureImage && captureSession != null && imageReader != null) {
            captureStillPicture(
                captureSession!!,
                imageReader!!,
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
                    }

                    override fun onSurfaceTextureSizeChanged(
                        surface: SurfaceTexture,
                        width: Int,
                        height: Int
                    ) {
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
        // Handle permission error
    }
}

private fun setupCaptureSession(
    cameraDevice: CameraDevice,
    textureView: TextureView,
    backgroundHandler: Handler,
    onSessionReady: (CameraCaptureSession, ImageReader) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val texture = textureView.surfaceTexture
        texture?.setDefaultBufferSize(1920, 1080)
        val surface = Surface(texture)

        val imageReader = ImageReader.newInstance(1920, 1080, ImageFormat.JPEG, 1)

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
    }
}

private fun captureStillPicture(
    captureSession: CameraCaptureSession,
    imageReader: ImageReader,
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

        imageReader.setOnImageAvailableListener({
            val image = imageReader.acquireLatestImage()
            saveImage(image, context, onImageCaptured, onError)
        }, backgroundHandler)

        captureSession.capture(captureRequestBuilder.build(), null, backgroundHandler)
    } catch (e: Exception) {
        onError("Failed to capture image: ${e.message}")
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
            "captured_image_${System.currentTimeMillis()}.jpg"
        )
        FileOutputStream(file).use { output ->
            output.write(bytes)
        }

        image.close()
        onImageCaptured(file.absolutePath)
    } catch (e: Exception) {
        onError("Failed to save image: ${e.message}")
    }
}
