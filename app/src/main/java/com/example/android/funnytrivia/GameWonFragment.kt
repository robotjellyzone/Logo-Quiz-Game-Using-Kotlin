package com.example.android.funnytrivia

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.funnytrivia.database.SummaryDatabase
import com.example.android.funnytrivia.databinding.FragmentGameWonBinding
import com.example.android.funnytrivia.summarymodel.SummaryViewModel
import com.example.android.funnytrivia.summarymodel.SummaryViewModelFactory


class GameWonFragment : Fragment() {

    companion object{
        private var questions: Int = 0
        private var answers: Int = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener {
            // When we use "safe arguments," we can replace fragment classes that are used in navigation code
            // with NavDirection classes. we do this so that we can use type-safe arguments with other fragments in app.
                view: View -> view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        val args = GameWonFragmentArgs.fromBundle(arguments!!) // !! -> not-null assertion operator
        Toast.makeText(context, "NumCorrect: ${args.totalCorrectAnswers}, NumQuestions: ${args.totalQuestionsCompleted}", Toast.LENGTH_LONG).show()

        questions = args.totalQuestionsCompleted
        answers  = args.totalCorrectAnswers

        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = SummaryDatabase.getInstance(application).summaryDatabaseDao
        val viewModelFactory = SummaryViewModelFactory(dataSource, application)


        // Get a reference to the ViewModel associated with this fragment.
        val summaryViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(SummaryViewModel::class.java)

        binding.summaryViewModel = summaryViewModel

        binding.saveResultButton.setOnClickListener {
            binding.summaryViewModel?.onSavingResult(questions, answers)
        }

        binding.setLifecycleOwner(this)

        return binding.root
    }

    // Creating our Share Intent
    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        val shareIntent = Intent(Intent.ACTION_SEND) // implicit intent: A task that your app initiates without knowing which app or activity will handle the task.
        //The actual data to be delivered is specified in the EXTRA_TEXT
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, getString(R.string.share_winning_text, args.totalCorrectAnswers, args.totalQuestionsCompleted))
        return shareIntent
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_share, menu)

        // Use the activity's packageManager property to gain access to the package manager,
        // and call resolveActivity().
        // If the result equals null, which means that the shareIntent doesn't resolve,
        // find the sharing menu item from the inflated menu and make the menu item invisible.
        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            // hide the menu item if it doesn't resolve
            menu?.findItem(R.id.share).setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            R.id.share -> shareSuccess()
        }

        return super.onOptionsItemSelected(item)
    }

}