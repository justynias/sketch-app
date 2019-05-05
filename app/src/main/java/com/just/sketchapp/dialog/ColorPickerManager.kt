package com.just.sketchapp.dialog

import android.content.Context
import android.content.DialogInterface
import com.just.sketchapp.R
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class ColorPickerManager {

    fun showColorPicker(context: Context, onConfirmButton: (Int) -> Unit) {
       // val header = context.getString(R.string.dialog_choose_color_header)

        ColorPickerDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert)
            .setTitle("Choose your brush color")
            .setPositiveButton("ok", object: ColorEnvelopeListener{
                override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                    onConfirmButton(envelope!!.color)
                }
            })
            .setNegativeButton("cancel", object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })
            .setPreferenceName("MyColorPicker")
            .show()
    }

}