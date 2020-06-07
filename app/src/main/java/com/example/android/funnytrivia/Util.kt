package com.example.android.funnytrivia

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.android.funnytrivia.database.SummaryLogoQuiz

fun formatNights(summary: List<SummaryLogoQuiz>, resources: Resources): Spanned {

    val sb = StringBuilder()

    sb.apply {

        append(resources.getString(R.string.title))
        summary.forEach {
            append("<br>")
            append(resources.getString(R.string.questions))
            append("\t ${it.numberOfQuestionsAttempted}:<br>")
            append(resources.getString(R.string.answers))
            append("\t ${it.numberOfCorrectAnswers}:<br>")
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

}
