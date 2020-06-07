package com.example.android.funnytrivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.android.funnytrivia.database.SummaryDatabase
import com.example.android.funnytrivia.databinding.FragmentGameWonBinding
import com.example.android.funnytrivia.databinding.FragmentSummaryBinding
import com.example.android.funnytrivia.summarymodel.SummaryViewModel
import com.example.android.funnytrivia.summarymodel.SummaryViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SummaryFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentSummaryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_summary, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = SummaryDatabase.getInstance(application).summaryDatabaseDao
        val viewModelFactory = SummaryViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        val summaryViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(SummaryViewModel::class.java)

        // To use the View Model with data binding, we have to explicitly
        // give the binding object a reference to it.
        binding.summariesViewModel = summaryViewModel

        binding.setLifecycleOwner(this)

        // Add an Observer on the state variable for showing a Snackbar message
        // when the CLEAR button is pressed.
        summaryViewModel.showSnackBarEvent.observe(this, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                // Reset state to make sure the snackbar is only shown once, even if the device
                // has a configuration change.
                summaryViewModel.doneShowingSnackbar()
            }
        })

        return binding.root

    }

}