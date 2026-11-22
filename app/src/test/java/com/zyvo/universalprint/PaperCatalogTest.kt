package com.zyvo.universalprint

import com.zyvo.universalprint.domain.model.PaperCatalog
import com.zyvo.universalprint.domain.model.PaperDimensions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PaperCatalogTest {
    @Test
    fun a4DimensionsAreCorrect() {
        val a4 = PaperCatalog.byId("iso-a4")!!
        assertEquals(210.0, a4.dimensions.widthMm, 0.001)
        assertEquals(297.0, a4.dimensions.heightMm, 0.001)
    }

    @Test
    fun customDimensionsPreserveDecimals() {
        val d = PaperDimensions(123.456, 789.012)
        assertEquals(123.456, d.widthMm, 0.0001)
        assertEquals(789.012, d.heightMm, 0.0001)
    }

    @Test
    fun mmToInchesConversion() {
        val d = PaperDimensions(25.4, 25.4)
        assertEquals(1.0, d.widthInches(), 0.0001)
    }

    @Test
    fun catalogHasNoDuplicateIds() {
        val ids = PaperCatalog.all.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun searchWorks() {
        val res = PaperCatalog.search("letter")
        assertTrue(res.isNotEmpty())
    }
}
