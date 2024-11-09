package com.example.videomessengerproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkGreen = Color(0xFF008800)
private val White = Color(0xFFFFFFFF)
private val Gold = Color(0xFFD3B000)
private val VeryLightGreen = Color(0xDEFFDE) // Очень светло-зеленый цвет

@Composable
fun VideoMessengerProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = DarkGreen,
            onPrimary = White,
            secondary = Gold,
            onSecondary = White,
            surface = DarkGreen,
            onSurface = White,
            background = DarkGreen,
            onBackground = White,
        )
    } else {
        lightColorScheme(
            primary = DarkGreen,
            onPrimary = White,
            secondary = Gold,
            onSecondary = White,
            surface = White,
            onSurface = DarkGreen,
            background = VeryLightGreen,
            onBackground = DarkGreen,
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}
