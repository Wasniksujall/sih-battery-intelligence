package com.sih.batteryui.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sih.batteryui.viewmodel.BatteryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringScreen(viewModel: BatteryViewModel) {
    val status by viewModel.batteryStatus.collectAsState()
    val isPowerOn by viewModel.isPowerOn.collectAsState()
    val darkGradient = Brush.verticalGradient(colors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364)))

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text("LIVE TELEMETRY", fontWeight = FontWeight.ExtraBold, color = Color.White, letterSpacing = 1.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            ) 
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(darkGradient)) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MonitoringCard("Voltage", "${String.format("%.2f", status.voltage)} V", Color(0xFF64B5F6))
                MonitoringCard("Current", if (isPowerOn) "${String.format("%.2f", status.current)} A" else "0.00 A", Color(0xFFFFD54F))
                MonitoringCard("Power", if (isPowerOn) "${String.format("%.2f", status.voltage * status.current / 1000)} kW" else "0.00 kW", Color(0xFF4CAF50))
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x33FFFFFF))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("PREDICTIVE MAINTENANCE", color = Color(0xFF90CAF9), fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Based on current cell degradation trends and thermal limits, next physical service is recommended in:", color = Color.LightGray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Text("142 DAYS", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}

@Composable
fun MonitoringCard(label: String, value: String, accentColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x33FFFFFF))
    ) {
        Row(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, style = MaterialTheme.typography.titleMedium, color = Color.LightGray)
            Text(value, style = MaterialTheme.typography.titleLarge, color = accentColor, fontWeight = FontWeight.Bold)
        }
    }
}
