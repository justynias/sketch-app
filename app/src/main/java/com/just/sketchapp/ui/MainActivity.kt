package com.just.sketchapp.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.just.sketchapp.R
import com.just.sketchapp.databinding.ActivityMainBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {


    override val kodein by kodein()
    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //initialize custom sketch view
       // val sketchView = findViewById<SketchView>(R.id.sketchView)



        //init viewmodel and data binding
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = mainViewModel
        }


        val imageButton = findViewById<ImageButton>(R.id.color)
        imageButton?.setOnClickListener {
          ColorPickerDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert)
            .setTitle("Choose your brush color")
              .setPositiveButton("ok", object: ColorEnvelopeListener {
                override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                    Log.d("COLOR PICKED", envelope?.hexCode)
                } })
              .setNegativeButton("cancel", object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })
              .setPreferenceName("MyColorPicker")
            .show()
        }

    }


}
