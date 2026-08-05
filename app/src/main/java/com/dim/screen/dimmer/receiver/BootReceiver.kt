package com.dim.screen.dimmer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.dim.screen.dimmer.data.DimRepository
import com.dim.screen.dimmer.service.DimService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED
        ) {
            val repository = DimRepository(context)
            if (repository.state.value.enabled && DimService.canDrawOverlays(context)) {
                ContextCompat.startForegroundService(context, Intent(context, DimService::class.java))
            }
        }
    }
}
