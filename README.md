# 🔋 Battery Intelligence Platform (SIH Hackathon)

[![GitHub Repo](https://img.shields.io/badge/GitHub-Repository-blue?logo=github)](https://github.com/Wasniksujall/sih-battery-intelligence.git)

This is a comprehensive, secure Battery Management System (BMS) application developed for the **Smart India Hackathon (SIH)**. 

It specifically solves the critical real-world problem of unauthorized individuals using generic Bluetooth applications to connect to E-Rickshaw BMS hardware and shutting down the vehicle.

## 🚀 Key Features

*   **Biometric Driver Authentication:** Eliminates complex passwords for uneducated drivers while ensuring a stolen phone cannot access the vehicle.
*   **Secure Digital Handshake:** Replaces the "open door" Bluetooth vulnerability with a 256-bit cryptographic token exchange. Unauthorized Chinese apps are instantly blocked.
*   **Real-Time Analytics Dashboard:** Monitors State of Charge (SoC), State of Health (SoH), Voltage, Current, and Power (kW).
*   **Predictive Maintenance:** Analyzes live trends to estimate when the battery needs physical servicing.
*   **Emergency Kill Switch:** Authorized drivers/fleet owners can securely cut the battery power in an emergency.

## 📱 Project Architecture

Built using modern Android development standards:
*   **Kotlin & Jetpack Compose:** For a fluid, reactive, and beautiful UI.
*   **MVVM Architecture:** Clean separation of data and UI using `ViewModel` and `StateFlow`.
*   **Mock IoT Generator:** Included for hackathon demonstration purposes, simulating real-time voltage drops, temperature fluctuations, and security alerts.

## 🐳 Docker Build Environment (For GitHub & CI/CD)

This repository is Docker-compatible, meaning anyone can compile the APK without needing to install Android Studio or the Android SDK on their machine.

```bash
# Build the Android APK using Docker Compose
docker-compose up
```
The compiled `.apk` will be generated using the containerized Ubuntu and Java 17 environment.

---
*Created for Smart India Hackathon.*
