package com.example.retrocalc

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.example.retrocalc.ui.components.*

@Composable
fun CalculatorScreen() {
    var state by remember { mutableStateOf(CalcState()) }
    val haptic = LocalHapticFeedback.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Display
        DisplayWindow(
            mainText = state.display,
            tapeText = CalcEngine.getTapeText(state)
        )
        
        // Keypad - Manual layout for proper wide key handling
        Column(
            verticalArrangement = Arrangement.spacedBy(RetroTokens.keySpacing)
        ) {
            // Row 1: C, ±, %, ÷
            Row(
                horizontalArrangement = Arrangement.spacedBy(RetroTokens.keySpacing)
            ) {
                RetroKey("C", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onClear(state) 
                }, contentDescription = "Clear")
                RetroKey("±", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onToggleSign(state) 
                }, contentDescription = "Plus minus")
                RetroKey("%", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onPercent(state) 
                }, contentDescription = "Percent")
                RetroOpKey("÷", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onOp(state, Op.DIV) 
                }, contentDescription = "Divide")
            }
            
            // Row 2: 7, 8, 9, ×
            Row(
                horizontalArrangement = Arrangement.spacedBy(RetroTokens.keySpacing)
            ) {
                RetroKey("7", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '7') 
                })
                RetroKey("8", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '8') 
                })
                RetroKey("9", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '9') 
                })
                RetroOpKey("×", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onOp(state, Op.MUL) 
                }, contentDescription = "Multiply")
            }
            
            // Row 3: 4, 5, 6, −
            Row(
                horizontalArrangement = Arrangement.spacedBy(RetroTokens.keySpacing)
            ) {
                RetroKey("4", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '4') 
                })
                RetroKey("5", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '5') 
                })
                RetroKey("6", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '6') 
                })
                RetroOpKey("−", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onOp(state, Op.SUB) 
                }, contentDescription = "Subtract")
            }
            
            // Row 4: 1, 2, 3, +
            Row(
                horizontalArrangement = Arrangement.spacedBy(RetroTokens.keySpacing)
            ) {
                RetroKey("1", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '1') 
                })
                RetroKey("2", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '2') 
                })
                RetroKey("3", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '3') 
                })
                RetroOpKey("+", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onOp(state, Op.ADD) 
                }, contentDescription = "Add")
            }
            
            // Row 5: 0 (wide), ., =
            Row(
                horizontalArrangement = Arrangement.spacedBy(RetroTokens.keySpacing)
            ) {
                WideRetroKey("0", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDigit(state, '0') 
                })
                RetroKey(".", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onDecimal(state) 
                }, contentDescription = "Decimal point")
                RetroOpKey("=", onClick = { 
                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                    state = CalcEngine.onEquals(state) 
                }, contentDescription = "Equals")
            }
        }
    }
}
