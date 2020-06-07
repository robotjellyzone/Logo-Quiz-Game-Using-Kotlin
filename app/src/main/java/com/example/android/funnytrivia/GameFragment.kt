package com.example.android.funnytrivia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.funnytrivia.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    data class Question(
        val imageId: Int,
        val text: String,
        val answers: List<String>)

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.
    private val questions: MutableList<Question> = mutableListOf(
        Question(
            imageId = R.drawable.adidas_logo,
            text = "Which shoe's brand logo is this?",
            answers = listOf("adidas", "Puma", "Bata", "Reebok")
        ),
        Question(
            imageId = R.drawable.indian_post_logo,
            text = "Which mail post's logo is this?",
            answers = listOf("Indian Post", "InXpress", "DTDC", "GATI")
        ),
        Question(
            imageId = R.drawable.pantene_logo,
            text = "Which shampoo's brand logo is this?",
            answers = listOf("Pantene", "Head & Shoulders", "Dove", "CHIK")
        ),
        Question(
            imageId = R.drawable.pringles_logo,
            text = "Identify this famous chips brand ?",
            answers = listOf("Pringles", "Lays", "MAD Angles", "Uncle Chips")
        ),
        Question(
            imageId = R.drawable.unilever_logo,
            text = "Identify this logo of indian company which produces foods, beverages, personal care products etc?",
            answers = listOf("Hindustan Unilever", "ITC", "P&G", "Marico")
        ),
        Question(
            imageId = R.drawable.volkswagen_logo,
            text = "Which car brand's logo is this?",
            answers = listOf("Volkswagen", "ŠKODA", "Ducati", "Audi")
        ),
        Question(
            imageId = R.drawable.mts_logo,
            text = "Which indian telecome's logo is this who provides wireless voice, broadband Internet, messaging and data services in India?",
            answers = listOf("MTS", "Airtel", "Cellcom", "Vodafone")
        ),
        Question(
            imageId = R.drawable.dove_logo,
            text = "Which american personal care brand's logo is this which was owned by Unilever & most oftenly produces shampoo & soaps in india?",
            answers = listOf("Dove", "Nivea", "Pears", "Fiama")
        ),
        Question(
            imageId = R.drawable.who_logo,
            text = "This specialized agency of the United Nations responsible for international public health. ?",
            answers = listOf("WHO", "MoHFW", "NHP", "PATH")
        ),
        Question(
            imageId = R.drawable.reebok_logo,
            text = "Which shoe brand's logo is this?",
            answers = listOf("Reebok", "Bata", "Flight", "PUMA")
        )
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    companion object {
        private var totalQuestionsCompleted = 0
        private var totalCorrectAnswers = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false)

        // Bind this fragment class to the layout
        binding.game = this

        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions(binding)

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            totalQuestionsCompleted++
            if (-1 != checkedId){
                onCheckingQuestion(checkedId)
                // Advance to the next question
                if (questionIndex < numQuestions) {
                    currentQuestion = questions[questionIndex]
                    setQuestion(binding)
                    // Calling invalidateAll on the FragmentGameBinding updates the data.
                    binding.invalidateAll()
                } else {
                    // We've won!  Navigate to the gameWonFragment.
                    // Using directions to navigate to the GameWonFragment
                    view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(totalCorrectAnswers, totalQuestionsCompleted))
                }
            } else {
                // Game over! A wrong answer sends us to the gameOverFragment.
                // Using directions to navigate to the GameOverFragment
                view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
            }
        }
        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions(binding: FragmentGameBinding) {
        questions.shuffle()
        questionIndex = 0
        setQuestion(binding)
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    private fun setQuestion(binding: FragmentGameBinding) {
        currentQuestion = questions[questionIndex]
        binding.questionImage.setImageResource(currentQuestion.imageId)
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_trivia_question,  totalCorrectAnswers, totalQuestionsCompleted+1)
    }

    private fun onCheckingQuestion(checkedId: Int) {
        if (-1 != checkedId) {
            var answerIndex = 0
            when (checkedId) {
                R.id.secondAnswerRadioButton -> answerIndex = 1
                R.id.thirdAnswerRadioButton -> answerIndex = 2
                R.id.fourthAnswerRadioButton -> answerIndex = 3
            }
            // The first answer in the original question is always the correct one, so if our
            // answer matches, we have the correct answer.
            if (answers[answerIndex] == currentQuestion.answers[0]) {
                questionIndex++
                totalCorrectAnswers++
            }

        }
    }
}