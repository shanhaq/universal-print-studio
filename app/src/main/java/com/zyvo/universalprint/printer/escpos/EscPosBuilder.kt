package com.zyvo.universalprint.printer.escpos

/**
 * ESC/POS command builder (Phase 08).
 * Capabilities are NOT assumed; callers should check the selected printer profile.
 * All commands are byte-level and unit-tested.
 */
class EscPosBuilder {
    private val buf = java.io.ByteArrayOutputStream()

    fun init(): EscPosBuilder { buf.write(byteArrayOf(0x1B, 0x40)); return this }
    fun text(s: String, charset: java.nio.charset.Charset = Charsets.UTF_8): EscPosBuilder {
        buf.write(s.toByteArray(charset)); return this
    }
    fun newline(): EscPosBuilder { buf.write(byteArrayOf(0x0A)); return this }
    fun bold(on: Boolean): EscPosBuilder { buf.write(byteArrayOf(0x1B, 0x45, if (on) 1 else 0)); return this }
    fun underline(on: Boolean): EscPosBuilder { buf.write(byteArrayOf(0x1B, 0x2D, if (on) 1 else 0)); return this }
    fun align(mode: Align): EscPosBuilder { buf.write(byteArrayOf(0x1B, 0x61, mode.code.toByte())); return this }
    fun textSize(width: Int = 1, height: Int = 1): EscPosBuilder {
        val w = (width - 1).coerceIn(0, 7)
        val h = (height - 1).coerceIn(0, 7)
        buf.write(byteArrayOf(0x1D, 0x21, ((w shl 4) or h).toByte())); return this
    }
    fun feed(n: Int): EscPosBuilder { buf.write(byteArrayOf(0x1B, 0x64, n.coerceIn(0, 255).toByte())); return this }
    fun cut(partial: Boolean = false): EscPosBuilder {
        buf.write(byteArrayOf(0x1D, 0x56, if (partial) 0x01 else 0x00)); return this
    }
    fun openDrawer(pin: Int = 0): EscPosBuilder {
        buf.write(byteArrayOf(0x1B, 0x70, pin.toByte(), 0x19.toByte(), 0xFA.toByte())); return this
    }
    fun qr(data: String, size: Int = 6): EscPosBuilder {
        val bytes = data.toByteArray(Charsets.UTF_8)
        // ESC/POS QR model 2 (simplified): store data then print
        buf.write(byteArrayOf(0x1D, 0x28, 0x6B, 0x04, 0x00, 0x31, 0x41, 0x32, 0x00))
        buf.write(byteArrayOf(0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x43, size.coerceIn(1, 16).toByte()))
        val len = bytes.size + 3
        buf.write(byteArrayOf(0x1D, 0x28, 0x6B, (len and 0xFF).toByte(), ((len shr 8) and 0xFF).toByte(), 0x31, 0x50, 0x30))
        buf.write(bytes)
        buf.write(byteArrayOf(0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x51, 0x30))
        return this
    }
    fun barcode(code: String, type: BarcodeType = BarcodeType.CODE128): EscPosBuilder {
        buf.write(byteArrayOf(0x1D, 0x6B, type.code.toByte()))
        buf.write(byteArrayOf(code.length.toByte()))
        buf.write(code.toByteArray(Charsets.UTF_8)); return this
    }
    fun build(): ByteArray = buf.toByteArray()

    enum class Align(val code: Int) { LEFT(0), CENTER(1), RIGHT(2) }
    enum class BarcodeType(val code: Int) { CODE128(73), CODE39(65), EAN13(67) }
}
