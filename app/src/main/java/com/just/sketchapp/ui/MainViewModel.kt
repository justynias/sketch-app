package com.just.sketchapp.ui
import android.util.DisplayMetrics
import androidx.lifecycle.ViewModel
import com.just.sketchapp.data.PaintModel

class MainViewModel(private val paintModel: PaintModel): ViewModel() {
    fun getHeight(){
        //return DisplayMetrics().heightPixels
    }


}