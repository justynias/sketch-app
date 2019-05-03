package com.just.sketchapp.ui.CanvasViewExperimental

import androidx.lifecycle.LifecycleOwner


interface MvvmCustomView<V: MvvmCanvasViewState, T: MvvmCanvasViewModel<V>> {
    val viewModel: T

    fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner)
}