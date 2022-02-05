package com.example.android.gestiondeentradas.partido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.gestiondeentradas.database.EntradaDatabase
import gestiondeentradas.R
import gestiondeentradas.databinding.FragmentPartidoBinding

class PartidoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentPartidoBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_partido, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = EntradaDatabase.getInstance(application).entradaDatabaseDao

        val viewModelFactory = PartidoViewModelFactory(dataSource, application)

        val partidoViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(PartidoViewModel::class.java)

        binding.partidoViewModel = partidoViewModel

        binding.lifecycleOwner = this

        // Add an Observer on the state variable for Navigating when STOP button is pressed.
        partidoViewModel.navigateToGrada.observe(viewLifecycleOwner, Observer { entrada ->
            entrada?.let {
                this.findNavController().navigate(
                        PartidoFragmentDirections
                                .actionSleepTrackerFragmentToSleepQualityFragment(entrada.id_entrada))
                partidoViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
