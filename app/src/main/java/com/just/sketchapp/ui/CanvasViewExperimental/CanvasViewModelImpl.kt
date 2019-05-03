package com.just.sketchapp.ui.CanvasViewExperimental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CanvasViewModelImpl : MvvmCanvasViewModel<CanvasViewState> {
    private val liveData = MutableLiveData<Int?>()


//    private val _color = MutableLiveData<Int?>()
//    fun getColor(): LiveData<Int?> = _color


    override var state: CanvasViewState? = null
        get() = CanvasViewState(liveData.value)
        set(value) {
            field = value
            restore(value)
        }

    fun getLiveData(): LiveData<Int?> = liveData


    private fun restore(state: CanvasViewState?) {
        liveData.value = state?.title
    }
}