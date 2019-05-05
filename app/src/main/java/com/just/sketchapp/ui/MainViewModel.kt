package com.just.sketchapp.ui
import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.just.sketchapp.data.FingerPath
import com.just.sketchapp.data.PaintModel

class MainViewModel(private val paintModel: PaintModel): ViewModel() {

    private var _paths  = MutableLiveData<MutableList<FingerPath>?>()
    private val _color = MutableLiveData<Int?>()
    private val _size = MutableLiveData<Int?>()

    init{
        _color.value = Color.RED
        _paths.value = mutableListOf<FingerPath>()
        _size.value =30

    }

    fun getColor(): LiveData<Int?> = _color
    fun setColor(color: Int){
        _color.value = color
    }


    fun setPaths(paths: MutableList<FingerPath>){
        _paths.value = paths
    }

    fun getPaths(): LiveData<MutableList<FingerPath>?> = _paths


    fun getSize(): LiveData<Int?> = _size

    fun  onSizeChanged(seekBar: SeekBar, progressValue: Int, fromUser: Boolean) {
        _size.value = progressValue
    }
    fun clearPaths(){
            setPaths(mutableListOf<FingerPath>())
    }



}

