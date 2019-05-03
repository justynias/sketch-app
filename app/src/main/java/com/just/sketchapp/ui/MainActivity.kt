package com.just.sketchapp.ui

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.just.sketchapp.databinding.ActivityMainBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.FileOutputStream
import java.io.IOException
import java.io.File.separator
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.just.sketchapp.R
import java.io.File

private const val MY_PERMISSION_WRITE_EXTERNAL_STORAGE = 1


class MainActivity : AppCompatActivity(), KodeinAware {

//temporary
    lateinit var sketchView: SketchView
    override val kodein by kodein()
    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //initialize custom sketch view
       sketchView = findViewById<SketchView>(R.id.sketchView)



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
            testExport()

        }

    }
    private fun requestWritePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
    }


    private fun hasFilePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )  == PackageManager.PERMISSION_GRANTED
    }

    private fun createBitmapFromView(view: View): Bitmap{
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        view.draw(Canvas(bitmap))
        return bitmap
    }
    fun testExport(){

        requestWritePermission()
        val path = Environment.getExternalStorageDirectory().toString() + File.separator + "test"
        if(hasFilePermission()) {

            try {

              val pics = findViewById<SketchView>(R.id.sketchView)
                val uri = MediaStore.Images.Media.insertImage(contentResolver, createBitmapFromView(pics),"new", null )
                val inp = contentResolver.openInputStream(uri.toUri())

            } catch (e: Exception) {
                Log.d("SCIEZKA", e.message)
            }
        }

    }
}
