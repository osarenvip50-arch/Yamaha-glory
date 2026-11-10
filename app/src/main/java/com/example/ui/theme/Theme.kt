package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FridayColorScheme = darkColorScheme(
    primary = CyanPrimary,
    onPrimary = DarkNavyBase,
    primaryContainer = DarkNavyCard,
    onPrimaryContainer = CyanGlow,
    secondary = AccentPurple,
    onSecondary = Color.White,
    secondaryContainer = DarkNavyCard,
    onSecondaryContainer = Color(0xFFD1C4E9),
    tertiary = AccentAmber,
    onTertiary = DarkNavyBase,
    background = DarkNavyBase,
    onBackground = TextPrimary,
    surface = DarkNavySurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkNavyCard,
    onSurfaceVariant = TextSecondary,
    outline = DarkNavyBorder,
    error = AccentRed,
    onError = Color.White
)

@Composable
fun FridayTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FridayColorScheme,
        typography = AppTypography,
        content = content
    )
}
