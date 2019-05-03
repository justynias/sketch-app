package com.just.sketchapp.ui.CanvasViewExperimental

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MvvmCanvasViewStateWrapper(
    val superState: Parcelable?,
    val state: MvvmCanvasViewState?
): Parcelable