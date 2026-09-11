package com.sih.batteryui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sih.batteryui.data.BatteryStatus
import com.sih.batteryui.data.MockBatteryRepository
import com.sih.batteryui.data.SecurityAlert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BatteryViewModel : ViewModel() {
    private val repository = MockBatteryRepository()

    private val _isPowerOn = MutableStateFlow(true)
    val isPowerOn: StateFlow<Boolean> = _isPowerOn

    private val _batteryStatus = MutableStateFlow(BatteryStatus())
    // Expose status directly as state flow, but we'll read isPowerOn in the UI to zero out current
    val batteryStatus: StateFlow<BatteryStatus> = _batteryStatus

    private val _alerts = MutableStateFlow<List<SecurityAlert>>(emptyList())
    val alerts: StateFlow<List<SecurityAlert>> = _alerts

    init {
        viewModelScope.launch {
            repository.getLiveBatteryStatus().collect { status ->
                _batteryStatus.value = status
            }
        }
        viewModelScope.launch {
            repository.getSecurityAlerts().collect { alertList ->
                _alerts.value = alertList
            }
        }
    }

    fun togglePower() {
        _isPowerOn.value = !_isPowerOn.value
    }
}
