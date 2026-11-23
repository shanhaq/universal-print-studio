package com.zyvo.universalprint.engine.layout

/**
 * Layout/fitting engine (Phase 05).
 *
 * Converts physical paper coordinates (mm) to canvas/output coordinates (px) at a given DPI,
 * and provides fitting transforms for content onto a target paper. Stretch is never a silent default.
 */
object LayoutEngine {

    enum class FitMode { ORIGINAL, FIT_WIDTH, FIT_HEIGHT, CONTAIN, FILL_CROP, CUSTOM_PERCENT }

    data class Transform(
        val scaleX: Double,
        val scaleY: Double,
        val offsetX: Double, // in target px
        val offsetY: Double
    )

    /** mm -> px at dpi */
    fun mmToPx(mm: Double, dpi: Int): Double = mm * dpi / 25.4

    /** px -> mm at dpi */
    fun pxToMm(px: Double, dpi: Int): Double = px * 25.4 / dpi

    /**
     * Compute transform to fit content of (contentWmm x contentHmm) onto paper (paperWmm x paperHmm).
     * stretchAllowed must be explicitly true to allow non-uniform scaling.
     */
    fun fit(
        contentWmm: Double,
        contentHmm: Double,
        paperWmm: Double,
        paperHmm: Double,
        mode: FitMode,
        dpi: Int = 150,
        customPercent: Double = 100.0,
        stretchAllowed: Boolean = false
    ): Transform {
        require(contentWmm > 0 && contentHmm > 0 && paperWmm > 0 && paperHmm > 0)
        val cw = mmToPx(contentWmm, dpi)
        val ch = mmToPx(contentHmm, dpi)
        val pw = mmToPx(paperWmm, dpi)
        val ph = mmToPx(paperHmm, dpi)

        val (sx, sy) = when (mode) {
            FitMode.ORIGINAL -> 1.0 to 1.0
            FitMode.FIT_WIDTH -> (pw / cw) to (pw / cw)
            FitMode.FIT_HEIGHT -> (ph / ch) to (ph / ch)
            FitMode.CONTAIN -> {
                val s = minOf(pw / cw, ph / ch)
                s to s
            }
            FitMode.FILL_CROP -> {
                val s = maxOf(pw / cw, ph / ch)
                s to s
            }
            FitMode.CUSTOM_PERCENT -> {
                val s = customPercent / 100.0
                if (stretchAllowed) (s * pw / cw) to (s * ph / ch) else s to s
            }
        }
        val scaledW = cw * sx
        val scaledH = ch * sy
        val ox = (pw - scaledW) / 2.0
        val oy = (ph - scaledH) / 2.0
        return Transform(sx, sy, ox, oy)
    }

    /** Returns true if content (after transform) extends beyond paper bounds (clipping). */
    fun isClipped(t: Transform, contentWmm: Double, contentHmm: Double, paperWmm: Double, paperHmm: Double, dpi: Int): Boolean {
        val cw = mmToPx(contentWmm, dpi); val ch = mmToPx(contentHmm, dpi)
        val pw = mmToPx(paperWmm, dpi); val ph = mmToPx(paperHmm, dpi)
        val sw = cw * t.scaleX; val sh = ch * t.scaleY
        return sw > pw + 0.5 || sh > ph + 0.5
    }

    /** Warn if content resolution is too low for target dpi. */
    fun lowResolution(contentPixelWidth: Int, paperWidthMm: Double, dpi: Int): Boolean {
        val needed = mmToPx(paperWidthMm, dpi)
        return contentPixelWidth < needed * 0.5
    }
}
