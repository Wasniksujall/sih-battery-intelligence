package com.sih.batteryui.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sih.batteryui.viewmodel.BatteryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringScreen(viewModel: BatteryViewModel) {
    val status by viewModel.batteryStatus.collectAsState()
    val isPowerOn by viewModel.isPowerOn.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Real-Time Monitoring") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MonitoringCard("Voltage", "${String.format("%.2f", status.voltage)} V")
            MonitoringCard("Current", if (isPowerOn) "${String.format("%.2f", status.current)} A" else "0.00 A")
            MonitoringCard("Power", if (isPowerOn) "${String.format("%.2f", status.voltage * status.current / 1000)} kW" else "0.00 kW")
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Predictive Maintenance", style = MaterialTheme.typography.titleLarge)
            Text("Based on current trends, next maintenance is recommended in 142 Days.")
        }
    }
}

@Composable
fun MonitoringCard(label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, style = MaterialTheme.typography.titleMedium)
            Text(value, style = MaterialTheme.typography.titleMedium)
        }
    }
}
