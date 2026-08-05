package com.dim.screen.dimmer.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.dim.screen.dimmer.BuildConfig
import com.dim.screen.dimmer.data.DimRepository

class DimService : Service() {

    private var windowManager: WindowManager? = null
    private var overlayView: View? = null
    private var overlayParams: WindowManager.LayoutParams? = null
    private lateinit var repository: DimRepository

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        repository = DimRepository(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (BuildConfig.DEBUG) {
            android.util.Log.i("DimService", "onStartCommand: canDraw=" + canDrawOverlays(this))
        }
        if (!canDrawOverlays(this)) {
            if (BuildConfig.DEBUG) {
                android.util.Log.w("DimService", "Sem permissao de overlay, parando")
            }
            stopSelf()
            return START_NOT_STICKY
        }

        if (overlayView == null) {
            try {
                ensureForeground()
                createOverlay()
                if (BuildConfig.DEBUG) {
                    android.util.Log.i("DimService", "Overlay criado com sucesso")
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    android.util.Log.e("DimService", "Falha ao criar overlay", e)
                }
                stopSelf()
                return START_NOT_STICKY
            }
        }
        repository.refresh()
        applyState()
        return START_STICKY
    }

    private fun ensureForeground() {
        NotificationHelper.ensureChannel(this)
        val notification = NotificationHelper.buildNotification(this)
        startForeground(NotificationHelper.NOTIFICATION_ID, notification)
    }

    private fun createOverlay() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val view = View(this)
        view.setBackgroundColor(0xFF000000.toInt())
        view.alpha = 1f

        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val bounds = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager!!.maximumWindowMetrics.bounds
        } else {
            @Suppress("DEPRECATION")
            val metrics = resources.displayMetrics
            android.graphics.Rect(0, 0, metrics.widthPixels, metrics.heightPixels)
        }

        val params = WindowManager.LayoutParams(
            bounds.width(),
            bounds.height(),
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
            }
        }

        try {
            windowManager?.addView(view, params)
            overlayView = view
            overlayParams = params
        } catch (e: Exception) {
            overlayView = null
            stopSelf()
        }
    }

    private fun applyState() {
        val state = repository.state.value
        overlayView?.alpha = state.alpha
        val params = overlayParams ?: return
        params.screenBrightness = -1f
        runCatching {
            windowManager?.updateViewLayout(overlayView, params)
        }
    }

    override fun onDestroy() {
        overlayView?.let { view ->
            runCatching { windowManager?.removeView(view) }
        }
        overlayView = null
        windowManager = null
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (BuildConfig.DEBUG) {
            android.util.Log.i("DimService", "onTaskRemoved: enabled=" + repository.state.value.enabled)
        }
        if (repository.state.value.enabled) {
            val restart = PendingIntent.getForegroundService(
                this,
                0,
                Intent(this, DimService::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val alarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarm.setAlarmClock(
                AlarmManager.AlarmClockInfo(System.currentTimeMillis() + 1000L, null),
                restart
            )
            if (BuildConfig.DEBUG) {
                android.util.Log.i("DimService", "Reinicio agendado via setAlarmClock")
            }
        }
        super.onTaskRemoved(rootIntent)
    }

    companion object {
        fun canDrawOverlays(context: Context): Boolean =
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                Settings.canDrawOverlays(context)
    }
}
