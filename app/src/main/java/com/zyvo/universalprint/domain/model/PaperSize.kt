package com.zyvo.universalprint.domain.model

/**
 * Physical paper dimensions. Always stored in millimetres to avoid unit confusion.
 * Never truncate decimals — use Double throughout.
 */
data class PaperDimensions(
    val widthMm: Double,
    val heightMm: Double
) {
    init {
        require(widthMm > 0) { "Paper width must be > 0 mm" }
        require(heightMm > 0) { "Paper height must be > 0 mm" }
    }

    val aspectRatio: Double get() = widthMm / heightMm

    fun isLandscape(): Boolean = widthMm > heightMm
    fun isPortrait(): Boolean = heightMm >= widthMm

    /** Convert mm -> inches */
    fun widthInches(): Double = widthMm / 25.4
    fun heightInches(): Double = heightMm / 25.4

    /** Convert mm -> points (1 point = 1/72 inch) */
    fun widthPoints(): Double = widthMm / 25.4 * 72.0
    fun heightPoints(): Double = heightMm / 25.4 * 72.0

    companion object {
        fun fromInches(w: Double, h: Double) = PaperDimensions(w * 25.4, h * 25.4)
        fun fromPoints(w: Double, h: Double) = PaperDimensions(w / 72.0 * 25.4, h / 72.0 * 25.4)
    }
}

enum class PaperCategory {
    ISO, NORTH_AMERICAN, PHOTO, LABEL, RECEIPT, CUSTOM
}

/**
 * A named paper size definition. Immutable catalog entry.
 * version field allows future migration of definitions.
 */
data class PaperSize(
    val id: String,
    val name: String,
    val dimensions: PaperDimensions,
    val category: PaperCategory,
    val version: Int = 1,
    val isCustom: Boolean = false
) {
    val displayName: String get() = if (isCustom) "$name (custom)" else name
}
