package com.just.sketchapp.ui

import android.content.Context
import android.graphics.*
import android.view.View
import android.util.AttributeSet
import android.view.MotionEvent
import com.just.sketchapp.data.FingerPath
import kotlin.math.absoluteValue
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log

import androidx.annotation.ColorInt
import androidx.databinding.InverseMethod
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.just.sketchapp.R
import java.lang.Exception


class CanvasView(context: Context, attr: AttributeSet?): View(context, attr){

    private var brushSize: Int = 0
    private var brushColor : Int  = Color.WHITE
    private val backgroundColor: Int = Color.WHITE
    private val touchTolerance:Float = 4.0f
    private var mX :Float? = null
    private var mY :Float? = null
    private var paths  = mutableListOf<FingerPath>()
    private lateinit var path: Path
    private var paint: Paint
    private lateinit var bitmap: Bitmap
    private val mCanvas: Canvas
    private var bitmapPaint: Paint = Paint(Paint.DITHER_FLAG)



    fun createBitmapFromView(): Canvas{

         mCanvas.drawBitmap(bitmap, 0.0f, 0.0f,  bitmapPaint)
        return mCanvas
    }

    fun setColor(@ColorInt color: Int) {
        brushColor = color
        paint.color = brushColor

    }

    fun setSize(size: Int) {
        brushSize = size
    }

    fun setPaths(path: MutableList<FingerPath>) {
        paths = path
        invalidate()
    }



    init{

        paint= Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.color = brushColor
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.xfermode = null
        paint.alpha = 0xff

        bitmap = Bitmap.createBitmap(context.resources.displayMetrics.widthPixels, context.resources.displayMetrics.heightPixels, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(bitmap)

    }


    private fun touchStart(x: Float, y: Float){
        path = Path()
        val fp = FingerPath(brushColor, brushSize, path)
        paths.add(fp)
        path.reset()
        path.moveTo(x,y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y:Float) {
        (mX != null && mY != null).let {
            val dx = (x - mX!!).absoluteValue
            val dy = (y - mY!!).absoluteValue

            if (dx >= touchTolerance && dy >= touchTolerance) {
                path.quadTo(mX!!, mY!!, (x + mX!!) / 2, (y + mY!!) / 2)
                mX = x
                mY = y
            }
        }
    }

    private fun touchUp(){
        (mX != null && mY != null ).let {
            path.lineTo(mX!!, mY!!)
        }
    }


    override fun onDraw(canvas: Canvas) {
        canvas.save()
        mCanvas.drawColor(backgroundColor)

        paths.forEach {
            paint.color = it.color
            paint.strokeWidth = it.brushSize.toFloat()
            paint.maskFilter = null

            mCanvas.drawPath(it.path, paint)
        }
     canvas.drawBitmap(bitmap, 0.0f, 0.0f,  bitmapPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
       val x  = event.x
       val y = event.y
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()}
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate() }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }


}