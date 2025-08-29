// FILE: data/ScanHistoryDao.kt
package com.rahul.auric.auricscan.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanHistoryDao {
    @Insert
    suspend fun insert(scan: ScanHistory)

    @Delete
    suspend fun delete(scan: ScanHistory)

    @Query("SELECT * FROM scan_history ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<ScanHistory>>

    @Query("DELETE FROM scan_history")
    suspend fun deleteAll()
}