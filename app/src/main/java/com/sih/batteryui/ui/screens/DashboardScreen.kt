package com.sih.batteryui.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import com.sih.batteryui.viewmodel.BatteryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: BatteryViewModel, onResetDemo: () -> Unit = {}) {
    val status by viewModel.batteryStatus.collectAsState()
    val isPowerOn by viewModel.isPowerOn.collectAsState()

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text("Battery Dashboard") },
                actions = {
                    IconButton(onClick = onResetDemo) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset Demo")
                    }
                }
            ) 
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Simulated Circular Gauge for SoC
            Card(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isPowerOn) MaterialTheme.colorScheme.surfaceVariant else androidx.compose.ui.graphics.Color(0xFFFFEBEE)
                )
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        progress = status.soc / 100f,
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        strokeWidth = 12.dp,
                        color = if (isPowerOn) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Red
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${String.format("%.1f", status.soc)}%", style = MaterialTheme.typography.headlineLarge)
                        Text(if (isPowerOn) "SoC" else "CUTOFF", style = MaterialTheme.typography.titleMedium, color = if (isPowerOn) androidx.compose.ui.graphics.Color.Unspecified else androidx.compose.ui.graphics.Color.Red)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.togglePower() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPowerOn) androidx.compose.ui.graphics.Color(0xFFD32F2F) else androidx.compose.ui.graphics.Color(0xFF388E3C)
                ),
                modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)
            ) {
                Text(if (isPowerOn) "EMERGENCY SHUTDOWN" else "RESTORE POWER", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                MetricCard("Health (SoH)", "${String.format("%.1f", status.soh)}%")
                MetricCard("Current", if (isPowerOn) "${String.format("%.1f", status.current)} A" else "0.0 A")
            }
        }
    }
}

@Composable
fun MetricCard(title: String, value: String) {
    Card(modifier = Modifier.size(120.dp, 100.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Text(title, style = MaterialTheme.typography.labelMedium)
        }
    }
}
