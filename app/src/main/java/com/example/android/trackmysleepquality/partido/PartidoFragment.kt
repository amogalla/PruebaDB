package com.example.android.trackmysleepquality.partido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.EntradaDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding

class PartidoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = EntradaDatabase.getInstance(application).entradaDatabaseDao

        val viewModelFactory = PartidoViewModelFactory(dataSource, application)

        val partidoViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(PartidoViewModel::class.java)

        binding.partidoViewModel = partidoViewModel

        binding.lifecycleOwner = this

        // Add an Observer on the state variable for Navigating when STOP button is pressed.
        partidoViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                this.findNavController().navigate(
                        PartidoFragmentDirections
                                .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                partidoViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
