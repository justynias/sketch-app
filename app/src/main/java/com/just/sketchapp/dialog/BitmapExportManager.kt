package com.just.sketchapp.dialog

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.provider.MediaStore
import android.util.Log
import com.just.sketchapp.data.FingerPath

class BitmapExportManager { //bitmap: Bitmap,

    private val paint: Paint = Paint()
    private val bitmapPaint: Paint = Paint(Paint.DITHER_FLAG)

    init{
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.xfermode = null
        paint.alpha = 0xff
    }

    fun saveBitmap(context: Context,  paths: List<FingerPath>?, width: Int, height: Int) {


        val bitmap = createBitmap(paths, width, height)
        try {
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "new", null)
        } catch (e: Exception) {
            Log.d("EXPORT ERROR", e.message)
        }
    }

     private fun createBitmap(paths: List<FingerPath>?, width: Int, height: Int): Bitmap{

         val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
         val canvas = Canvas(bitmap)

         canvas.drawColor(Color.WHITE)
             paths?.forEach {
                paint.color = it.color
                paint.strokeWidth = it.brushSize.toFloat()
                paint.maskFilter = null

           canvas.drawPath(it.path, paint)
        }
        canvas.drawBitmap(bitmap, 0.0f, 0.0f,  bitmapPaint)
         return bitmap
    }
}

