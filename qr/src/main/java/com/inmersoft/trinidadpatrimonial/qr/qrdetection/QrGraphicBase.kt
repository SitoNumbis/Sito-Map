package com.inmersoft.trinidadpatrimoniald.qr.qrdetection

import android.graphics.*
import androidx.core.content.ContextCompat
import com.inmersoft.trinidadpatrimonial.qr.R
import com.inmersoft.trinidadpatrimoniald.qr.camera.GraphicOverlay

internal abstract class QrGraphicBase(overlay: GraphicOverlay) : GraphicOverlay.Graphic(overlay) {

    private val boxPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.qr_reticle_stroke)
        style = Paint.Style.STROKE
        strokeWidth =
            context.resources.getDimensionPixelOffset(R.dimen.qr_reticle_stroke_width).toFloat()
    }

    private val scrimPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.qr_reticle_background)
    }

    private val eraserPaint: Paint = Paint().apply {
        strokeWidth = boxPaint.strokeWidth
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    val boxCornerRadius: Float =
        context.resources.getDimensionPixelOffset(R.dimen.qr_reticle_corner_radius).toFloat()

    val pathPaint: Paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = boxPaint.strokeWidth
        pathEffect = CornerPathEffect(boxCornerRadius)
    }

    val boxRect: RectF = QrUtils.getBarcodeReticleBox(overlay)

    override fun draw(canvas: Canvas) {
        // Draws the dark background scrim and leaves the box area clear.
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), scrimPaint)
        // As the stroke is always centered, so erase twice with FILL and STROKE respectively to clear
        // all area that the box rect would occupy.
        eraserPaint.style = Paint.Style.FILL
        canvas.drawRoundRect(boxRect, boxCornerRadius, boxCornerRadius, eraserPaint)
        eraserPaint.style = Paint.Style.STROKE
        canvas.drawRoundRect(boxRect, boxCornerRadius, boxCornerRadius, eraserPaint)
        // Draws the box.
        canvas.drawRoundRect(boxRect, boxCornerRadius, boxCornerRadius, boxPaint)
    }
}