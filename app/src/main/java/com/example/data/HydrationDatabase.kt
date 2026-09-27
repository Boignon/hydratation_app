package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HydrationRecord::class], version = 1, exportSchema = false)
abstract class HydrationDatabase : RoomDatabase() {
    abstract fun hydrationDao(): HydrationDao

    companion object {
        @Volatile
        private var INSTANCE: HydrationDatabase? = null

        fun getDatabase(context: Context): HydrationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HydrationDatabase::class.java,
                    "hydration_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
