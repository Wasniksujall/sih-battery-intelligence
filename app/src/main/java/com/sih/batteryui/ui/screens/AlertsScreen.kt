package com.sih.batteryui.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sih.batteryui.data.AlertSeverity
import com.sih.batteryui.viewmodel.BatteryViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(viewModel: BatteryViewModel) {
    val alerts by viewModel.alerts.collectAsState()
    val darkGradient = Brush.verticalGradient(colors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364)))

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text("SECURITY LOGS", fontWeight = FontWeight.ExtraBold, color = Color.White, letterSpacing = 1.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            ) 
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(darkGradient)) {
            if (alerts.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("SYSTEM SECURE", color = Color(0xFF4CAF50), fontSize = 24.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    Text("No unauthorized attempts detected", color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
                    items(alerts) { alert ->
                        val alertColor = when(alert.severity) {
                            AlertSeverity.CRITICAL -> Color(0xFFEF5350)
                            AlertSeverity.HIGH -> Color(0xFFFFCA28)
                            else -> Color(0xFF64B5F6)
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .border(1.dp, alertColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = alert.type.uppercase(), fontWeight = FontWeight.Bold, color = alertColor, letterSpacing = 1.sp)
                                    val dateString = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(alert.timestamp))
                                    Text(text = dateString, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = alert.description, color = Color.White, fontSize = 15.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
