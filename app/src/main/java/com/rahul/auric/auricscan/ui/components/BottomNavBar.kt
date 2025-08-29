// FILE: ui/components/BottomNavBar.kt

package com.rahul.auric.auricscan.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rahul.auric.auricscan.Routes

@Composable
fun BottomNavBar(
    navController: NavController
) {
    // This observes the navigation back stack and tells us the current route
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {

        NavigationBarItem(
            // Highlight the icon if the current route is the home route
            selected = currentRoute == Routes.HOME,
            onClick = {
                // Navigate to home, ensuring we don't build up a huge stack of home screens
                navController.navigate(Routes.HOME) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )

        // History Item
        NavigationBarItem(
            selected = currentRoute == Routes.HISTORY,
            onClick = {
                navController.navigate(Routes.HISTORY) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.History, contentDescription = "History") },
            label = { Text("History") }
        )
    }
}