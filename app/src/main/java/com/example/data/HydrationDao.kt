package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HydrationDao {
    @Query("SELECT * FROM hydration_records WHERE dayKey = :dayKey ORDER BY timestamp DESC")
    fun getRecordsForDay(dayKey: String): Flow<List<HydrationRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: HydrationRecord): Long

    @Query("DELETE FROM hydration_records WHERE id = :id")
    suspend fun deleteRecordById(id: Long)

    @Query("DELETE FROM hydration_records WHERE dayKey = :dayKey")
    suspend fun deleteAllForDay(dayKey: String)

    @Query("SELECT SUM(amountMl) FROM hydration_records WHERE dayKey = :dayKey")
    fun getTotalForDay(dayKey: String): Flow<Int?>
}
