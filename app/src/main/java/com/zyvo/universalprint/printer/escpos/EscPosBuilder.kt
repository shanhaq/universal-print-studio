package com.zyvo.universalprint.printer.escpos

/**
 * Minimal ESC/POS command builder. Commands vary by printer; this covers the
 * common subset. Always verify against the target printer's manual.
 */
class EscPosBuilder {
    private val buf = java.io.ByteArrayOutputStream()

    fun init(): EscPosBuilder { buf.write(byteArrayOf(0x1B, 0x40)); return this }
    fun text(s: String): EscPosBuilder { buf.write(s.toByteArray(Charsets.UTF_8)); return this }
    fun newline(): EscPosBuilder { buf.write(byteArrayOf(0x0A)); return this }
    fun bold(on: Boolean): EscPosBuilder { buf.write(byteArrayOf(0x1B, 0x45, if (on) 1 else 0)); return this }
    fun align(center: Boolean): EscPosBuilder { buf.write(byteArrayOf(0x1B, 0x61, if (center) 1 else 0)); return this }
    fun cut(): EscPosBuilder { buf.write(byteArrayOf(0x1D, 0x56, 0x00)); return this }
    fun feed(n: Int): EscPosBuilder { buf.write(byteArrayOf(0x1B, 0x64, n.toByte())); return this }
    fun build(): ByteArray = buf.toByteArray()
}
