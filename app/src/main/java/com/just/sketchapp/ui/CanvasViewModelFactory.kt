package com.just.sketchapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CanvasViewModelFactory : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CanvasViewModel() as T
    }
}