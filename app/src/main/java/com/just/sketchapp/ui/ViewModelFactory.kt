package com.just.sketchapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.just.sketchapp.data.PaintModel

class ViewModelFactory (private val paintModel: PaintModel): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(paintModel) as T
    }
}