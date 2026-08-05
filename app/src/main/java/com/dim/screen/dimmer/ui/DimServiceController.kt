package com.dim.screen.dimmer.ui

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.dim.screen.dimmer.service.DimService

object DimServiceController {

    fun start(context: Context) {
        val intent = Intent(context, DimService::class.java)
        ContextCompat.startForegroundService(context, intent)
    }

    fun stop(context: Context) {
        val intent = Intent(context, DimService::class.java)
        context.stopService(intent)
    }
}
