package com.dim.screen.dimmer.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dim.screen.dimmer.data.DimRepository
import com.dim.screen.dimmer.data.DimState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DimViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DimRepository(application)

    val state: StateFlow<DimState> = repository.state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DimState()
    )

    fun setEnabled(enabled: Boolean) {
        repository.setEnabled(enabled)
    }

    fun setBrightness(brightness: Int) {
        repository.setBrightness(brightness)
    }
}
