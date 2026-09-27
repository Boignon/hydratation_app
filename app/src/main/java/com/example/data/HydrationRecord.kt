package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hydration_records")
data class HydrationRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amountMl: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val dayKey: String
)
