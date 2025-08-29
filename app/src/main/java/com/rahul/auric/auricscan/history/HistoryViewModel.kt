// FILE: history/HistoryViewModel.kt

package com.rahul.auric.auricscan.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.auric.auricscan.data.AppDatabase
import com.rahul.auric.auricscan.data.ScanHistory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// We use AndroidViewModel because we need the Application context to get the database
class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    // Get a reference to the DAO (Data Access Object) from our database
    private val dao = AppDatabase.getDatabase(application).scanHistoryDao()

    // This is the magic part. 'getAllScans()' returns a Flow, which is a stream of data.
    // Every time the data in the database changes, this Flow will automatically emit the new list.
    // .stateIn() converts this "hot" Flow into a "cold" StateFlow that our UI can easily collect.
    val allScans = dao.getAllScans()
        .stateIn(
            scope = viewModelScope, // The lifecycle scope of the ViewModel
            started = SharingStarted.WhileSubscribed(5000), // Start collecting when the UI is visible
            initialValue = emptyList() // Start with an empty list before the first data arrives
        )

    // A simple function to delete one specific scan.
    // It runs in a coroutine so it doesn't block the main UI thread.
    fun deleteScan(scan: ScanHistory) {
        viewModelScope.launch {
            dao.delete(scan)
        }
    }

    // A function to delete all scans from the history.
    fun deleteAllScans() {
        viewModelScope.launch {
            dao.deleteAll()
        }
    }
}