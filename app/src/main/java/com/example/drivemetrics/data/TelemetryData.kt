package com.example.drivemetrics.data

data class TelemetryData(
    val speed: Float = 0f,
    val fuelLevel: Float = 0f,
    val doorOpen: Boolean = false,
    val temperature: Float = 22f
)