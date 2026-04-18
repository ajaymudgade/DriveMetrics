package com.example.drivemetrics.repository

import com.example.drivemetrics.data.TelemetryData
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    fun getTelemetry(): Flow<TelemetryData>
}