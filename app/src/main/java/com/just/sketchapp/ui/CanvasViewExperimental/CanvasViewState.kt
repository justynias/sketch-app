package com.just.sketchapp.ui.CanvasViewExperimental

import android.graphics.Color
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CanvasViewState(
    val title: Int? = Color.BLUE
):MvvmCanvasViewState