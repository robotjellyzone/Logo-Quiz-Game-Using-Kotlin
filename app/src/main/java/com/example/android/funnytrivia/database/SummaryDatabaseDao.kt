package com.example.android.funnytrivia.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Defines methods for using the SummaryLogoQuiz class with Room.
 */
@Dao
interface SummaryDatabaseDao {

    @Insert
    fun insert(summary: SummaryLogoQuiz)


    @Query("SELECT * from quiz_summary_table WHERE userId = :key")
    fun get(key: Long): SummaryLogoQuiz?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM quiz_summary_table")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     */
    @Query("SELECT * FROM quiz_summary_table ORDER BY userId DESC")
    fun getAllScoreSummary(): LiveData<List<SummaryLogoQuiz>>

    /**
     * Selects and returns the latest score.
     */
    @Query("SELECT * FROM quiz_summary_table ORDER BY userId DESC LIMIT 1")
    fun getSummary(): SummaryLogoQuiz?
}
