package com.example.android.funnytrivia

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.funnytrivia.database.SummaryDatabase
import com.example.android.funnytrivia.database.SummaryDatabaseDao
import com.example.android.funnytrivia.database.SummaryLogoQuiz
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class SummaryDatabaseTest {

    private lateinit var summaryDao: SummaryDatabaseDao
    private lateinit var db: SummaryDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SummaryDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        summaryDao = db.summaryDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val summary = SummaryLogoQuiz()
        summaryDao.insert(summary)
        val scoreSummary = summaryDao.getSummary()
        assertEquals(scoreSummary?.numberOfQuestionsAttempted, 0)
    }
}
