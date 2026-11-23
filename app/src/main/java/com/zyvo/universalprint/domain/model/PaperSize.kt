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
    ISO, JIS, ANSI, ARCH, NORTH_AMERICAN, REGIONAL, PHOTO, ENVELOPE, LABEL, CARD, RECEIPT, THERMAL, MANUFACTURER, CUSTOM
}

/**
 * A named paper size definition. Immutable catalog entry.
 * version field allows future migration of definitions.
 */
enum class PaperOrientation { PORTRAIT, LANDSCAPE, EITHER }

data class PaperSize(
    val id: String,
    val name: String,
    val dimensions: PaperDimensions,
    val category: PaperCategory,
    // Provenance / standards metadata (Phase 02)
    val standardCode: String? = null,        // e.g. "ISO 216", "ANSI/ASME Y14.1"
    val region: String? = null,              // e.g. "Global", "US", "Japan"
    val sourceName: String? = null,          // standards body / dataset name
    val sourceUrl: String? = null,           // reference URL
    val revision: String? = null,            // standard revision/version
    val isStandard: Boolean = false,        // verified standard entry
    val isCustom: Boolean = false,
    val isFavorite: Boolean = false,
    val isRollMedia: Boolean = false,        // thermal/continuous roll
    val defaultOrientation: PaperOrientation = PaperOrientation.EITHER,
    val version: Int = 1,
    val metadata: Map<String, String> = emptyMap()
) {
    val displayName: String get() = if (isCustom) "$name (custom)" else name
    val widthMm: Double get() = dimensions.widthMm
    val heightMm: Double get() = dimensions.heightMm
}
