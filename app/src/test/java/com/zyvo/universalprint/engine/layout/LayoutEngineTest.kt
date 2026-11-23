package com.zyvo.universalprint.engine.layout

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LayoutEngineTest {
    @Test
    fun contain_scales_uniformly() {
        val t = LayoutEngine.fit(100.0, 100.0, 200.0, 200.0, LayoutEngine.FitMode.CONTAIN, dpi = 100)
        assertThat(t.scaleX).isWithin(0.001).of(2.0)
        assertThat(t.scaleY).isWithin(0.001).of(2.0)
    }

    @Test
    fun contain_does_not_clip() {
        val t = LayoutEngine.fit(100.0, 50.0, 200.0, 200.0, LayoutEngine.FitMode.CONTAIN, dpi = 100)
        assertThat(LayoutEngine.isClipped(t, 100.0, 50.0, 200.0, 200.0, 100)).isFalse()
    }

    @Test
    fun fill_crop_clips() {
        val t = LayoutEngine.fit(100.0, 50.0, 200.0, 200.0, LayoutEngine.FitMode.FILL_CROP, dpi = 100)
        assertThat(LayoutEngine.isClipped(t, 100.0, 50.0, 200.0, 200.0, 100)).isTrue()
    }

    @Test
    fun fit_width_scales_to_width() {
        val t = LayoutEngine.fit(100.0, 50.0, 200.0, 100.0, LayoutEngine.FitMode.FIT_WIDTH, dpi = 100)
        assertThat(t.scaleX).isWithin(0.001).of(2.0)
    }

    @Test
    fun original_no_scale() {
        val t = LayoutEngine.fit(50.0, 50.0, 200.0, 200.0, LayoutEngine.FitMode.ORIGINAL, dpi = 100)
        assertThat(t.scaleX).isWithin(0.001).of(1.0)
        assertThat(t.scaleY).isWithin(0.001).of(1.0)
    }

    @Test
    fun mm_px_roundtrip() {
        val px = LayoutEngine.mmToPx(25.4, 100)
        assertThat(px).isWithin(0.001).of(100.0)
        assertThat(LayoutEngine.pxToMm(px, 100)).isWithin(0.001).of(25.4)
    }

    @Test
    fun invalid_inputs_throw() {
        try {
            LayoutEngine.fit(0.0, 10.0, 10.0, 10.0, LayoutEngine.FitMode.CONTAIN)
            throw AssertionError("expected")
        } catch (e: IllegalArgumentException) { /* ok */ }
    }
}
