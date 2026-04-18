package com.example.drivemetrics

import android.util.Log
import com.example.drivemetrics.data.TelemetryData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class MockCarDataSource {

    fun getMockTelemetry(): Flow<TelemetryData> = flow {
        Log.d(Constants.TAG, "Starting MOCK telemetry stream")

        var speed = 0f

        while (true) {

            speed += Random.nextFloat() * 5
            if (speed > 160) speed = 0f

            val data = TelemetryData(
                speed = speed,
                fuelLevel = Random.nextInt(10, 100).toFloat(),
                doorOpen = Random.nextBoolean(),
                temperature = Random.nextInt(18, 30).toFloat()
            )



            emit(
                TelemetryData(
                    speed = speed,
                    fuelLevel = Random.nextInt(10, 100).toFloat(),
                    doorOpen = Random.nextBoolean(),
                    temperature = Random.nextInt(18, 30).toFloat()
                )
            )

            Log.d(Constants.TAG, "Mock data emitted: $data")

            delay(1000)


        }
    }
}