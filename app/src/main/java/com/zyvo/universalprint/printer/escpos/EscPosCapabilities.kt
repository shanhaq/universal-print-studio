package com.zyvo.universalprint.printer.escpos

/**
 * Capability flags for an ESC/POS printer (Phase 08/11).
 * Never assume capabilities; populate from verified profile or successful capability test.
 */
data class EscPosCapabilities(
    val supportsBold: Boolean = true,
    val supportsUnderline: Boolean = true,
    val supportsAlign: Boolean = true,
    val supportsSize: Boolean = true,
    val supportsCut: Boolean = false,
    val supportsDrawer: Boolean = false,
    val supportsQr: Boolean = false,
    val supportsBarcode: Boolean = false,
    val supportsStatusQuery: Boolean = false,
    val codePage: String = "UTF-8",
    val maxCharsPerLine: Int = 32
)
