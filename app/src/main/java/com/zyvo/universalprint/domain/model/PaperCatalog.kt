package com.zyvo.universalprint.domain.model

/**
 * Catalog of standard paper sizes. Dimensions in mm (precise, never truncated).
 * Source: ISO 216, ANSI/ASME Y14.1, photo paper standards, common label/receipt sizes.
 */
object PaperCatalog {

    private val mm = { w: Double, h: Double -> PaperDimensions(w, h) }

    val all: List<PaperSize> = listOf(
        // ISO A series
        PaperSize("iso-a0", "A0", mm(841.0, 1189.0), PaperCategory.ISO),
        PaperSize("iso-a1", "A1", mm(594.0, 841.0), PaperCategory.ISO),
        PaperSize("iso-a2", "A2", mm(420.0, 594.0), PaperCategory.ISO),
        PaperSize("iso-a3", "A3", mm(297.0, 420.0), PaperCategory.ISO),
        PaperSize("iso-a4", "A4", mm(210.0, 297.0), PaperCategory.ISO),
        PaperSize("iso-a5", "A5", mm(148.0, 210.0), PaperCategory.ISO),
        PaperSize("iso-a6", "A6", mm(105.0, 148.0), PaperCategory.ISO),
        PaperSize("iso-a7", "A7", mm(74.0, 105.0), PaperCategory.ISO),
        PaperSize("iso-a8", "A8", mm(52.0, 74.0), PaperCategory.ISO),
        // ISO B series
        PaperSize("iso-b4", "B4", mm(250.0, 353.0), PaperCategory.ISO),
        PaperSize("iso-b5", "B5", mm(176.0, 250.0), PaperCategory.ISO),
        PaperSize("iso-b6", "B6", mm(125.0, 176.0), PaperCategory.ISO),
        // ISO C (envelope) series
        PaperSize("iso-c4", "C4", mm(229.0, 324.0), PaperCategory.ISO),
        PaperSize("iso-c5", "C5", mm(162.0, 229.0), PaperCategory.ISO),
        PaperSize("iso-c6", "C6", mm(114.0, 162.0), PaperCategory.ISO),
        // North American
        PaperSize("na-letter", "Letter", mm(215.9, 279.4), PaperCategory.NORTH_AMERICAN),
        PaperSize("na-legal", "Legal", mm(215.9, 355.6), PaperCategory.NORTH_AMERICAN),
        PaperSize("na-tabloid", "Tabloid", mm(279.4, 431.8), PaperCategory.NORTH_AMERICAN),
        PaperSize("na-ledger", "Ledger", mm(431.8, 279.4), PaperCategory.NORTH_AMERICAN),
        PaperSize("na-executive", "Executive", mm(184.15, 266.7), PaperCategory.NORTH_AMERICAN),
        PaperSize("na-statement", "Statement", mm(139.7, 215.9), PaperCategory.NORTH_AMERICAN),
        PaperSize("na-folio", "Folio", mm(215.9, 330.2), PaperCategory.NORTH_AMERICAN),
        // Photo
        PaperSize("photo-4x6", "4x6 inch", mm(101.6, 152.4), PaperCategory.PHOTO),
        PaperSize("photo-5x7", "5x7 inch", mm(127.0, 177.8), PaperCategory.PHOTO),
        PaperSize("photo-8x10", "8x10 inch", mm(203.2, 254.0), PaperCategory.PHOTO),
        PaperSize("photo-11x14", "11x14 inch", mm(279.4, 355.6), PaperCategory.PHOTO),
        PaperSize("photo-a4", "A4 Photo", mm(210.0, 297.0), PaperCategory.PHOTO),
        // Labels (common sticker/label sizes)
        PaperSize("label-1x2", "Label 1x2 in", mm(25.4, 50.8), PaperCategory.LABEL),
        PaperSize("label-2x1", "Label 2x1 in", mm(50.8, 25.4), PaperCategory.LABEL),
        PaperSize("label-2x2", "Label 2x2 in", mm(50.8, 50.8), PaperCategory.LABEL),
        PaperSize("label-3x2", "Label 3x2 in", mm(76.2, 50.8), PaperCategory.LABEL),
        PaperSize("label-4x2", "Label 4x2 in", mm(101.6, 50.8), PaperCategory.LABEL),
        PaperSize("label-4x6", "Shipping 4x6", mm(101.6, 152.4), PaperCategory.LABEL),
        // Receipt (thermal)
        PaperSize("receipt-58", "Receipt 58mm", mm(58.0, 210.0), PaperCategory.RECEIPT),
        PaperSize("receipt-80", "Receipt 80mm", mm(80.0, 297.0), PaperCategory.RECEIPT),
        PaperSize("receipt-110", "Receipt 110mm", mm(110.0, 297.0), PaperCategory.RECEIPT)
    )

    fun byId(id: String): PaperSize? = all.firstOrNull { it.id == id }
    fun byCategory(cat: PaperCategory): List<PaperSize> = all.filter { it.category == cat }
    fun search(query: String): List<PaperSize> =
        all.filter { it.name.contains(query, ignoreCase = true) }
}
