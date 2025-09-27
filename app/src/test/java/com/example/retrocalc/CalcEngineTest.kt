package com.example.retrocalc

import org.junit.Assert.*
import org.junit.Test

class CalcEngineTest {
    
    @Test
    fun `basic addition`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '1')
        state = CalcEngine.onDigit(state, '2')
        state = CalcEngine.onOp(state, Op.ADD)
        state = CalcEngine.onDigit(state, '7')
        state = CalcEngine.onEquals(state)
        assertEquals("19", state.display)
    }
    
    @Test
    fun `repeated equals with addition`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '1')
        state = CalcEngine.onDigit(state, '2')
        state = CalcEngine.onOp(state, Op.ADD)
        state = CalcEngine.onDigit(state, '7')
        state = CalcEngine.onEquals(state) // 19
        state = CalcEngine.onEquals(state) // 26
        assertEquals("26", state.display)
    }
    
    @Test
    fun `multiplication`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '4')
        state = CalcEngine.onDigit(state, '2')
        state = CalcEngine.onOp(state, Op.MUL)
        state = CalcEngine.onDigit(state, '1')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onEquals(state)
        assertEquals("420", state.display)
    }
    
    @Test
    fun `percentage calculation`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '5')
        state = CalcEngine.onOp(state, Op.ADD)
        state = CalcEngine.onDigit(state, '5')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onPercent(state) // 50% of 5 = 2.5
        state = CalcEngine.onEquals(state) // 5 + 2.5 = 7.5
        assertEquals("7.5", state.display)
    }
    
    @Test
    fun `division by zero shows error`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '9')
        state = CalcEngine.onOp(state, Op.DIV)
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onEquals(state)
        assertTrue(state.error)
        assertEquals("Error", state.display)
    }
    
    @Test
    fun `error state resets on next input`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '9')
        state = CalcEngine.onOp(state, Op.DIV)
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onEquals(state) // Error
        state = CalcEngine.onDigit(state, '5') // Should reset error
        assertFalse(state.error)
        assertEquals("5", state.display)
    }
    
    @Test
    fun `AC clears everything`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '1')
        state = CalcEngine.onDigit(state, '2')
        state = CalcEngine.onOp(state, Op.ADD)
        state = CalcEngine.onDigit(state, '3')
        state = CalcEngine.onClear(state) // This should be C
        state = CalcEngine.onClear(state) // This should be AC
        assertEquals("0", state.display)
        assertNull(state.accumulator)
        assertNull(state.pendingOp)
        assertFalse(state.inTyping)
    }
    
    @Test
    fun `C clears current entry only`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '1')
        state = CalcEngine.onDigit(state, '2')
        state = CalcEngine.onOp(state, Op.ADD)
        state = CalcEngine.onDigit(state, '3')
        state = CalcEngine.onDigit(state, '4')
        state = CalcEngine.onClear(state) // Should be C (clear current)
        assertEquals("0", state.display)
        assertNotNull(state.accumulator)
        assertNotNull(state.pendingOp)
        assertFalse(state.inTyping)
    }
    
    @Test
    fun `toggle sign`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '5')
        state = CalcEngine.onToggleSign(state)
        assertEquals("-5", state.display)
        state = CalcEngine.onToggleSign(state)
        assertEquals("5", state.display)
    }
    
    @Test
    fun `toggle sign on zero`() {
        var state = CalcState()
        state = CalcEngine.onToggleSign(state)
        assertEquals("0", state.display)
    }
    
    @Test
    fun `decimal input`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '5')
        state = CalcEngine.onDecimal(state)
        state = CalcEngine.onDigit(state, '2')
        assertEquals("5.2", state.display)
    }
    
    @Test
    fun `decimal on fresh state`() {
        var state = CalcState()
        state = CalcEngine.onDecimal(state)
        assertEquals("0.", state.display)
        assertTrue(state.inTyping)
    }
    
    @Test
    fun `repeated decimal does nothing`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '5')
        state = CalcEngine.onDecimal(state)
        state = CalcEngine.onDecimal(state) // Should not add another decimal
        assertEquals("5.", state.display)
    }
    
    @Test
    fun `operation chaining`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '1')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onOp(state, Op.ADD)
        state = CalcEngine.onDigit(state, '5')
        state = CalcEngine.onOp(state, Op.MUL) // Should evaluate 10+5=15 first
        state = CalcEngine.onDigit(state, '2')
        state = CalcEngine.onEquals(state)
        assertEquals("30", state.display)
    }
    
    @Test
    fun `repeated equals with subtraction`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '1')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onOp(state, Op.SUB)
        state = CalcEngine.onDigit(state, '3')
        state = CalcEngine.onEquals(state) // 7
        state = CalcEngine.onEquals(state) // 4
        state = CalcEngine.onEquals(state) // 1
        assertEquals("1", state.display)
    }
    
    @Test
    fun `percentage without pending operation`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '5')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onPercent(state) // Should divide by 100
        assertEquals("0.5", state.display)
    }
    
    @Test
    fun `large number formatting`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '1')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')

        state = CalcEngine.onOp(state, Op.MUL)

        state = CalcEngine.onDigit(state, '1')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')
        state = CalcEngine.onDigit(state, '0')

        state = CalcEngine.onEquals(state)

        // Should format with scientific notation
        assertTrue(state.display.contains("E"))
    }
    
    @Test
    fun `tape text shows pending operation`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '4')
        state = CalcEngine.onDigit(state, '2')
        state = CalcEngine.onOp(state, Op.MUL)
        val tapeText = CalcEngine.getTapeText(state)
        assertEquals("42×", tapeText)
    }
    
    @Test
    fun `tape text is null when no pending operation`() {
        var state = CalcState()
        state = CalcEngine.onDigit(state, '4')
        state = CalcEngine.onDigit(state, '2')
        val tapeText = CalcEngine.getTapeText(state)
        assertNull(tapeText)
    }
}
