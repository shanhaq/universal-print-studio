package com.zyvo.universalprint.util

import android.content.Context
import android.graphics.Bitmap
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import androidx.print.PrintHelper

object PrintHelperUtil {
    fun printBitmap(context: Context, jobName: String, bitmap: Bitmap) {
        val printHelper = PrintHelper(context)
        printHelper.scaleMode = PrintHelper.SCALE_MODE_FIT
        printHelper.printBitmap(jobName, bitmap)
    }
}
