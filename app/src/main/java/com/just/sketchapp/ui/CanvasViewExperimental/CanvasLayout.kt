package com.just.sketchapp.ui.CanvasViewExperimental

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.LifecycleOwner
import java.lang.Exception

abstract class CanvasLayout <V: MvvmCanvasViewState, T: MvvmCanvasViewModel<V>>(
    context: Context,
    attributeSet: AttributeSet?
): View(context, attributeSet), MvvmCustomView<V, T> {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val lifecycleOwner = context as? LifecycleOwner ?: throw Exception()
        onLifecycleOwnerAttached(lifecycleOwner)
    }

    override fun onSaveInstanceState() = MvvmCanvasViewStateWrapper(super.onSaveInstanceState(), viewModel.state)

    @Suppress("UNCHECKED_CAST")
    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is MvvmCanvasViewStateWrapper) {
            viewModel.state = state.state as V?
            super.onRestoreInstanceState(state.superState)
        }
    }
}