package com.example.android.gestiondeentradas.grada

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
import gestiondeentradas.databinding.FragmentGradaBinding

class GradaFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_grada.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentGradaBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_grada, container, false)

        val application = requireNotNull(this.activity).application

        val arguments = GradaFragmentArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val dataSource = EntradaDatabase.getInstance(application).entradaDatabaseDao
        val viewModelFactory = GradaViewModelFactory(arguments.sleepNightKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val gradaViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(GradaViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.gradaViewModel = gradaViewModel

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        gradaViewModel.navigateToGrada.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                        GradaFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())

                gradaViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
