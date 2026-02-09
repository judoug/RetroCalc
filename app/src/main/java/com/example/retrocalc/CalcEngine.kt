package com.example.retrocalc

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

enum class Op {
    ADD, SUB, MUL, DIV
}

data class CalcState(
    val display: String = "0",
    val accumulator: BigDecimal? = null,
    val pendingOp: Op? = null,
    val inTyping: Boolean = false,
    val lastEqualsOp: Op? = null,
    val lastEqualsRight: BigDecimal? = null,
    val error: Boolean = false
)

object CalcEngine {
    private val mathContext = MathContext(12, RoundingMode.HALF_UP)
    private val displayFormat = DecimalFormat("#,###.##########", DecimalFormatSymbols(Locale.US))
    
    fun onDigit(state: CalcState, digit: Char): CalcState {
        if (state.error) return state.copy(error = false, display = digit.toString(), inTyping = true)
        
        return if (state.inTyping) {
            val newDisplay = if (state.display == "0") digit.toString() else state.display + digit
            state.copy(display = newDisplay)
        } else {
            state.copy(display = digit.toString(), inTyping = true)
        }
    }
    
    fun onDecimal(state: CalcState): CalcState {
        if (state.error) return state.copy(error = false, display = "0.", inTyping = true)
        
        return if (state.inTyping) {
            if (!state.display.contains(".")) {
                state.copy(display = state.display + ".")
            } else {
                state
            }
        } else {
            state.copy(display = "0.", inTyping = true)
        }
    }
    
    fun onClear(state: CalcState): CalcState {
        return if (state.inTyping || state.display != "0") {
            // C - Clear current entry
            state.copy(
                display = "0",
                inTyping = false
            )
        } else {
            // AC - All Clear
            CalcState()
        }
    }
    
    fun onToggleSign(state: CalcState): CalcState {
        if (state.error) return state

        val current = parseDisplay(state.display) ?: return state
        
        val newDisplay = if (current == BigDecimal.ZERO) {
            "0"
        } else {
            formatDisplay(-current)
        }
        
        return state.copy(display = newDisplay)
    }
    
    fun onPercent(state: CalcState): CalcState {
        if (state.error) return state

        val current = parseDisplay(state.display) ?: return state
        
        return if (state.pendingOp != null && state.accumulator != null) {
            // If an operation is pending, treat current as percentage of accumulator
            val percentage = current.divide(BigDecimal(100), mathContext)
            val result = when (state.pendingOp) {
                Op.ADD -> state.accumulator + (state.accumulator * percentage)
                Op.SUB -> state.accumulator - (state.accumulator * percentage)
                Op.MUL -> state.accumulator * percentage
                Op.DIV -> if (percentage != BigDecimal.ZERO) state.accumulator / percentage else {
                    return state.copy(error = true, display = "Error")
                }
            }
            state.copy(
                display = formatDisplay(result),
                accumulator = result,
                pendingOp = null,
                inTyping = false
            )
        } else {
            // Otherwise, divide current by 100
            val result = current.divide(BigDecimal(100), mathContext)
            state.copy(display = formatDisplay(result), inTyping = false)
        }
    }
    
    fun onOp(state: CalcState, op: Op): CalcState {
        if (state.error) return state

        val current = parseDisplay(state.display) ?: return state
        
        return if (state.pendingOp != null && state.accumulator != null && state.inTyping) {
            // Evaluate pending operation first
            val result = evaluate(state.accumulator, current, state.pendingOp)
            if (result == null) {
                state.copy(error = true, display = "Error")
            } else {
                state.copy(
                    display = formatDisplay(result),
                    accumulator = result,
                    pendingOp = op,
                    inTyping = false
                )
            }
        } else {
            // Just set the new operation
            state.copy(
                accumulator = current,
                pendingOp = op,
                inTyping = false
            )
        }
    }
    
    fun onEquals(state: CalcState): CalcState {
        if (state.error) return state

        val current = parseDisplay(state.display) ?: return state
        
        return if (state.pendingOp != null && state.accumulator != null) {
            // First equals - evaluate the operation
            val result = evaluate(state.accumulator, current, state.pendingOp)
            if (result == null) {
                state.copy(error = true, display = "Error")
            } else {
                state.copy(
                    display = formatDisplay(result),
                    accumulator = result,
                    pendingOp = null,
                    inTyping = false,
                    lastEqualsOp = state.pendingOp,
                    lastEqualsRight = current
                )
            }
        } else if (state.lastEqualsOp != null && state.lastEqualsRight != null) {
            // Repeated equals - repeat last operation
            val result = evaluate(current, state.lastEqualsRight, state.lastEqualsOp)
            if (result == null) {
                state.copy(error = true, display = "Error")
            } else {
                state.copy(
                    display = formatDisplay(result),
                    accumulator = result,
                    inTyping = false
                )
            }
        } else {
            state
        }
    }
    
    private fun evaluate(left: BigDecimal, right: BigDecimal, op: Op): BigDecimal? {
        return try {
            when (op) {
                Op.ADD -> left.add(right, mathContext)
                Op.SUB -> left.subtract(right, mathContext)
                Op.MUL -> left.multiply(right, mathContext)
                Op.DIV -> {
                    if (right == BigDecimal.ZERO) {
                        null // Division by zero
                    } else {
                        left.divide(right, mathContext)
                    }
                }
            }
        } catch (e: Exception) {
            null
        }
    }
    
    private fun parseDisplay(display: String): BigDecimal? {
        return try {
            BigDecimal(display.replace(",", ""))
        } catch (e: NumberFormatException) {
            null
        }
    }

    private fun formatDisplay(value: BigDecimal): String {
        return try {
            // Handle very large or very small numbers
            val absValue = value.abs()
            if (absValue >= BigDecimal("1E9") || (absValue < BigDecimal("0.000001") && absValue > BigDecimal.ZERO)) {
                // Use scientific notation for very large or very small numbers
                String.format("%.6E", value)
            } else {
                // Remove trailing zeros after decimal point
                val formatted = displayFormat.format(value)
                if (formatted.contains(".")) {
                    formatted.trimEnd('0').trimEnd('.')
                } else {
                    formatted
                }
            }
        } catch (e: Exception) {
            "Error"
        }
    }
    
    fun getTapeText(state: CalcState): String? {
        return if (state.pendingOp != null && state.accumulator != null) {
            val opSymbol = when (state.pendingOp) {
                Op.ADD -> "+"
                Op.SUB -> "−"
                Op.MUL -> "×"
                Op.DIV -> "÷"
            }
            "${formatDisplay(state.accumulator)}$opSymbol"
        } else {
            null
        }
    }
}
