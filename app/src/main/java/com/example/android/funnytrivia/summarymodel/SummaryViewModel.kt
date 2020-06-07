package com.example.android.funnytrivia.summarymodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.funnytrivia.database.SummaryDatabaseDao
import com.example.android.funnytrivia.database.SummaryLogoQuiz
import com.example.android.funnytrivia.formatNights
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SummaryViewModel(val database: SummaryDatabaseDao,
                       application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val summaries = database.getAllScoreSummary()

    /**
     * Request a toast by setting this value to true.
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val summariesString = Transformations.map(summaries) { summaries ->
        formatNights(summaries, application.resources)
    }

    private var summary = MutableLiveData<SummaryLogoQuiz?>()

    init {
        initializeSummary()
    }

    private fun initializeSummary() {
        uiScope.launch {
            summary.value = getSummaryFromDatabase()
        }
    }

    private suspend fun getSummaryFromDatabase(): SummaryLogoQuiz? {
        return withContext(Dispatchers.IO) {
            var newSummary = database.getSummary()
            newSummary
        }
    }

    /**
     * Executes when the SAVE RESULTS button is clicked.
     */

    fun onSavingResult(questions: Int, answers: Int) {
        uiScope.launch {
            val newSummary = SummaryLogoQuiz()

            newSummary.numberOfQuestionsAttempted = questions
            newSummary.numberOfCorrectAnswers = answers

            insert(newSummary)

            summary.value = getSummaryFromDatabase()
        }
    }

    private suspend fun insert(summary: SummaryLogoQuiz) {
        withContext(Dispatchers.IO) {
            database.insert(summary)
        }
    }

    /**
     * If this is true, immediately `show()` a toast and call `doneShowingSnackbar()`.
     */
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    /**
     * Call this immediately after calling `show()` on a toast.
     *
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate
     * toast.
     */
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    /**
     * Executes when the CLEAR button is clicked.
     */
    fun onClear() {
        uiScope.launch {
            clear()
            summary.value = null

            // Show a snackbar message, because it's friendly.
            _showSnackbarEvent.value = true
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }



















    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}