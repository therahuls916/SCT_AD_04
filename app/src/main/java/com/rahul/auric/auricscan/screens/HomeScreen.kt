// FILE: screens/HomeScreen.kt

package com.rahul.auric.auricscan.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rahul.auric.auricscan.R
import com.rahul.auric.auricscan.ui.components.ActionCard
import com.rahul.auric.auricscan.ui.components.BottomNavBar
import com.rahul.auric.auricscan.ui.components.TopBar
import com.rahul.auric.auricscan.ui.theme.AuricScanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onNavigateToScanner: () -> Unit,
    onNavigateToGenerator: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ActionCard(
                title = "Scan QR Code",
                subtitle = "Use your camera to scan QR codes",
                buttonText = "Scan",
                imageResId = R.drawable.ic_camera,
                iconTintColor = MaterialTheme.colorScheme.primary,
                onButtonClick = onNavigateToScanner
            )

            ActionCard(
                title = "Generate QR Code",
                subtitle = "Create your own QR codes",
                buttonText = "Generate",
                imageResId = R.drawable.ic_create,
                onButtonClick = onNavigateToGenerator
            )
        }
    }
}


@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    AuricScanTheme {
        val navController = rememberNavController()
        HomeScreen(
            navController = navController,
            onNavigateToScanner = {},
            onNavigateToGenerator = {}
        )
    }
}