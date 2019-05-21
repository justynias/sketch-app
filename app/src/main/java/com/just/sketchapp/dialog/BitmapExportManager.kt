package com.just.sketchapp.dialog

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.just.sketchapp.data.FingerPath

class BitmapExportManager{

    private fun createBitmapFromView(canvas: View): Bitmap {
        val bitmap = Bitmap.createBitmap(canvas.measuredWidth, canvas.measuredHeight, Bitmap.Config.ARGB_8888)
        canvas.draw(Canvas(bitmap))
        return bitmap
    }
    fun saveBitmap(context: Context, canvas: View): Boolean {


        val bitmap = createBitmapFromView(canvas)
        try {
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "new", null)
        } catch (e: Exception) {
            Log.d("EXPORT ERROR", e.message)
            return false
        }
        return true
    }


}

