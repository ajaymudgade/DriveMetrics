package com.example.drivemetrics.repository

import com.example.drivemetrics.MockCarDataSource
import com.example.drivemetrics.data.TelemetryData
import kotlinx.coroutines.flow.Flow

class CarRepositoryImpl(
    private val mockSource: MockCarDataSource
) : CarRepository {

    override fun getTelemetry(): Flow<TelemetryData> {
        return mockSource.getMockTelemetry() // FORCE MOCK
    }
}