package com.example.android.trackmysleepquality.grada

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.trackmysleepquality.database.EntradaDatabaseDao
import kotlinx.coroutines.*

/**
 * ViewModel for GradaFragment.
 *
 * @param sleepNightKey The key of the current night we are working on.
 */
class GradaViewModel(
        private val entradaKey: Long = 0L,
        val database: EntradaDatabaseDao) : ViewModel() {

    private val _navigateToGrada = MutableLiveData<Boolean?>()

    val navigateToGrada: LiveData<Boolean?>
        get() = _navigateToGrada

    fun doneNavigating() {
        _navigateToGrada.value = null
    }

    fun onSetSleepQuality(gradaElegida: Int) {
        viewModelScope.launch {
            val entrada = database.get(entradaKey) ?: return@launch
            when(gradaElegida){
                0 -> entrada.grada = "Tribuna"
                1 -> entrada.grada = "Preferencia"
                2-> entrada.grada = "Fondo sur"
                3 -> entrada.grada = "Fondo norte"
            }
            database.update(entrada)

            // Setting this state variable to true will alert the observer and trigger navigation.
            _navigateToGrada.value = true
        }
    }
}

