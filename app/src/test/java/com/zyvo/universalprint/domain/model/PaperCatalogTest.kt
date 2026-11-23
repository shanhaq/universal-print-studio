package com.zyvo.universalprint.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PaperCatalogTest {
    @Test
    fun ids_unique() {
        val ids = PaperCatalog.all.map { it.id }
        assertThat(ids).hasSize(ids.toSet().size)
    }

    @Test
    fun all_dimensions_positive() {
        PaperCatalog.all.forEach { p ->
            assertThat(p.dimensions.widthMm).isGreaterThan(0.0)
            assertThat(p.dimensions.heightMm).isGreaterThan(0.0)
        }
    }

    @Test
    fun byId_finds_a4() {
        assertThat(PaperCatalog.byId("iso-a4")?.name).isEqualTo("A4")
    }

    @Test
    fun search_finds_by_name() {
        assertThat(PaperCatalog.search("letter").isNotEmpty()).isTrue()
    }

    @Test
    fun categories_non_empty() {
        PaperCategory.values().forEach { /* enum sanity */ }
        assertThat(PaperCatalog.all.size).isGreaterThan(50)
    }
}
