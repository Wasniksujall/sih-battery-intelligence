package com.sih.batteryui.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sih.batteryui.data.AlertSeverity
import com.sih.batteryui.viewmodel.BatteryViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(viewModel: BatteryViewModel) {
    val alerts by viewModel.alerts.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Security & Alerts") }) }
    ) { padding ->
        if (alerts.isEmpty()) {
            Text("No active alerts", modifier = Modifier.padding(padding).padding(16.dp))
        } else {
            LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
                items(alerts) { alert ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when(alert.severity) {
                                AlertSeverity.CRITICAL -> Color(0xFFFFCDD2)
                                AlertSeverity.HIGH -> Color(0xFFFFF9C4)
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = alert.type, style = MaterialTheme.typography.titleMedium)
                            Text(text = alert.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            val dateString = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(alert.timestamp))
                            Text(text = "Time: $dateString", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}
