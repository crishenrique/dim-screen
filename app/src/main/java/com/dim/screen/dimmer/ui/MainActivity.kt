package com.dim.screen.dimmer.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dim.screen.dimmer.R
import com.dim.screen.dimmer.databinding.ActivityMainBinding
import com.dim.screen.dimmer.service.DimService
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DimViewModel by viewModels()

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            handlePermissionResult()
        }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            handlePermissionResult()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeState()
    }

    private fun setupListeners() {
        binding.switchDim.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableDimming()
            } else {
                viewModel.setEnabled(false)
                DimServiceController.stop(this)
            }
        }

        binding.applyButton.setOnClickListener {
            viewModel.setBrightness(binding.seekbar.value.toInt())
            enableDimming()
        }

        binding.seekbar.addOnChangeListener { _, value, _ ->
            binding.percentText.text = "${value.toInt()}%"
            viewModel.setBrightness(value.toInt())
            if (viewModel.state.value.enabled) {
                DimServiceController.start(this)
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.switchDim.isChecked = state.enabled
                binding.seekbar.value = state.brightness.toFloat()
                binding.percentText.text = "${state.brightness}%"
            }
        }
    }

    private fun enableDimming() {
        if (Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) !=
            android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            return
        }
        if (DimService.canDrawOverlays(this)) {
            viewModel.setEnabled(true)
            DimServiceController.start(this)
        } else {
            requestOverlayPermission()
        }
    }

    private fun requestOverlayPermission() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(R.string.overlay_permission_title)
            .setMessage(R.string.overlay_permission_message)
            .setPositiveButton(R.string.overlay_permission_continue) { _, _ -> openOverlaySettings() }
            .setNegativeButton(R.string.overlay_permission_later, null)
            .show()
    }

    private fun openOverlaySettings() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        overlayPermissionLauncher.launch(intent)
    }

    private fun handlePermissionResult() {
        if (DimService.canDrawOverlays(this)) {
            viewModel.setEnabled(true)
            DimServiceController.start(this)
        } else {
            viewModel.setEnabled(false)
            binding.switchDim.isChecked = false
        }
    }
}
