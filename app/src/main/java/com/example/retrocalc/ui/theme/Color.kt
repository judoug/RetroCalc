package com.example.retrocalc.ui.theme

import androidx.compose.ui.graphics.Color

// Retro Calculator Color Palette - Warm pastel tones with 3D look
val cream = Color(0xFFF4EFE7)
val creamDark = Color(0xFFE9E1D6)
val oliveDisplay = Color(0xFFB8C4A0)  // Slightly more saturated olive
val oliveDisplayDark = Color(0xFF9BA885)  // Darker olive for display border

// Warm pastel button colors
val peachButton = Color(0xFFFFB366)  // Warm peach for primary buttons
val peachButtonDark = Color(0xFFE6994D)  // Darker peach for shadows
val creamButton = Color(0xFFFFF2E6)  // Light cream for number buttons
val creamButtonDark = Color(0xFFE6D4C0)  // Darker cream for shadows

// Highlighted buttons (C button)
val redOrange = Color(0xFFC0392B)  // Darker red for C button
val redOrangeDark = Color(0xFFA93226)  // Darker red for shadows

// Operators
val operatorOrange = Color(0xFFF39C12)  // Slightly darker orange for operators
val operatorOrangeDark = Color(0xFFE67E22)  // Darker orange for shadows

val textDark = Color(0xFF2D2D2D)  // Dark text for contrast

// Material3 Color Scheme
val RetroLightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = operatorOrange,
    onPrimary = Color.White,
    primaryContainer = operatorOrangeDark,
    onPrimaryContainer = Color.White,
    secondary = redOrange,
    onSecondary = Color.White,
    tertiary = creamButtonDark,
    onTertiary = textDark,
    background = cream,
    onBackground = textDark,
    surface = creamButton,
    onSurface = textDark,
    surfaceVariant = creamDark,
    onSurfaceVariant = textDark,
    outline = creamButtonDark,
    outlineVariant = creamButtonDark.copy(alpha = 0.5f)
)
