package com.zyvo.universalprint.printer.escpos

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EscPosBuilderTest {
    @Test
    fun init_and_text() {
        val b = EscPosBuilder().init().text("Hi").newline().build()
        assertThat(b[0]).isEqualTo(0x1B.toByte())
        assertThat(b[1]).isEqualTo(0x40.toByte())
        assertThat(String(b, 2, 2, Charsets.UTF_8)).isEqualTo("Hi")
    }

    @Test
    fun bold_toggle() {
        val b = EscPosBuilder().bold(true).build()
        assertThat(b.toList()).contains(0x1B.toByte())
        assertThat(b.toList()).contains(0x45.toByte())
        assertThat(b.toList()).contains(0x01.toByte())
    }

    @Test
    fun cut_command() {
        val b = EscPosBuilder().cut().build()
        assertThat(b.toList()).containsExactly(0x1D.toByte(), 0x56.toByte(), 0x00.toByte()).inOrder()
    }
}
