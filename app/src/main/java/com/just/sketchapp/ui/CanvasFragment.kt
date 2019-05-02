package com.just.sketchapp.ui
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.just.sketchapp.R
import com.just.sketchapp.databinding.CanvasFragmentBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CanvasFragment: Fragment(), KodeinAware{

    override val kodein by kodein()

    private val canvasViewModelFactory: CanvasViewModelFactory by instance()
    private lateinit var canvasViewModel: CanvasViewModel

    companion object {
        fun newInstance() = CanvasFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.canvas_fragment, container, false)
//        val binding: CanvasFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.canvas_fragment, container,false)
//        binding.viewModel = canvasViewModel
//       binding.lifecycleOwner = this
//       return binding.root

//
       // return inflater.inflate(R.layout.canvas_fragment, container, false)
        //Log.d("WIDOK", sketchFragment.toString())
        //return sketchFragment
        //val sketchView = sketchFragment.findViewById<SketchView>(R.id.sketchView)
        //sketchView.initMetrics(resources.displayMetrics)

        //return sketchFragment
//        windowManager.defaultDisplay.getMetrics(metrics)
//        Log.d("ROZMIAR", metrics.heightPixels.toString())
//        sketchView.initMetrics(metrics)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        canvasViewModel = ViewModelProviders.of(this, canvasViewModelFactory).get(CanvasViewModel::class.java)

    }


}