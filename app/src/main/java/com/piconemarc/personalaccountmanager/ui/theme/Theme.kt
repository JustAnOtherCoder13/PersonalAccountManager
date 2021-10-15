package com.piconemarc.personalaccountmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = darkColors(
    primary = Brown,
    primaryVariant = BrownDark,
    secondary = White,
    secondaryVariant = Gray,
    onPrimary = White,
    onSecondary = Black,
    error = Negative,
    onSurface = White
)

private val DarkColorPalette = lightColors(
    primary = White,
    primaryVariant = Gray,
    secondary = Black,
    onPrimary = Black,
    onSecondary = White,
    error = Negative,
    onSurface = Black
)

@Composable
fun PersonalAccountManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}