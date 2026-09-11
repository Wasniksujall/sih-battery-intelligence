package com.sih.batteryui.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PowerOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sih.batteryui.viewmodel.BatteryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: BatteryViewModel, onResetDemo: () -> Unit = {}) {
    val status by viewModel.batteryStatus.collectAsState()
    val isPowerOn by viewModel.isPowerOn.collectAsState()

    val darkGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
    )

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text("VEHICLE DASHBOARD", fontWeight = FontWeight.ExtraBold, color = Color.White, letterSpacing = 1.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(onClick = onResetDemo) {
                        Icon(Icons.Default.Logout, contentDescription = "Reset Demo", tint = Color(0xFFEF5350))
                    }
                }
            ) 
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(darkGradient)) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Sleek Circular Gauge for SoC
                Card(
                    modifier = Modifier
                        .size(260.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(130.dp),
                    elevation = CardDefaults.elevatedCardElevation(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isPowerOn) Color(0x22FFFFFF) else Color(0x44D32F2F)
                    )
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            progress = status.soc / 100f,
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            strokeWidth = 18.dp,
                            strokeCap = StrokeCap.Round,
                            color = if (isPowerOn) Color(0xFF4CAF50) else Color(0xFFEF5350),
                            trackColor = Color.White.copy(alpha = 0.1f)
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${String.format("%.1f", status.soc)}%", fontSize = 56.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                            Text(if (isPowerOn) "BATTERY LEVEL" else "POWER CUTOFF", style = MaterialTheme.typography.labelLarge, color = if (isPowerOn) Color(0xFF81C784) else Color(0xFFFF8A80), letterSpacing = 1.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Emergency Button
                Button(
                    onClick = { viewModel.togglePower() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPowerOn) Color(0xFFD32F2F) else Color(0xFF388E3C)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Icon(if (isPowerOn) Icons.Default.PowerOff else Icons.Default.Bolt, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (isPowerOn) "EMERGENCY SHUTDOWN" else "RESTORE POWER", 
                        fontSize = 18.sp, 
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Metric Cards Row
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Health (SoH)", 
                        value = "${String.format("%.1f", status.soh)}%", 
                        icon = Icons.Default.Favorite,
                        iconColor = Color(0xFFF06292)
                    )
                    MetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Current Draw", 
                        value = if (isPowerOn) "${String.format("%.1f", status.current)} A" else "0.0 A", 
                        icon = Icons.Default.Bolt,
                        iconColor = Color(0xFFFFD54F)
                    )
                }
            }
        }
    }
}

@Composable
fun MetricCard(modifier: Modifier = Modifier, title: String, value: String, icon: ImageVector, iconColor: Color) {
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x33FFFFFF)) // Glassmorphism effect
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, style = MaterialTheme.typography.labelMedium, color = Color(0xFFB0BEC5))
            }
            Text(value, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
