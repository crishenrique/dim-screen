package com.dim.screen.dimmer.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DimRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _state = MutableStateFlow(
        DimState(
            enabled = prefs.getBoolean(KEY_ENABLED, false),
            brightness = prefs.getInt(KEY_BRIGHTNESS, DEFAULT_BRIGHTNESS)
        )
    )
    val state: StateFlow<DimState> = _state.asStateFlow()

    fun setEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_ENABLED, enabled).apply()
        _state.value = _state.value.copy(enabled = enabled)
    }

    fun setBrightness(brightness: Int) {
        val safe = brightness.coerceIn(0, 100)
        prefs.edit().putInt(KEY_BRIGHTNESS, safe).apply()
        _state.value = _state.value.copy(brightness = safe)
    }

    fun refresh() {        _state.value = DimState(
            enabled = prefs.getBoolean(KEY_ENABLED, false),
            brightness = prefs.getInt(KEY_BRIGHTNESS, DEFAULT_BRIGHTNESS)
        )
    }

    companion object {
        private const val PREFS_NAME = "dim_screen_prefs"
        private const val KEY_ENABLED = "enabled"
        private const val KEY_BRIGHTNESS = "brightness"
        private const val DEFAULT_BRIGHTNESS = 50
    }
}
