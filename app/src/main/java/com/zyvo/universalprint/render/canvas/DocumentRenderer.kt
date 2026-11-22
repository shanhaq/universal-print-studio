package com.zyvo.universalprint.render.canvas

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import com.zyvo.universalprint.domain.model.Document
import com.zyvo.universalprint.domain.model.DocumentLayer
import com.zyvo.universalprint.domain.model.LayerType

/**
 * Renders a [Document] to a [Bitmap] at the requested DPI.
 * 1 mm = dpi/25.4 pixels.
 */
object DocumentRenderer {

    fun render(document: Document, dpi: Int = 150): Bitmap {
        val pxPerMm = dpi / 25.4
        val w = (document.paperSize.dimensions.widthMm * pxPerMm).toInt().coerceAtLeast(1)
        val h = (document.paperSize.dimensions.heightMm * pxPerMm).toInt().coerceAtLeast(1)
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        canvas.drawColor(Color.WHITE)

        val sorted = document.layers.filter { it.visible }.sortedBy { it.zIndex }
        for (layer in sorted) {
            drawLayer(canvas, layer, pxPerMm)
        }
        return bmp
    }

    private fun drawLayer(canvas: Canvas, layer: DocumentLayer, pxPerMm: Double) {
        val x = (layer.xMm * pxPerMm).toFloat()
        val y = (layer.yMm * pxPerMm).toFloat()
        val w = (layer.widthMm * pxPerMm).toFloat()
        val h = (layer.heightMm * pxPerMm).toFloat()
        when (layer.type) {
            LayerType.TEXT -> {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = (layer.textColor ?: 0xFF000000).toInt()
                    textSize = ((layer.fontSizePt ?: 12.0) * (pxPerMm / 25.4 * 72.0 / 72.0)).toFloat() // pt -> px approx
                    typeface = Typeface.create(layer.fontFamily, styleOf(layer.bold, layer.italic))
                }
                canvas.drawText(layer.text ?: "", x, y + paint.textSize, paint)
            }
            LayerType.SHAPE -> {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    style = Paint.Style.FILL
                    color = (layer.fillColor ?: 0xFFCCCCCC).toInt()
                }
                canvas.drawRect(x, y, x + w, y + h, paint)
                layer.strokeColor?.let { sc ->
                    val sp = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                        style = Paint.Style.STROKE
                        color = sc.toInt()
                        strokeWidth = ((layer.strokeWidthMm ?: 0.5) * pxPerMm).toFloat()
                    }
                    canvas.drawRect(x, y, x + w, y + h, sp)
                }
            }
            LayerType.LINE -> {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    style = Paint.Style.STROKE
                    color = (layer.strokeColor ?: 0xFF000000).toInt()
                    strokeWidth = ((layer.strokeWidthMm ?: 0.5) * pxPerMm).toFloat()
                }
                canvas.drawLine(x, y, x + w, y + h, paint)
            }
            LayerType.IMAGE -> {
                // Image loading handled by Coil in UI; renderer draws placeholder
                val paint = Paint().apply { color = Color.LTGRAY }
                canvas.drawRect(x, y, x + w, y + h, paint)
            }
        }
    }

    private fun styleOf(bold: Boolean, italic: Boolean): Int = when {
        bold && italic -> Typeface.BOLD_ITALIC
        bold -> Typeface.BOLD
        italic -> Typeface.ITALIC
        else -> Typeface.NORMAL
    }
}
