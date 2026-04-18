package com.example.drivemetrics.UI

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class SpeedometerView(context: Context) : View(context) {

    private val arcPaint = Paint().apply {
        strokeWidth = 25f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val needlePaint = Paint().apply {
        color = Color.MAGENTA
        strokeWidth = 10f
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 90f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val warningPaint = Paint().apply {
        color = Color.RED
        textSize = 60f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val fuelPaint = Paint().apply {
        strokeWidth = 18f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private var displayedSpeed = 0f

    var speed: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var fuelLevel: Float = 50f
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val centerX = width / 2
        val centerY = height / 2

        val radius = min(width, height) / 2 - 60f

        val rect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        // 🔥 SPEED COLOR ZONES
        drawSpeedZones(canvas, rect)

        // 🔥 FUEL ARC
        drawFuelArc(canvas, rect, radius)

        // Smooth speed
        displayedSpeed = displayedSpeed * 0.9f + speed * 0.1f

        val angle = getAngleFromSpeed(displayedSpeed)

        // Needle
        val needleLength = radius - 50
        val needleX = centerX + needleLength * cos(Math.toRadians(angle.toDouble())).toFloat()
        val needleY = centerY + needleLength * sin(Math.toRadians(angle.toDouble())).toFloat()

        canvas.drawLine(centerX, centerY, needleX, needleY, needlePaint)

        // Center dot
        canvas.drawCircle(centerX, centerY, 20f, needlePaint)

        // Speed text
        canvas.drawText("${displayedSpeed.toInt()}", centerX, centerY + 150f, textPaint)

        // 🚨 Overspeed warning
        if (displayedSpeed > 100) {
            canvas.drawText("OVERSPEED!", centerX, centerY - 150f, warningPaint)
        }
    }

    private fun drawSpeedZones(canvas: Canvas, rect: RectF) {
        // Green zone (0–60)
        arcPaint.color = Color.GREEN
        canvas.drawArc(rect, 135f, 90f, false, arcPaint)

        // Yellow zone (60–100)
        arcPaint.color = Color.YELLOW
        canvas.drawArc(rect, 225f, 90f, false, arcPaint)

        // Red zone (100–180)
        arcPaint.color = Color.RED
        canvas.drawArc(rect, 315f, 90f, false, arcPaint)
    }

    private fun drawFuelArc(canvas: Canvas, rect: RectF, radius: Float) {
        val fuelRect = RectF(
            rect.left + 30,
            rect.top + 30,
            rect.right - 30,
            rect.bottom - 30
        )

        val sweepAngle = (fuelLevel / 100f) * 270f

        fuelPaint.color = when {
            fuelLevel > 50 -> Color.GREEN
            fuelLevel > 20 -> Color.YELLOW
            else -> Color.RED
        }

        canvas.drawArc(fuelRect, 135f, sweepAngle, false, fuelPaint)
    }

    private fun getAngleFromSpeed(speed: Float): Float {
        val maxSpeed = 180f
        return 135f + (speed / maxSpeed) * 270f
    }
}