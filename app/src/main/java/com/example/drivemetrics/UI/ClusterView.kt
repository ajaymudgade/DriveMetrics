package com.example.drivemetrics.UI

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ClusterView(context: Context) : View(context) {

    private val arcPaint = Paint().apply {
        strokeWidth = 20f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val needlePaint = Paint().apply {
        color = Color.MAGENTA
        strokeWidth = 8f
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 60f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val warningPaint = Paint().apply {
        color = Color.RED
        textSize = 40f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val fuelPaint = Paint().apply {
        strokeWidth = 14f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    // DATA
    var speed: Float = 0f
    var rpm: Float = 0f
    var fuel: Float = 50f

    // Smooth values
    private var smoothSpeed = 0f
    private var smoothRpm = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val centerY = height / 2

        val radius = min(width, height) / 3

        val leftCenterX = width / 3
        val rightCenterX = width * 2 / 3

        // Smooth animation
        smoothSpeed = smoothSpeed * 0.9f + speed * 0.1f
        smoothRpm = smoothRpm * 0.9f + rpm * 0.1f

        // Draw RPM (LEFT)
        drawGauge(
            canvas,
            leftCenterX,
            centerY,
            radius,
            smoothRpm,
            max = 8000f,
            label = "RPM",
            isRpm = true
        )

        // Draw SPEED (RIGHT)
        drawGauge(
            canvas,
            rightCenterX,
            centerY,
            radius,
            smoothSpeed,
            max = 180f,
            label = "km/h",
            isRpm = false
        )

        // Fuel arc around speed
        drawFuelArc(canvas, rightCenterX, centerY, radius)
    }

    private fun drawGauge(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        radius: Float,
        value: Float,
        max: Float,
        label: String,
        isRpm: Boolean
    ) {
        val rect = RectF(cx - radius, cy - radius, cx + radius, cy + radius)

        // Zones
        if (isRpm) {
            arcPaint.color = Color.GREEN
            canvas.drawArc(rect, 135f, 180f, false, arcPaint)

            arcPaint.color = Color.RED
            canvas.drawArc(rect, 315f, 90f, false, arcPaint)
        } else {
            arcPaint.color = Color.GREEN
            canvas.drawArc(rect, 135f, 90f, false, arcPaint)

            arcPaint.color = Color.YELLOW
            canvas.drawArc(rect, 225f, 90f, false, arcPaint)

            arcPaint.color = Color.RED
            canvas.drawArc(rect, 315f, 90f, false, arcPaint)
        }

        // Needle
        val angle = 135f + (value / max) * 270f

        val needleLength = radius - 30

        val x = cx + needleLength * cos(Math.toRadians(angle.toDouble())).toFloat()
        val y = cy + needleLength * sin(Math.toRadians(angle.toDouble())).toFloat()

        canvas.drawLine(cx, cy, x, y, needlePaint)

        // Center
        canvas.drawCircle(cx, cy, 15f, needlePaint)

        // Value text
        canvas.drawText("${value.toInt()}", cx, cy + 100f, textPaint)

        // Label
        canvas.drawText(label, cx, cy + 150f, textPaint)

        // Warning
        if (!isRpm && value > 100) {
            canvas.drawText("OVERSPEED", cx, cy - 120f, warningPaint)
        }

        if (isRpm && value > 6000) {
            canvas.drawText("HIGH RPM", cx, cy - 120f, warningPaint)
        }
    }

    private fun drawFuelArc(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val rect = RectF(
            cx - radius + 20,
            cy - radius + 20,
            cx + radius - 20,
            cy + radius - 20
        )

        val sweep = (fuel / 100f) * 270f

        fuelPaint.color = when {
            fuel > 50 -> Color.GREEN
            fuel > 20 -> Color.YELLOW
            else -> Color.RED
        }

        canvas.drawArc(rect, 135f, sweep, false, fuelPaint)
    }
}