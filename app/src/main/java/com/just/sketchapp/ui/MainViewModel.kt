package com.just.sketchapp.ui
import android.graphics.Color
import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.just.sketchapp.data.PaintModel

class MainViewModel(private val paintModel: PaintModel): ViewModel() {

    private val _color = MutableLiveData<Int?>()
    private val _size = MutableLiveData<Int?>()

    init{
        _color.value = Color.RED
    }

    fun getColor(): LiveData<Int?> = _color
    fun setColor(color: Int){
        _color.value = color
    }

    fun getSize(): LiveData<Int?> = _size

fun  onSizeChanged(seekBar: SeekBar, progressValue: Int, fromUser: Boolean) {
    _size.value = progressValue
    }


}