package com.sih.batteryui.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothSearching
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun BluetoothConnectScreen(onConnectionSuccess: () -> Unit) {
    var connectionState by remember { mutableStateOf(0) } 
    // 0 = Scanning, 1 = Found BMS, 2 = Handshake, 3 = Success

    val infiniteTransition = rememberInfiniteTransition(label = "radar")
    val radarScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.5f,
        animationSpec = infiniteRepeatable(animation = tween(1500, easing = LinearOutSlowInEasing), repeatMode = RepeatMode.Restart),
        label = "radarScale"
    )
    val radarAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f, targetValue = 0f,
        animationSpec = infiniteRepeatable(animation = tween(1500, easing = LinearOutSlowInEasing), repeatMode = RepeatMode.Restart),
        label = "radarAlpha"
    )

    LaunchedEffect(connectionState) {
        when (connectionState) {
            0 -> { delay(2500); connectionState = 1 }
            2 -> { delay(3000); connectionState = 3 }
            3 -> { delay(1500); onConnectionSuccess() }
        }
    }

    val darkGradient = Brush.verticalGradient(colors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364)))

    Column(
        modifier = Modifier.fillMaxSize().background(darkGradient).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
            if (connectionState == 0 || connectionState == 2) {
                Box(modifier = Modifier.size(120.dp).scale(radarScale).clip(CircleShape).background(Color(0xFF4CAF50).copy(alpha = radarAlpha)))
            }
            Icon(
                imageVector = if (connectionState >= 2) Icons.Default.Lock else Icons.Default.BluetoothSearching,
                contentDescription = "Bluetooth",
                modifier = Modifier.size(80.dp),
                tint = if (connectionState >= 2) Color(0xFF4CAF50) else Color(0xFF64B5F6)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        when (connectionState) {
            0 -> {
                CircularProgressIndicator(color = Color(0xFF64B5F6))
                Spacer(modifier = Modifier.height(24.dp))
                Text("SCANNING FOR VEHICLE BMS...", style = MaterialTheme.typography.titleMedium, color = Color.White, letterSpacing = 2.sp)
                Text("Ensuring isolated environment", color = Color.Gray, fontSize = 12.sp)
            }
            1 -> {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0x334CAF50)), shape = CircleShape) {
                    Text("VEHICLE FOUND: E-RICKSHAW [TX-902]", fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(16.dp))
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { connectionState = 2 },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.height(56.dp).fillMaxWidth(0.8f)
                ) {
                    Text("INITIATE SECURE HANDSHAKE", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
            }
            2 -> {
                CircularProgressIndicator(color = Color(0xFF4CAF50))
                Spacer(modifier = Modifier.height(24.dp))
                Text("PERFORMING DIGITAL HANDSHAKE", style = MaterialTheme.typography.titleMedium, color = Color.White, letterSpacing = 1.5.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Verifying 256-bit Cryptographic Token...", color = Color(0xFF81C784), fontSize = 14.sp)
            }
            3 -> {
                Text("ACCESS GRANTED", color = Color(0xFF4CAF50), fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
                Text("Connection is cryptographically secure", color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}
