package com.qpeterp.clip.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


object Colors {
    val LightGray = Color(0xFFEBEDF2)
    val DarkGray = Color(0xFF747474)
    val White = Color.White
    val Black = Color.Black
    val Transparent = Color.Transparent
}

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    val customColorScheme = ColorScheme(
        primary = Color.Black,
        onPrimary = Color.White,
        primaryContainer = Color.Gray,
        onPrimaryContainer = Color.White,
        inversePrimary = Color.White,
        secondary = Color.DarkGray,
        onSecondary = Color.White,
        secondaryContainer = Color.LightGray,
        onSecondaryContainer = Color.Black,
        tertiary = Color.Red,
        onTertiary = Color.White,
        tertiaryContainer = Color.Transparent,
        onTertiaryContainer = Color.Black,
        background = Color.White,
        onBackground = Color.Black,
        surface = Color.LightGray,
        onSurface = Color.Black,
        surfaceVariant = Color.Gray,
        onSurfaceVariant = Color.White,
        surfaceTint = Color.Black,
        inverseSurface = Color.DarkGray,
        inverseOnSurface = Color.White,
        error = Color.Red,
        onError = Color.White,
        errorContainer = Color.Red,
        onErrorContainer = Color.Black,
        outline = Color.Gray,
        outlineVariant = Color.DarkGray,
        scrim = Color.Black.copy(alpha = 0.5f),
        surfaceBright = Color.White,
        surfaceDim = Color.LightGray,
        surfaceContainer = Color.Gray,
        surfaceContainerHigh = Color.DarkGray,
        surfaceContainerHighest = Color.Black,
        surfaceContainerLow = Color.LightGray,
        surfaceContainerLowest = Color.White
    )

    MaterialTheme(
        colorScheme = customColorScheme
    ) {
        content()
    }
}
