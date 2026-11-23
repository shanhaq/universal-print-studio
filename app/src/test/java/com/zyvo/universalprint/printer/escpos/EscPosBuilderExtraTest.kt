package com.zyvo.universalprint.printer.escpos

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EscPosBuilderExtraTest {
    @Test
    fun align_center() {
        val b = EscPosBuilder().align(EscPosBuilder.Align.CENTER).build()
        assertThat(b.toList()).containsExactly(0x1B.toByte(), 0x61.toByte(), 0x01.toByte()).inOrder()
    }

    @Test
    fun underline_on() {
        val b = EscPosBuilder().underline(true).build()
        assertThat(b.toList()).containsExactly(0x1B.toByte(), 0x2D.toByte(), 0x01.toByte()).inOrder()
    }

    @Test
    fun textSize() {
        val b = EscPosBuilder().textSize(2, 2).build()
        assertThat(b[0]).isEqualTo(0x1D.toByte())
        assertThat(b[1]).isEqualTo(0x21.toByte())
    }

    @Test
    fun feed() {
        val b = EscPosBuilder().feed(3).build()
        assertThat(b.toList()).containsExactly(0x1B.toByte(), 0x64.toByte(), 0x03.toByte()).inOrder()
    }

    @Test
    fun drawer_pulse() {
        val b = EscPosBuilder().openDrawer().build()
        assertThat(b[0]).isEqualTo(0x1B.toByte())
        assertThat(b[1]).isEqualTo(0x70.toByte())
    }

    @Test
    fun barcode_contains_code() {
        val b = EscPosBuilder().barcode("12345").build()
        assertThat(String(b)).contains("12345")
    }
}
