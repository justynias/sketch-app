package com.just.sketchapp.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.just.sketchapp.databinding.ActivityMainBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import com.just.sketchapp.R
import com.just.sketchapp.dialog.BitmapExportManager
import com.just.sketchapp.dialog.ColorPickerManager
import com.todo.shakeit.core.ShakeDetector
import com.todo.shakeit.core.ShakeListener
import kotlinx.android.synthetic.main.activity_main.*


private const val MY_PERMISSION_WRITE_EXTERNAL_STORAGE = 1


class MainActivity : AppCompatActivity(), KodeinAware,  ShakeListener {


    override val kodein by kodein()
    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var mainViewModel: MainViewModel
    private val colorPickerManager: ColorPickerManager by instance()
    private val bitmapExportManager: BitmapExportManager by instance()
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init viewmodel and data binding
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = mainViewModel
            this.setVariable(BR.viewModel, mainViewModel)
            this.executePendingBindings()
        }

        //init button listener
        val colorButton = findViewById<ImageButton>(R.id.color)
        colorButton?.setOnClickListener {
            colorPickerManager.showColorPicker(this) {
                mainViewModel.setColor(it)
            }
        }
        val cancelButton = findViewById<ImageButton>(R.id.cancel)
        cancelButton?.setOnClickListener {
            mainViewModel.clearPaths()
        }
        val downloadButton = findViewById<ImageButton>(R.id.download)
        downloadButton?.setOnClickListener {
            val pics = findViewById<CanvasView>(R.id.canvasView)
            requestWritePermission() // need to refactor requests
            if (hasFilePermission()) {
                if(bitmapExportManager.saveBitmap(this, canvasView))
                    Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
            }
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
        ) == PackageManager.PERMISSION_GRANTED
    }


    override fun onShake() {
        ShakeDetector.unRegisterForShakeEvent(this)
        showDialog()
    }

    private fun showDialog() {
        // Initialize a new instance of alert dialog builder object
        var builder = AlertDialog.Builder(this).setMessage("Are you sure you want to delete your work permamently? ")

        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
               DialogInterface.BUTTON_POSITIVE -> {mainViewModel.clearPaths()
                ShakeDetector.registerForShakeEvent(this)}
                DialogInterface.BUTTON_NEUTRAL -> ShakeDetector.registerForShakeEvent(this)
            }
        }
        builder.setPositiveButton("YES", dialogClickListener)
        builder.setNeutralButton("CANCEL", dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }

    override fun onPause() {
        ShakeDetector.unRegisterForShakeEvent(this)
        super.onPause()
    }

    override fun onResume() {
        ShakeDetector.registerForShakeEvent(this)
        super.onResume()
    }
}
