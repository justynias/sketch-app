package com.just.sketchapp.ui.CanvasViewExperimental

interface MvvmCanvasViewModel<T: MvvmCanvasViewState> {
    var state: T?
}