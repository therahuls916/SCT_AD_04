// FILE: screens/HistoryScreen.kt

package com.rahul.auric.auricscan.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rahul.auric.auricscan.data.ScanHistory
import com.rahul.auric.auricscan.history.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit,
    // Get an instance of our HistoryViewModel
    historyViewModel: HistoryViewModel = viewModel()
) {
    // Collect the 'allScans' StateFlow as Compose state.
    // This 'scans' variable will automatically update whenever the database changes.
    val scans by historyViewModel.allScans.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan History") },
                // The back button will trigger the navigation action we receive
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                // The delete-all button will call the ViewModel function
                actions = {
                    if (scans.isNotEmpty()) { // Only show the button if there's something to delete
                        IconButton(onClick = { historyViewModel.deleteAllScans() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete All")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        // If the list of scans is empty, show a message.
        if (scans.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No scan history found.")
            }
        } else {
            // If the list is not empty, display it in a LazyColumn.
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 'items' is a special function for displaying lists in LazyColumn.
                items(scans, key = { it.id }) { scan ->
                    HistoryItem(
                        scan = scan,
                        onDelete = { historyViewModel.deleteScan(scan) }
                    )
                }
            }
        }
    }
}

// A reusable composable for displaying a single row in our history list.
@Composable
fun HistoryItem(scan: ScanHistory, onDelete: () -> Unit) {
    // Format the timestamp into a human-readable date string
    val date = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date(scan.timestamp))

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = scan.content, fontWeight = FontWeight.SemiBold)
                Text(text = date, style = MaterialTheme.typography.bodySmall)
            }
            // The delete button for this specific item
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Scan")
            }
        }
    }
}