package com.example.retrocalc.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrocalc.ui.theme.*

// Design tokens - Optimized for Quest floating window
object RetroTokens {
    val keySize = 72.dp
    val keySpacing = 12.dp
    val displayPadding = 24.dp
    val displayCornerRadius = 16.dp
    val keyCornerRadius = 36.dp
    val shadowElevation = 4.dp
    val innerShadowOffset = 2.dp
    
    // Quest-specific optimizations
    val focusRingSize = 8.dp  // Larger focus ring for Quest controllers
    val minTouchTarget = 48.dp  // Minimum touch target size
    val displayTextSize = 48.sp  // Optimized for Quest window size
    val tapeTextSize = 16.sp     // Smaller tape text
}

@Composable
fun DisplayWindow(
    modifier: Modifier = Modifier,
    mainText: String,
    tapeText: String? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)  // Fixed height for equal padding
            .padding(horizontal = RetroTokens.displayPadding)
            .clip(RoundedCornerShape(RetroTokens.displayCornerRadius))
            .background(oliveDisplay)
            .border(
                width = 10.dp,
                color = textDark,
                shape = RoundedCornerShape(RetroTokens.displayCornerRadius)
            )
            .padding(RetroTokens.displayPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (!tapeText.isNullOrEmpty()) {
                Text(
                    text = tapeText,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = RetroTokens.tapeTextSize),
                    color = textDark.copy(alpha = 0.7f),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = mainText,
                style = MaterialTheme.typography.displayLarge.copy(fontSize = RetroTokens.displayTextSize),
                color = textDark,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun RetroKey(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = text,
    isPressed: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressedState by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    val pressScale by animateFloatAsState(
        targetValue = if (isPressedState || isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "pressScale"
    )
    
    Box(
        modifier = modifier
            .size(RetroTokens.keySize)
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
                translationY = if (isPressedState || isPressed) 2f else 0f
            }
            .shadow(
                elevation = if (isPressedState || isPressed) 2.dp else 4.dp,
                shape = CircleShape,
                ambientColor = creamButtonDark.copy(alpha = 0.3f),
                spotColor = creamButtonDark.copy(alpha = 0.5f)
            )
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        creamButton.copy(alpha = 1.0f),
                        creamButton.copy(alpha = 0.9f)
                    ),
                    radius = RetroTokens.keySize.value * 0.7f
                )
            )
            .border(
                width = 7.dp,
                color = creamButtonDark.copy(alpha = 0.8f),
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .focusRequester(FocusRequester())
            .focusable()
            .semantics {
                this.contentDescription = contentDescription
            },
        contentAlignment = Alignment.Center
    ) {
        // Inner shadow effect
        Box(
            modifier = Modifier
                .size(RetroTokens.keySize - RetroTokens.innerShadowOffset)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Transparent,
                            creamButtonDark.copy(alpha = 0.1f)
                        ),
                        radius = RetroTokens.keySize.value
                    )
                )
        )
        
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            ),
            color = textDark,
            fontWeight = FontWeight.ExtraBold
        )
        
        // Focus ring - Quest optimized
        if (isFocused) {
            Box(
                modifier = Modifier
                    .size(RetroTokens.keySize + RetroTokens.focusRingSize)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = operatorOrange,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun RetroHighlightKey(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = text,
    isPressed: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressedState by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    val pressScale by animateFloatAsState(
        targetValue = if (isPressedState || isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "pressScale"
    )
    
    Box(
        modifier = modifier
            .size(RetroTokens.keySize)
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
                translationY = if (isPressedState || isPressed) 2f else 0f
            }
            .shadow(
                elevation = if (isPressedState || isPressed) 2.dp else 4.dp,
                shape = CircleShape,
                ambientColor = redOrangeDark.copy(alpha = 0.3f),
                spotColor = redOrangeDark.copy(alpha = 0.5f)
            )
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        redOrange.copy(alpha = 1.0f),
                        redOrange.copy(alpha = 0.9f)
                    ),
                    radius = RetroTokens.keySize.value * 0.7f
                )
            )
            .border(
                width = 7.dp,
                color = redOrangeDark.copy(alpha = 0.4f),
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .focusRequester(FocusRequester())
            .focusable()
            .semantics {
                this.contentDescription = contentDescription
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            ),
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
        
        // Focus ring - Quest optimized
        if (isFocused) {
            Box(
                modifier = Modifier
                    .size(RetroTokens.keySize + RetroTokens.focusRingSize)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun RetroOpKey(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = text,
    isPressed: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressedState by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    val pressScale by animateFloatAsState(
        targetValue = if (isPressedState || isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "pressScale"
    )
    
    Box(
        modifier = modifier
            .size(RetroTokens.keySize)
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
                translationY = if (isPressedState || isPressed) 2f else 0f
            }
            .shadow(
                elevation = if (isPressedState || isPressed) 2.dp else 4.dp,
                shape = CircleShape,
                ambientColor = operatorOrangeDark.copy(alpha = 0.3f),
                spotColor = operatorOrangeDark.copy(alpha = 0.5f)
            )
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        operatorOrange.copy(alpha = 1.0f),
                        operatorOrange.copy(alpha = 0.9f)
                    ),
                    radius = RetroTokens.keySize.value * 0.7f
                )
            )
            .border(
                width = 7.dp,
                color = operatorOrangeDark.copy(alpha = 0.4f),
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .focusRequester(FocusRequester())
            .focusable()
            .semantics {
                this.contentDescription = contentDescription
            },
        contentAlignment = Alignment.Center
    ) {
        // Inner shadow effect
        Box(
            modifier = Modifier
                .size(RetroTokens.keySize - RetroTokens.innerShadowOffset)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Transparent,
                            operatorOrangeDark.copy(alpha = 0.2f)
                        ),
                        radius = RetroTokens.keySize.value
                    )
                )
        )
        
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            ),
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
        
        // Focus ring - Quest optimized
        if (isFocused) {
            Box(
                modifier = Modifier
                    .size(RetroTokens.keySize + RetroTokens.focusRingSize)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun WideRetroKey(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = text,
    isPressed: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressedState by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()
    
    val pressScale by animateFloatAsState(
        targetValue = if (isPressedState || isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "pressScale"
    )
    
    val wideKeyWidth = RetroTokens.keySize * 2 + RetroTokens.keySpacing
    
    Box(
        modifier = modifier
            .width(wideKeyWidth)
            .height(RetroTokens.keySize)
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
            }
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(RetroTokens.keyCornerRadius),
                ambientColor = creamButtonDark,
                spotColor = creamButtonDark
            )
            .clip(RoundedCornerShape(RetroTokens.keyCornerRadius))
            .background(creamButton)
            .border(
                width = 7.dp,
                color = creamButtonDark.copy(alpha = 0.8f),
                shape = RoundedCornerShape(RetroTokens.keyCornerRadius)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .focusRequester(FocusRequester())
            .focusable()
            .semantics {
                this.contentDescription = contentDescription
            },
        contentAlignment = Alignment.Center
    ) {
        // Inner shadow effect
        Box(
            modifier = Modifier
                .width(wideKeyWidth - RetroTokens.innerShadowOffset)
                .height(RetroTokens.keySize - RetroTokens.innerShadowOffset)
                .clip(RoundedCornerShape(RetroTokens.keyCornerRadius))
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Transparent,
                            creamButtonDark.copy(alpha = 0.1f)
                        ),
                        radius = RetroTokens.keySize.value
                    )
                )
        )
        
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            ),
            color = textDark,
            fontWeight = FontWeight.ExtraBold
        )
        
        // Focus ring - Quest optimized
        if (isFocused) {
            Box(
                modifier = Modifier
                    .width(wideKeyWidth + RetroTokens.focusRingSize)
                    .height(RetroTokens.keySize + RetroTokens.focusRingSize)
                    .clip(RoundedCornerShape(RetroTokens.keyCornerRadius))
                    .border(
                        width = 3.dp,
                        color = operatorOrange,
                        shape = RoundedCornerShape(RetroTokens.keyCornerRadius)
                    )
            )
        }
    }
}
