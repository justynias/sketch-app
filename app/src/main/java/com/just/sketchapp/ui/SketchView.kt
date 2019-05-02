package com.just.sketchapp.ui

import android.content.Context
import android.graphics.*
import android.view.View
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import com.just.sketchapp.data.FingerPath
import kotlin.math.absoluteValue

class SketchView(context: Context, attr: AttributeSet?): View(context, attr) {

    val brushSize: Int = 20
    val brushColor : Int  = Color.RED
    val backgroundColor: Int = Color.WHITE
    val touchTolerance:Float = 4.0f
    private var mX :Float? = null
    private var mY :Float? = null
    private var paths  = mutableListOf<FingerPath>()
    private lateinit var path: Path
    private var paint: Paint
    private lateinit var bitmap: Bitmap

    private lateinit var mCanvas: Canvas
    private var bitmapPaint: Paint = Paint(Paint.DITHER_FLAG)


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
        val fp = FingerPath(brushColor, brushSize, path!!)

        paths.add(fp)

        path.reset()
        path.moveTo(x,y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y:Float) {
        //check mx, my ?
        (mX != null && mY != null).let {
            val dx = (x - mX!!).absoluteValue
            val dy = (y - mY!!).absoluteValue

            if (dx!! >= touchTolerance && dy >= touchTolerance) {
                path?.quadTo(mX!!, mY!!, (x + mX!!) / 2, (y + mY!!) / 2)
                mX = x
                mY = y
            }
        }
    }

    private fun touchUp(){
        (mX != null && mY != null ).let {
            path?.lineTo(mX!!, mY!!)
        }
    }


    override fun onDraw(canvas: Canvas) {
        canvas.save()
        mCanvas.drawColor(backgroundColor)

        paths?.forEach {
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