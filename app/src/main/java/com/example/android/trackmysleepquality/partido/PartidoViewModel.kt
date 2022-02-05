
package com.example.android.trackmysleepquality.partido

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.EntradaDatabaseDao
import com.example.android.trackmysleepquality.database.Entrada
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for PartidoFragment.
 */
class PartidoViewModel(
    val database: EntradaDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private var tonight = MutableLiveData<Entrada?>()

    private val nights = database.getAllNights()

    /**
     * Converted nights to Spanned for displaying.
     */
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    private val _navigateToSleepQuality = MutableLiveData<Entrada>()

    val navigateToSleepQuality: LiveData<Entrada>
        get() = _navigateToSleepQuality

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): Entrada? {
            var entrada = database.getEntrada()
            if (entrada?.grada != entrada?.grada) {
                entrada = null
            }
            return entrada
    }

    private suspend fun clear() {
            database.clear() 
    }

    private suspend fun update(night: Entrada) {
            database.update(night)
    }

    private suspend fun insert(night: Entrada) {
            database.insert(night)
    }

    /**
     * Executes when the START button is clicked.
     */
    fun seleccionDePartido() {
        viewModelScope.launch {
            val entradaNueva = Entrada()

            insert(entradaNueva)

            tonight.value = getTonightFromDatabase()

            viewModelScope.launch {
                val oldNight = tonight.value ?: return@launch
                //oldNight.endTimeMilli = System.currentTimeMillis()
                update(oldNight)
                _navigateToSleepQuality.value = oldNight
            }

        }
    }

    /**
     * Executes when the STOP button is clicked.
     */
    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value ?: return@launch

            //oldNight.endTimeMilli = System.currentTimeMillis()

            update(oldNight)

            _navigateToSleepQuality.value = oldNight
        }
    }

    /**
     * Executes when the CLEAR button is clicked.
     */
    fun onClear() {
        viewModelScope.launch {
            // Clear the database table.
            clear()

            // And clear tonight since it's no longer in the database
            tonight.value = null
        }
    }

    /**
     */
}
