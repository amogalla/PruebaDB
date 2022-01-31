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
        private val sleepNightKey: Long = 0L,
        val database: EntradaDatabaseDao) : ViewModel() {

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }

    fun onSetSleepQuality(quality: Int) {
        viewModelScope.launch {
                val tonight = database.get(sleepNightKey) ?: return@launch
                tonight.sleepQuality = quality
                database.update(tonight)

            // Setting this state variable to true will alert the observer and trigger navigation.
            _navigateToSleepTracker.value = true
        }
    }
}

