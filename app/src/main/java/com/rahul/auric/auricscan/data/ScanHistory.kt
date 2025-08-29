// FILE: data/ScanHistory.kt
package com.rahul.auric.auricscan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_history")
data class ScanHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)