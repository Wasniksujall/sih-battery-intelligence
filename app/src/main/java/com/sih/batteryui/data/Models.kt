package com.sih.batteryui.data

data class BatteryStatus(
    val voltage: Float = 0f,
    val current: Float = 0f,
    val temperature: Float = 0f,
    val soc: Float = 0f, // State of Charge %
    val soh: Float = 100f // State of Health %
)

enum class AlertSeverity { LOW, MEDIUM, HIGH, CRITICAL }

data class SecurityAlert(
    val id: String,
    val timestamp: Long,
    val type: String,
    val description: String,
    val severity: AlertSeverity
)
