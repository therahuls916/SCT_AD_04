// FILE: ui/components/TopBar.kt

package com.rahul.auric.auricscan.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rahul.auric.auricscan.ui.theme.AuricScanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
) {
    TopAppBar(
        title = { Text("AuricScan", color = MaterialTheme.colorScheme.onBackground) },
        actions = {

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Preview
@Composable
fun TopBarPreview() {
    AuricScanTheme {
        TopBar()
    }
}