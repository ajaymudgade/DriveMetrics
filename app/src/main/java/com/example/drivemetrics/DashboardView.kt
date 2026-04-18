package com.example.drivemetrics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import com.example.drivemetrics.data.TelemetryData

class DashboardView(context: Context) : View(context) {

    private val paint = Paint().apply {
        color = Color.GREEN
        textSize = 60f
    }

    var data: TelemetryData = TelemetryData()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d(Constants.TAG, "UI Draw → Speed: ${data.speed}")
        canvas.drawText("Speed: ${data.speed.toInt()} km/h", 50f, 100f, paint)
        canvas.drawText("Fuel: ${data.fuelLevel.toInt()}%", 50f, 200f, paint)
        canvas.drawText("Door: ${if (data.doorOpen) "Open" else "Closed"}", 50f, 300f, paint)
        canvas.drawText("Temp: ${data.temperature}°C", 50f, 400f, paint)
    }
}