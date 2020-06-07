package com.example.android.funnytrivia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SummaryLogoQuiz::class], version = 1, exportSchema = false)
abstract class SummaryDatabase: RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val summaryDatabaseDao: SummaryDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: SummaryDatabase? = null

        fun getInstance(context: Context): SummaryDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SummaryDatabase::class.java,
                        "summary_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}