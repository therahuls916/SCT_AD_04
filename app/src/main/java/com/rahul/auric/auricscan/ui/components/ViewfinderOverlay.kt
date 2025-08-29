// FILE: ui/components/ViewfinderOverlay.kt

package com.rahul.auric.auricscan.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ViewfinderOverlay(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val boxWidth = size.width * 0.7f
        val boxHeight = boxWidth
        val cornerRadius = CornerRadius(24.dp.toPx(), 24.dp.toPx())
        val strokeWidth = 6.dp.toPx()
        val cornerLength = 32.dp.toPx()

        val top = (size.height - boxHeight) / 2
        val left = (size.width - boxWidth) / 2

        // This is the "cutout" effect
        drawRoundRect(
            color = Color.Black,
            alpha = 0.6f,
            blendMode = BlendMode.DstOut
        )
        drawRoundRect(
            topLeft = Offset(left, top),
            size = Size(boxWidth, boxHeight),
            cornerRadius = cornerRadius,
            color = Color.Transparent,
            blendMode = BlendMode.Clear
        )

        // Draw the corner brackets
        val cornerStroke = Stroke(width = strokeWidth)
        // Top-left corner
        drawPath(
            color = Color.White,
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(left, top + cornerRadius.y + cornerLength)
                lineTo(left, top + cornerRadius.y)
                quadraticBezierTo(left, top, left + cornerRadius.x, top)
                lineTo(left + cornerRadius.x + cornerLength, top)
            },
            style = cornerStroke
        )
        // Top-right corner
        drawPath(
            color = Color.White,
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(left + boxWidth - cornerRadius.x - cornerLength, top)
                lineTo(left + boxWidth - cornerRadius.x, top)
                quadraticBezierTo(left + boxWidth, top, left + boxWidth, top + cornerRadius.y)
                lineTo(left + boxWidth, top + cornerRadius.y + cornerLength)
            },
            style = cornerStroke
        )
        // Bottom-left corner
        drawPath(
            color = Color.White,
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(left, top + boxHeight - cornerRadius.y - cornerLength)
                lineTo(left, top + boxHeight - cornerRadius.y)
                quadraticBezierTo(left, top + boxHeight, left + cornerRadius.x, top + boxHeight)
                lineTo(left + cornerRadius.x + cornerLength, top + boxHeight)
            },
            style = cornerStroke
        )
        // Bottom-right corner
        drawPath(
            color = Color.White,
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(left + boxWidth - cornerRadius.x - cornerLength, top + boxHeight)
                lineTo(left + boxWidth - cornerRadius.x, top + boxHeight)
                quadraticBezierTo(left + boxWidth, top + boxHeight, left + boxWidth, top + boxHeight - cornerRadius.y)
                lineTo(left + boxWidth, top + boxHeight - cornerRadius.y - cornerLength)
            },
            style = cornerStroke
        )
    }
}