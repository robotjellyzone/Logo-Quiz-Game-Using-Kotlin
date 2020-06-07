package com.example.android.funnytrivia.database

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_summary_table")
data class SummaryLogoQuiz(

    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "numOfQuestions")
    var numberOfQuestionsAttempted: Int =  0,

    @ColumnInfo(name = "numOfCorrectAnswers")
    var numberOfCorrectAnswers: Int = 0

)

