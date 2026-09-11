package com.sih.batteryui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sih.batteryui.ui.screens.AlertsScreen
import com.sih.batteryui.ui.screens.DashboardScreen
import com.sih.batteryui.ui.screens.MonitoringScreen
import com.sih.batteryui.viewmodel.BatteryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BatteryApp()
            }
        }
    }
}

@Composable
fun BatteryApp() {
    val navController = rememberNavController()
    val viewModel: BatteryViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Only show bottom navigation on main dashboard screens
    val showBottomBar = currentRoute in listOf("dashboard", "monitoring", "alerts")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                        label = { Text("Dashboard") },
                        selected = currentRoute == "dashboard",
                        onClick = { navController.navigate("dashboard") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Analytics, contentDescription = "Monitoring") },
                        label = { Text("Monitoring") },
                        selected = currentRoute == "monitoring",
                        onClick = { navController.navigate("monitoring") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Warning, contentDescription = "Alerts") },
                        label = { Text("Alerts") },
                        selected = currentRoute == "alerts",
                        onClick = { navController.navigate("alerts") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") { 
                com.sih.batteryui.ui.screens.LoginScreen(
                    onLoginSuccess = { navController.navigate("bluetooth") { popUpTo("login") { inclusive = true } } }
                ) 
            }
            composable("bluetooth") { 
                com.sih.batteryui.ui.screens.BluetoothConnectScreen(
                    onConnectionSuccess = { navController.navigate("dashboard") { popUpTo("bluetooth") { inclusive = true } } }
                ) 
            }
            composable("dashboard") { 
                DashboardScreen(
                    viewModel = viewModel,
                    onResetDemo = { navController.navigate("login") { popUpTo(0) { inclusive = true } } }
                ) 
            }
            composable("monitoring") { MonitoringScreen(viewModel) }
            composable("alerts") { AlertsScreen(viewModel) }
        }
    }
}
