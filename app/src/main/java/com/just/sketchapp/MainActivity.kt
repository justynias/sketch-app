package com.just.sketchapp

import android.content.DialogInterface
import android.hardware.display.DisplayManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.skydoves.colorpickerview.listeners.ColorListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize custom sketch view
        val sketchView = findViewById<SketchView>(R.id.sketchView)
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        sketchView.initMetrics(metrics)


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
