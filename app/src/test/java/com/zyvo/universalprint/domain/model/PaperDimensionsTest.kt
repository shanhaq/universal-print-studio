package com.zyvo.universalprint.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PaperDimensionsTest {
    @Test
    fun conversion_roundTrips() {
        val d = PaperDimensions.fromInches(8.5, 11.0)
        assertThat(d.widthMm).isWithin(0.01).of(215.9)
        assertThat(d.heightMm).isWithin(0.01).of(279.4)
        assertThat(d.widthInches()).isWithin(0.001).of(8.5)
        assertThat(d.heightInches()).isWithin(0.001).of(11.0)
    }

    @Test
    fun points_conversion() {
        val d = PaperDimensions.fromPoints(612.0, 792.0) // 8.5x11 in points
        assertThat(d.widthMm).isWithin(0.1).of(215.9)
        assertThat(d.heightMm).isWithin(0.1).of(279.4)
    }

    @Test
    fun aspectRatio_and_orientation() {
        val portrait = PaperDimensions(210.0, 297.0)
        assertThat(portrait.aspectRatio).isWithin(0.001).of(210.0 / 297.0)
        assertThat(portrait.isPortrait()).isTrue()
        assertThat(portrait.isLandscape()).isFalse()
    }

    @Test
    fun invalid_dimensions_throw() {
        try {
            PaperDimensions(0.0, 10.0)
            throw AssertionError("expected exception")
        } catch (e: IllegalArgumentException) { /* ok */ }
        try {
            PaperDimensions(10.0, -1.0)
            throw AssertionError("expected exception")
        } catch (e: IllegalArgumentException) { /* ok */ }
    }
}
