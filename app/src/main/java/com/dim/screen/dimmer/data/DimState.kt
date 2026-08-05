package com.dim.screen.dimmer.data

data class DimState(
    val enabled: Boolean = false,
    val brightness: Int = 50
) {
    val alpha: Float get() = (brightness / 100f).coerceIn(0f, 1f)
}
