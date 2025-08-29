// FILE: scanner/ScannerViewModel.kt

package com.rahul.auric.auricscan.scanner

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.rahul.auric.auricscan.data.AppDatabase
import com.rahul.auric.auricscan.data.ScanHistory
import kotlinx.coroutines.launch

enum class ScannedContentType {
    URL, TEXT
}

class ScannerViewModel(application: Application) : AndroidViewModel(application) {
    val scannedQrCode = mutableStateOf<String?>(null)
    // ✅ FIX: Corrected the typo from 'mutableStateof' to 'mutableStateOf'
    val scannedContentType = mutableStateOf<ScannedContentType?>(null)
    private val scanHistoryDao = AppDatabase.getDatabase(application).scanHistoryDao()

    fun setupCamera(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        BarcodeAnalyzer(
                            onBarcodeDetected = { result ->
                                if (scannedQrCode.value == null) {
                                    scannedQrCode.value = result
                                    analyzeContent(result)
                                    saveScanToHistory(result)
                                }
                            },
                             onFailure = { exception ->
                                Toast.makeText(
                                    context,
                                    "Failed to scan: ${exception.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    )
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(context))
    }

    private fun saveScanToHistory(content: String) {
        viewModelScope.launch {
            scanHistoryDao.insert(ScanHistory(content = content))
        }
    }

    private fun analyzeContent(text: String) {
        if (Patterns.WEB_URL.matcher(text).matches()) {
            scannedContentType.value = ScannedContentType.URL
        } else {
            scannedContentType.value = ScannedContentType.TEXT
        }
    }

    fun onQrCodeScanned() {
        scannedQrCode.value = null
        scannedContentType.value = null
    }

    fun shareScannedText(context: Context) {
        val text = scannedQrCode.value ?: return
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share Scanned Text")
        context.startActivity(shareIntent)
    }
}


class BarcodeAnalyzer(
    private val onBarcodeDetected: (String) -> Unit,
    private val onFailure: (Exception) -> Unit
) : ImageAnalysis.Analyzer {
    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.rawValue?.let(onBarcodeDetected)
                    }
                }
                .addOnFailureListener(onFailure)
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}