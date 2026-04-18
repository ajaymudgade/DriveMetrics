package com.example.drivemetrics

import android.car.Car
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import android.util.Log
import com.example.drivemetrics.data.TelemetryData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CarDataSource(private val context: Context) {

    fun getCarTelemetry(): Flow<TelemetryData> = callbackFlow {

        try {
            Log.d(Constants.TAG, "Initializing Car connection")
            val car = Car.createCar(context)
            car.connect()

            Log.d(Constants.TAG, "Car connected: ${car.isConnected}")

            val manager =
                car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

            val callback = object : CarPropertyManager.CarPropertyEventCallback {
                override fun onChangeEvent(value: android.car.hardware.CarPropertyValue<*>) {
                    Log.d(Constants.TAG, "CarProperty update received: ${value.propertyId}")

                    val speed = (value.value as? Float) ?: 0f

                    Log.d(Constants.TAG, "Speed from Car API: $speed")

                    val data = TelemetryData(
                        speed = (value.value as? Float) ?: 0f,
                        fuelLevel = (0..100).random().toFloat(), // fallback
                        doorOpen = listOf(true, false).random(),
                        temperature = (20..30).random().toFloat()
                    )
                    Log.d(Constants.TAG, "Emitting Car data: $data")

                    trySend(data)
                }

                override fun onErrorEvent(propId: Int, zone: Int) {}
            }

            manager.registerCallback(
                callback,
                android.car.VehiclePropertyIds.PERF_VEHICLE_SPEED,
                CarPropertyManager.SENSOR_RATE_ONCHANGE
            )

            awaitClose {
                car.disconnect()
            }

        } catch (e: Exception) {
            close(e)
        }
    }
}