package com.example.data

import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HydrationRepository(private val dao: HydrationDao) {

    fun getTodayKey(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getTodayRecords(): Flow<List<HydrationRecord>> {
        return dao.getRecordsForDay(getTodayKey())
    }

    suspend fun addWater(amountMl: Int): Long {
        val record = HydrationRecord(
            amountMl = amountMl,
            timestamp = System.currentTimeMillis(),
            dayKey = getTodayKey()
        )
        return dao.insertRecord(record)
    }

    suspend fun deleteRecord(id: Long) {
        dao.deleteRecordById(id)
    }

    suspend fun resetToday() {
        dao.deleteAllForDay(getTodayKey())
    }
}
