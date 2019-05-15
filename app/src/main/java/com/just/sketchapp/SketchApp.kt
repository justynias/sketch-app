package com.just.sketchapp

import android.app.Application
import com.just.sketchapp.data.PaintModel
import com.just.sketchapp.dialog.BitmapExportManager
import com.just.sketchapp.dialog.ColorPickerManager
import com.just.sketchapp.ui.ViewModelFactory
import com.todo.shakeit.core.ShakeIt
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class SketchApp: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@SketchApp))
        bind () from provider { ViewModelFactory(instance()) }
        bind() from singleton { PaintModel() }
        bind() from singleton { ColorPickerManager() }
        bind() from singleton { BitmapExportManager() }

    }

    override fun onCreate() {
        super.onCreate()
        ShakeIt.init(this)
    }
}
