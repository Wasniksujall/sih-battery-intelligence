package com.sih.batteryui.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Security
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    // Pulsing animation for the fingerprint scanner
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pulse_scale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                colors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364)) // High-tech dark gradient
            ))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Security,
            contentDescription = "Security",
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "E-Rickshaw BMS",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = "SECURE ACCESS PORTAL",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF81C784),
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(64.dp))

        Card(
            modifier = Modifier
                .size(140.dp)
                .scale(scale)
                .clip(CircleShape),
            colors = CardDefaults.cardColors(containerColor = Color(0x334CAF50)),
            onClick = { onLoginSuccess() }
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Fingerprint Login",
                    modifier = Modifier.size(80.dp),
                    tint = Color(0xFF4CAF50)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "SCAN FINGERPRINT TO IGNITE",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        TextButton(onClick = { /* Demo purpose */ }) {
            Text("USE FLEET OWNER OVERRIDE PIN", color = Color(0xFF90CAF9))
        }
    }
}
