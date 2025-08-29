// FILE: AppNavigation.kt
package com.rahul.auric.auricscan

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rahul.auric.auricscan.screens.GeneratorScreen
import com.rahul.auric.auricscan.screens.HomeScreen
import com.rahul.auric.auricscan.screens.ScannerScreen
import com.rahul.auric.auricscan.screens.HistoryScreen

object Routes {
    const val HOME = "home"
    const val SCANNER = "scanner"
    const val GENERATOR = "generator"
    const val HISTORY = "history"

}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                onNavigateToScanner = { navController.navigate(Routes.SCANNER) },
                onNavigateToGenerator = { navController.navigate(Routes.GENERATOR) }
            )
        }
        composable(Routes.SCANNER) {
            ScannerScreen()
        }
        composable(Routes.GENERATOR) {
            GeneratorScreen(onNavigateBack = { navController.navigateUp() })
        }

        composable(Routes.HISTORY) {
            HistoryScreen(onNavigateBack = { navController.navigateUp() })
        }
    }
}