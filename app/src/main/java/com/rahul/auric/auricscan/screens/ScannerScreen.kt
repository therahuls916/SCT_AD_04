// FILE: screens/ScannerScreen.kt

package com.rahul.auric.auricscan.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rahul.auric.auricscan.scanner.ScannedContentType
import com.rahul.auric.auricscan.scanner.ScannerViewModel
import com.rahul.auric.auricscan.ui.components.ViewfinderOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerScreen(
    scannerViewModel: ScannerViewModel = viewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scannedValue by scannerViewModel.scannedQrCode
    val scannedContentType by scannerViewModel.scannedContentType

    var hasCameraPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCameraPermission = granted }
    )

    LaunchedEffect(key1 = true) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (hasCameraPermission) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    }
                    scannerViewModel.setupCamera(ctx, lifecycleOwner, previewView)
                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )
            ViewfinderOverlay()
        } else {
            PermissionDeniedContent { launcher.launch(Manifest.permission.CAMERA) }
        }

        if (scannedValue != null && scannedContentType != null) {
            ModalBottomSheet(onDismissRequest = { scannerViewModel.onQrCodeScanned() }) {
                ScanResultContent(
                    scannedValue = scannedValue!!,
                    contentType = scannedContentType!!,
                    onShare = { scannerViewModel.shareScannedText(context) }
                )
            }
        }
    }
}

@Composable
fun ScanResultContent(
    scannedValue: String,
    contentType: ScannedContentType,
    onShare: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Scan Result", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(scannedValue, textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))

        // Show different buttons based on the content type
        when (contentType) {
            ScannedContentType.URL -> {
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(scannedValue))
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Default.Link, contentDescription = "Open Link", modifier = Modifier.size(ButtonDefaults.IconSize))
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Open in Browser")
                }
            }
            ScannedContentType.TEXT -> {
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_WEB_SEARCH).apply { putExtra("query", scannedValue) }
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search Web", modifier = Modifier.size(ButtonDefaults.IconSize))
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Search on Web")
                }
            }
        }
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = { onShare() }) { Text("Share") }
            TextButton(onClick = {
                clipboardManager.setText(AnnotatedString(scannedValue))
                Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
            }) { Text("Copy") }
        }
    }
}


@Composable
fun PermissionDeniedContent(onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Camera permission is required to scan QR codes.", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRequestPermission) { Text("Grant Permission") }
    }
}