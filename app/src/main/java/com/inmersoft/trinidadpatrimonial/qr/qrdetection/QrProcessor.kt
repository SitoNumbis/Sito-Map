package com.inmersoft.trinidadpatrimonial.qr.qrdetection

import android.annotation.SuppressLint
import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import com.inmersoft.trinidadpatrimonial.qr.camera.CameraReticleAnimator
import com.inmersoft.trinidadpatrimonial.qr.camera.GraphicOverlay

class QrProcessor(
    private val graphicOverlay: GraphicOverlay,
    private val scanListener: IScanProcessListener
) : ImageAnalysis.Analyzer {
    private val cameraReticleAnimator = CameraReticleAnimator(graphicOverlay)
    private val barcodeScanner: BarcodeScanner = QrUtils.provideBarcodeScanner()


    //TODO: (Detener la cámara, Mostrar resultados, ...)

    interface IScanProcessListener {
        fun onDetected(scanResult: String?)
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { mediaImage ->
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            graphicOverlay.setCameraPreviewSize(Size(mediaImage.width, mediaImage.height))
            val image = InputImage.fromMediaImage(mediaImage, rotationDegrees)

            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val barcodeInCenter = barcodes.firstOrNull { barcode ->
                        val boundingBox = barcode.boundingBox ?: return@firstOrNull false
                        val box = graphicOverlay.translateRect(boundingBox)
                        val reticleBox = QrUtils.getBarcodeReticleBox(graphicOverlay)
                        reticleBox.contains(box)
                    }

                    graphicOverlay.clear()

                    when (barcodeInCenter) {
                        null -> addReticleGraphic()
                        else -> {
                            processScan(barcodeInCenter.rawValue)
                        }
                    }

                    graphicOverlay.invalidate()
                }
                .addOnCompleteListener { imageProxy.close() }
        }
    }

    private fun processScan(qrValue: String?) {
        scanListener.onDetected(qrValue)
    }

    private fun addReticleGraphic() {
        cameraReticleAnimator.start()
        graphicOverlay.add(QrReticleGraphic(graphicOverlay, cameraReticleAnimator))
    }

    companion object {
        private const val TAG = "QrProcessor"
    }
}