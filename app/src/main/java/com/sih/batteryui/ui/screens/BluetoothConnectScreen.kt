package com.sih.batteryui.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothSearching
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun BluetoothConnectScreen(onConnectionSuccess: () -> Unit) {
    var connectionState by remember { mutableStateOf(0) } 
    // 0 = Scanning, 1 = Found BMS, 2 = Handshake, 3 = Success

    LaunchedEffect(connectionState) {
        when (connectionState) {
            0 -> {
                delay(2000) // Scan for 2 seconds
                connectionState = 1
            }
            2 -> {
                delay(2500) // Simulate secret token handshake
                connectionState = 3
            }
            3 -> {
                delay(1000) // Wait 1 second on success screen
                onConnectionSuccess()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (connectionState >= 2) Icons.Default.Lock else Icons.Default.BluetoothSearching,
            contentDescription = "Bluetooth",
            modifier = Modifier.size(80.dp),
            tint = if (connectionState >= 2) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (connectionState) {
            0 -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Scanning for Rickshaw BMS...", style = MaterialTheme.typography.titleMedium)
            }
            1 -> {
                Text("BMS Found: E-Rickshaw [TX-902]", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { connectionState = 2 }) {
                    Text("Connect Securely")
                }
            }
            2 -> {
                CircularProgressIndicator(color = Color(0xFF4CAF50))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Performing Digital Handshake...", style = MaterialTheme.typography.titleMedium)
                Text("Exchanging 256-bit Secret Token", style = MaterialTheme.typography.bodySmall)
            }
            3 -> {
                Text("Connection Secure!", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
            }
        }
    }
}
