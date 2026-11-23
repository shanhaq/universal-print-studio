package com.zyvo.universalprint.domain.model

/**
 * Catalog of standard paper sizes. Dimensions in mm (precise, never truncated).
 * Source: ISO 216, JIS P 0138, ANSI/ASME Y14.1, photo paper standards,
 * common envelope/label/card/thermal sizes. Unverified sizes are NOT included;
 * use Custom Paper Manager for those.
 */
object PaperCatalog {

    private val mm = { w: Double, h: Double -> PaperDimensions(w, h) }

    val all: List<PaperSize> = listOf(
        // ---- ISO A series (ISO 216) ----
        PaperSize("iso-a0", "A0", mm(841.0, 1189.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a1", "A1", mm(594.0, 841.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a2", "A2", mm(420.0, 594.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a3", "A3", mm(297.0, 420.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a4", "A4", mm(210.0, 297.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a5", "A5", mm(148.0, 210.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a6", "A6", mm(105.0, 148.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a7", "A7", mm(74.0, 105.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a8", "A8", mm(52.0, 74.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a9", "A9", mm(37.0, 52.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-a10", "A10", mm(26.0, 37.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        // ---- ISO B series ----
        PaperSize("iso-b0", "B0", mm(1000.0, 1414.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b1", "B1", mm(707.0, 1000.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b2", "B2", mm(500.0, 707.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b3", "B3", mm(353.0, 500.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b4", "B4", mm(250.0, 353.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b5", "B5", mm(176.0, 250.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b6", "B6", mm(125.0, 176.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b7", "B7", mm(88.0, 125.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b8", "B8", mm(62.0, 88.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b9", "B9", mm(44.0, 62.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        PaperSize("iso-b10", "B10", mm(31.0, 44.0), PaperCategory.ISO, standardCode = "ISO 216", region = "Global", sourceName = "ISO 216", isStandard = true),
        // ---- ISO C (envelope) series ----
        PaperSize("iso-c0", "C0", mm(917.0, 1297.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        PaperSize("iso-c1", "C1", mm(648.0, 917.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        PaperSize("iso-c2", "C2", mm(458.0, 648.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        PaperSize("iso-c3", "C3", mm(324.0, 458.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        PaperSize("iso-c4", "C4", mm(229.0, 324.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        PaperSize("iso-c5", "C5", mm(162.0, 229.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        PaperSize("iso-c6", "C6", mm(114.0, 162.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        PaperSize("iso-c7", "C7", mm(81.0, 114.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        PaperSize("iso-c8", "C8", mm(57.0, 81.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        PaperSize("iso-dl", "DL", mm(110.0, 220.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        // ---- JIS B series (JIS P 0138) ----
        PaperSize("jis-b0", "JIS B0", mm(1030.0, 1456.0), PaperCategory.JIS, standardCode = "JIS P 0138", region = "Japan", sourceName = "JIS P 0138", isStandard = true),
        PaperSize("jis-b1", "JIS B1", mm(728.0, 1030.0), PaperCategory.JIS, standardCode = "JIS P 0138", region = "Japan", sourceName = "JIS P 0138", isStandard = true),
        PaperSize("jis-b2", "JIS B2", mm(515.0, 728.0), PaperCategory.JIS, standardCode = "JIS P 0138", region = "Japan", sourceName = "JIS P 0138", isStandard = true),
        PaperSize("jis-b3", "JIS B3", mm(364.0, 515.0), PaperCategory.JIS, standardCode = "JIS P 0138", region = "Japan", sourceName = "JIS P 0138", isStandard = true),
        PaperSize("jis-b4", "JIS B4", mm(257.0, 364.0), PaperCategory.JIS, standardCode = "JIS P 0138", region = "Japan", sourceName = "JIS P 0138", isStandard = true),
        PaperSize("jis-b5", "JIS B5", mm(182.0, 257.0), PaperCategory.JIS, standardCode = "JIS P 0138", region = "Japan", sourceName = "JIS P 0138", isStandard = true),
        PaperSize("jis-b6", "JIS B6", mm(128.0, 182.0), PaperCategory.JIS, standardCode = "JIS P 0138", region = "Japan", sourceName = "JIS P 0138", isStandard = true),
        PaperSize("jis-b7", "JIS B7", mm(91.0, 128.0), PaperCategory.JIS, standardCode = "JIS P 0138", region = "Japan", sourceName = "JIS P 0138", isStandard = true),
        // ---- ANSI (ANSI/ASME Y14.1) ----
        PaperSize("ansi-a", "ANSI A (Letter)", mm(215.9, 279.4), PaperCategory.ANSI, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        PaperSize("ansi-b", "ANSI B (Tabloid)", mm(279.4, 431.8), PaperCategory.ANSI, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        PaperSize("ansi-c", "ANSI C", mm(431.8, 558.8), PaperCategory.ANSI, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        PaperSize("ansi-d", "ANSI D", mm(558.8, 863.6), PaperCategory.ANSI, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        PaperSize("ansi-e", "ANSI E", mm(863.6, 1117.6), PaperCategory.ANSI, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        // ---- ARCH ----
        PaperSize("arch-a", "Arch A", mm(228.6, 304.8), PaperCategory.ARCH, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        PaperSize("arch-b", "Arch B", mm(304.8, 457.2), PaperCategory.ARCH, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        PaperSize("arch-c", "Arch C", mm(457.2, 609.6), PaperCategory.ARCH, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        PaperSize("arch-d", "Arch D", mm(609.6, 914.4), PaperCategory.ARCH, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        PaperSize("arch-e", "Arch E", mm(914.4, 1219.2), PaperCategory.ARCH, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        PaperSize("arch-e1", "Arch E1", mm(762.0, 1066.8), PaperCategory.ARCH, standardCode = "ANSI/ASME Y14.1", region = "US", sourceName = "ANSI/ASME Y14.1", isStandard = true),
        // ---- North American common ----
        PaperSize("na-letter", "Letter", mm(215.9, 279.4), PaperCategory.NORTH_AMERICAN, standardCode = "ANSI", region = "US/Canada", sourceName = "ANSI", isStandard = true),
        PaperSize("na-legal", "Legal", mm(215.9, 355.6), PaperCategory.NORTH_AMERICAN, standardCode = "ANSI", region = "US/Canada", sourceName = "ANSI", isStandard = true),
        PaperSize("na-tabloid", "Tabloid", mm(279.4, 431.8), PaperCategory.NORTH_AMERICAN, standardCode = "ANSI", region = "US/Canada", sourceName = "ANSI", isStandard = true),
        PaperSize("na-ledger", "Ledger", mm(431.8, 279.4), PaperCategory.NORTH_AMERICAN, standardCode = "ANSI", region = "US/Canada", sourceName = "ANSI", isStandard = true),
        PaperSize("na-executive", "Executive", mm(184.15, 266.7), PaperCategory.NORTH_AMERICAN, standardCode = "ANSI", region = "US", sourceName = "ANSI", isStandard = true),
        PaperSize("na-statement", "Statement", mm(139.7, 215.9), PaperCategory.NORTH_AMERICAN, standardCode = "ANSI", region = "US", sourceName = "ANSI", isStandard = true),
        PaperSize("na-folio", "Folio", mm(215.9, 330.2), PaperCategory.NORTH_AMERICAN, standardCode = "ANSI", region = "US/Philippines", sourceName = "ANSI", isStandard = true),
        PaperSize("na-junior-legal", "Junior Legal", mm(127.0, 203.2), PaperCategory.NORTH_AMERICAN, standardCode = "ANSI", region = "US", sourceName = "ANSI", isStandard = true),
        // ---- Photo ----
        PaperSize("photo-2x3", "Photo 2x3 in", mm(50.8, 76.2), PaperCategory.PHOTO, region = "Global", isStandard = true),
        PaperSize("photo-3x5", "Photo 3x5 in", mm(76.2, 127.0), PaperCategory.PHOTO, region = "Global", isStandard = true),
        PaperSize("photo-4x6", "Photo 4x6 in", mm(101.6, 152.4), PaperCategory.PHOTO, region = "Global", isStandard = true),
        PaperSize("photo-5x7", "Photo 5x7 in", mm(127.0, 177.8), PaperCategory.PHOTO, region = "Global", isStandard = true),
        PaperSize("photo-8x10", "Photo 8x10 in", mm(203.2, 254.0), PaperCategory.PHOTO, region = "Global", isStandard = true),
        PaperSize("photo-11x14", "Photo 11x14 in", mm(279.4, 355.6), PaperCategory.PHOTO, region = "Global", isStandard = true),
        PaperSize("photo-a4", "A4 Photo", mm(210.0, 297.0), PaperCategory.PHOTO, region = "Global", isStandard = true),
        // ---- Envelopes (common) ----
        PaperSize("env-monarch", "Monarch", mm(98.4, 190.5), PaperCategory.ENVELOPE, region = "US", isStandard = true),
        PaperSize("env-us10", "US #10", mm(104.8, 241.3), PaperCategory.ENVELOPE, region = "US", isStandard = true),
        PaperSize("env-dl", "DL", mm(110.0, 220.0), PaperCategory.ENVELOPE, standardCode = "ISO 269", region = "Global", sourceName = "ISO 269", isStandard = true),
        // ---- Cards ----
        PaperSize("card-business-iso", "Business Card (ISO)", mm(85.0, 55.0), PaperCategory.CARD, region = "Global", isStandard = true),
        PaperSize("card-business-us", "Business Card (US)", mm(88.9, 50.8), PaperCategory.CARD, region = "US", isStandard = true),
        PaperSize("card-id-1", "ID Card (CR80)", mm(85.6, 53.98), PaperCategory.CARD, region = "Global", isStandard = true),
        // ---- Labels ----
        PaperSize("label-1x2", "Label 1x2 in", mm(25.4, 50.8), PaperCategory.LABEL, region = "Global", isStandard = true),
        PaperSize("label-2x1", "Label 2x1 in", mm(50.8, 25.4), PaperCategory.LABEL, region = "Global", isStandard = true),
        PaperSize("label-2x2", "Label 2x2 in", mm(50.8, 50.8), PaperCategory.LABEL, region = "Global", isStandard = true),
        PaperSize("label-3x2", "Label 3x2 in", mm(76.2, 50.8), PaperCategory.LABEL, region = "Global", isStandard = true),
        PaperSize("label-4x2", "Label 4x2 in", mm(101.6, 50.8), PaperCategory.LABEL, region = "Global", isStandard = true),
        PaperSize("label-4x6", "Shipping 4x6", mm(101.6, 152.4), PaperCategory.LABEL, region = "Global", isStandard = true),
        // ---- Receipt / Thermal rolls (width x nominal height) ----
        PaperSize("receipt-57", "Receipt 57mm", mm(57.0, 297.0), PaperCategory.RECEIPT, isRollMedia = true, region = "Global", isStandard = true),
        PaperSize("receipt-58", "Receipt 58mm", mm(58.0, 297.0), PaperCategory.RECEIPT, isRollMedia = true, region = "Global", isStandard = true),
        PaperSize("receipt-76", "Receipt 76mm", mm(76.0, 297.0), PaperCategory.RECEIPT, isRollMedia = true, region = "Global", isStandard = true),
        PaperSize("receipt-80", "Receipt 80mm", mm(80.0, 297.0), PaperCategory.RECEIPT, isRollMedia = true, region = "Global", isStandard = true),
        PaperSize("receipt-82", "Receipt 82.5mm", mm(82.5, 297.0), PaperCategory.RECEIPT, isRollMedia = true, region = "Global", isStandard = true),
        PaperSize("receipt-110", "Receipt 110mm", mm(110.0, 297.0), PaperCategory.RECEIPT, isRollMedia = true, region = "Global", isStandard = true),
        PaperSize("thermal-57", "Thermal 57mm (roll)", mm(57.0, 1000.0), PaperCategory.THERMAL, isRollMedia = true, region = "Global", isStandard = true),
        PaperSize("thermal-80", "Thermal 80mm (roll)", mm(80.0, 1000.0), PaperCategory.THERMAL, isRollMedia = true, region = "Global", isStandard = true)
    )

    fun byId(id: String): PaperSize? = all.firstOrNull { it.id == id }
    fun byCategory(cat: PaperCategory): List<PaperSize> = all.filter { it.category == cat }
    fun search(query: String): List<PaperSize> =
        all.filter { it.name.contains(query, ignoreCase = true) || (it.standardCode?.contains(query, ignoreCase = true) == true) }
    fun favorites(): List<PaperSize> = all.filter { it.isFavorite }
}
