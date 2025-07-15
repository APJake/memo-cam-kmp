package com.apjake.composeplayground.components.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by AP-Jake
 * on 11/07/2025
 */

@Composable
fun CloudShape3(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    size: Dp = 300.dp,
) {

    val cloudShape = remember { cloudShape3() }
    Box(
        modifier = Modifier
            .size(200.dp, 120.dp)
            .border(
                width = 4.dp,
                color = Color.Red,
                shape = cloudShape
            )
    )
}

private fun cloudShape3(): Shape {
    val cloudShape = GenericShape { size, _ ->
        val width = size.width
        val height = size.height

        // Define cloud proportions
        val centerY = height * 0.6f
        val leftCircleX = width * 0.2f
        val leftCircleRadius = width * 0.15f

        val centerCircleX = width * 0.5f
        val centerCircleRadius = width * 0.2f

        val rightCircleX = width * 0.8f
        val rightCircleRadius = width * 0.12f

        val topCircleX = width * 0.35f
        val topCircleY = height * 0.35f
        val topCircleRadius = width * 0.13f

        val topRightCircleX = width * 0.65f
        val topRightCircleY = height * 0.4f
        val topRightCircleRadius = width * 0.11f

        // Create a path for the cloud shape
        val path = Path().apply {

            // Start from the leftmost point
            moveTo(leftCircleX - leftCircleRadius, centerY)

            // Create the cloud outline by connecting arcs
            // Left circle (bottom left)
            addArc(
                androidx.compose.ui.geometry.Rect(
                    left = leftCircleX - leftCircleRadius,
                    top = centerY - leftCircleRadius,
                    right = leftCircleX + leftCircleRadius,
                    bottom = centerY + leftCircleRadius
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f
            )

            // Connect to center circle
            lineTo(centerCircleX - centerCircleRadius, centerY)

            // Center circle (bottom center)
            addArc(
                androidx.compose.ui.geometry.Rect(
                    left = centerCircleX - centerCircleRadius,
                    top = centerY - centerCircleRadius,
                    right = centerCircleX + centerCircleRadius,
                    bottom = centerY + centerCircleRadius
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f
            )

            // Connect to right circle
            lineTo(rightCircleX - rightCircleRadius, centerY)

            // Right circle (bottom right)
            addArc(
                androidx.compose.ui.geometry.Rect(
                    left = rightCircleX - rightCircleRadius,
                    top = centerY - rightCircleRadius,
                    right = rightCircleX + rightCircleRadius,
                    bottom = centerY + rightCircleRadius
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f
            )

            // Now create the top part of the cloud
            // Connect to top right circle
            lineTo(topRightCircleX + topRightCircleRadius, topRightCircleY)

            // Top right circle
            addArc(
                androidx.compose.ui.geometry.Rect(
                    left = topRightCircleX - topRightCircleRadius,
                    top = topRightCircleY - topRightCircleRadius,
                    right = topRightCircleX + topRightCircleRadius,
                    bottom = topRightCircleY + topRightCircleRadius
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 180f
            )

            // Connect to top circle
            lineTo(topCircleX + topCircleRadius, topCircleY)

            // Top circle
            addArc(
                androidx.compose.ui.geometry.Rect(
                    left = topCircleX - topCircleRadius,
                    top = topCircleY - topCircleRadius,
                    right = topCircleX + topCircleRadius,
                    bottom = topCircleY + topCircleRadius
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 180f
            )

            // Close the path
            close()
        }

        // Set the path to the shape
        addPath(path)
    }

    return cloudShape
}

@Composable
fun CloudShape(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    size: Dp = 300.dp,
) {
    val cloudShape = remember {
        GenericShape { size, _ ->
            val width = size.width
            val height = size.height

            // Circle 1
            val circle1Radius = width * 0.32f
            val circle1X = width - circle1Radius
            val circle1Y = height - circle1Radius
            val circle1Center = Offset(
                x = circle1X,
                y = circle1Y
            )
            addOval(
                Rect(
                    center = circle1Center,
                    radius = circle1Radius
                )
            )

            // Circle 2
            val circle2Radius = circle1Radius * 0.7f
            val circle2Center = Offset(
                x = circle2Radius,
                y = height - circle2Radius
            )
            addOval(
                Rect(
                    center = circle2Center,
                    radius = circle2Radius
                )
            )

            addRect(
                Rect(
                    offset = circle2Center,
                    size = Size(
                        width = width - circle1Radius - circle2Radius,
                        height = circle2Radius
                    )
                )
            )

            close()
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(cloudShape)
            .background(color)
    )
}

@Composable
fun CloudShape2(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    size: Dp = 300.dp,
) {
    // 60% of size
    val circle1Size = remember { (size.value * 0.6).dp }
    // 80% of circle 1
    val circle2Size = remember { (circle1Size.value * 0.7).dp }

    val circle2MarginAlignment = remember { (circle2Size.value * 0.3).dp }

    val sizeOfBox = remember { circle1Size + circle2Size - circle2MarginAlignment }

    val halfOfCircle1 = remember {
        (circle1Size.value / 2).dp
    }
    val overlayRectangleWidth = remember {
        ((circle1Size.value / 2) + (circle2Size.value / 2) - circle2MarginAlignment.value).dp
    }

    Box(modifier.width(sizeOfBox)) {
        Box(
            Modifier
                .size(circle1Size)
                .background(
                    color = color,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
        )
        Box(
            Modifier
                .size(circle2Size)
                .background(
                    color = Color.Gray,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .align(Alignment.BottomStart)
        )
        Box(
            Modifier
                .size(circle2Size)
                .background(
                    color = color,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .align(Alignment.BottomStart)
        )
        Box(
            Modifier
                .padding(
                    end = halfOfCircle1
                )
                .height(halfOfCircle1)
                .width(overlayRectangleWidth)
                .background(
                    color = Color.Red,
                )
                .align(Alignment.BottomEnd)
        )
    }
}
