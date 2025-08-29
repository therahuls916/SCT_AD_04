package com.rahul.auric.auricscan.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColors = darkColorScheme(
    primary = BrightMintAccent,
    background = DarkGreenBackground,
    surface = DarkGreenCard,
    onPrimary = DarkGreenBackground, // Text on a bright green button should be dark
    onBackground = OffWhiteText,      // The main text color on the dark background
    onSurface = OffWhiteText          // The text color used on cards
)

@Composable
fun AuricScanTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        // Assuming you have a Typography.kt and Shape.kt file in this package
        // typography = Typography,
        // shapes = Shapes,
        content = content
    )
}