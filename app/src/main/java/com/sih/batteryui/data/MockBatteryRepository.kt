package com.sih.batteryui.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import kotlin.random.Random

class MockBatteryRepository {

    fun getLiveBatteryStatus(): Flow<BatteryStatus> = flow {
        var currentVoltage = 380f
        var currentAmps = 15f
        var currentTemp = 32f
        var currentSoc = 85f
        
        while (true) {
            // Simulate slight fluctuations in readings
            currentVoltage += Random.nextFloat() * 2 - 1
            currentAmps += Random.nextFloat() * 4 - 2
            currentTemp += Random.nextFloat() * 0.5f - 0.25f
            currentSoc -= 0.01f // Discharging slowly

            // Clamp values
            if (currentVoltage > 400f) currentVoltage = 400f
            if (currentVoltage < 300f) currentVoltage = 300f
            if (currentTemp > 60f) currentTemp = 60f
            if (currentSoc < 0f) currentSoc = 0f

            emit(
                BatteryStatus(
                    voltage = currentVoltage,
                    current = currentAmps,
                    temperature = currentTemp,
                    soc = currentSoc,
                    soh = 95f
                )
            )
            delay(1000) // Emit every second
        }
    }

    fun getSecurityAlerts(): Flow<List<SecurityAlert>> = flow {
        val alerts = mutableListOf<SecurityAlert>()
        
        // Initial mock alerts
        alerts.add(SecurityAlert(UUID.randomUUID().toString(), System.currentTimeMillis() - 3600000, "Anomaly", "Slight voltage drop detected in Cell 4", AlertSeverity.LOW))
        emit(alerts.toList())
        
        while(true) {
            delay(15000) // Random alert every 15 seconds for demo purposes
            if (Random.nextBoolean()) {
                alerts.add(0, SecurityAlert(
                    id = UUID.randomUUID().toString(),
                    timestamp = System.currentTimeMillis(),
                    type = "Overheating",
                    description = "Temperature spike detected above threshold.",
                    severity = AlertSeverity.HIGH
                ))
                emit(alerts.toList())
            }
        }
    }
}
