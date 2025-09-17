package com.example.retrocalc.ui.theme

import androidx.compose.ui.graphics.Color

// Retro Calculator Color Palette
val cream = Color(0xFFF4EFE7)
val creamDark = Color(0xFFE9E1D6)
val oliveDisplay = Color(0xFFC7CFB0)
val keyTopLight = Color(0xFFFFF9F1)
val keyTop = Color(0xFFF7EBDD)
val keyShadow = Color(0xFFD9CFC2)
val accentOrange = Color(0xFFF39B3C)
val accentOrangeDark = Color(0xFFE48727)
val burntOrange = Color(0xFFC95F2C)
val textDark = Color(0xFF1F1F1F)

// Material3 Color Scheme
val RetroLightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = accentOrange,
    onPrimary = Color.White,
    primaryContainer = accentOrangeDark,
    onPrimaryContainer = Color.White,
    secondary = burntOrange,
    onSecondary = Color.White,
    tertiary = keyShadow,
    onTertiary = textDark,
    background = cream,
    onBackground = textDark,
    surface = keyTop,
    onSurface = textDark,
    surfaceVariant = creamDark,
    onSurfaceVariant = textDark,
    outline = keyShadow,
    outlineVariant = keyShadow.copy(alpha = 0.5f)
)
