package com.example.android.funnytrivia

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.funnytrivia.databinding.TitleFragmentBinding


/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    //onCreateView(): Called to inflate the fragment's layout.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<TitleFragmentBinding>(inflater, R.layout.title_fragment, container, false)
        binding.playButton.setOnClickListener {
            // When we use "safe arguments," we can replace fragment classes that are used in navigation code
            // with NavDirection classes. we do this so that we can use type-safe arguments with other fragments in app.
                view:View -> view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_trivia)

        setHasOptionsMenu(true)

        //Log.i("TitleFragment", "onCreateView Called")

        return binding.root
    }

    //Its Called when the fragment is associated with its owner activity.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Log.i("TitleFragment", "onAttach Called")
    }

    //onCreate() for the fragment is called to do initial fragment creation (other than layout).
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.i("TitleFragment", "onCreate Called")
    }

    //onStart(): Called when the fragment becomes visible; parallel to the activity's onStart().
    override fun onStart() {
        super.onStart()
        //Log.i("TitleFragment", "onStart Called")
    }

    //onActivityCreated(): Called when the owner activity's onCreate() is complete.
    // our fragment will not be able to access the activity until this method is called.
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Log.i("TitleFragment", "onActivity Called")
    }

    //onResume(): Called when the fragment gains the user focus; parallel to the activity's onResume().
    override fun onResume() {
        super.onResume()
        //Log.i("TitleFragment", "onResume Called")
    }

    //onPause(): Called when the fragment loses the user focus; parallel to the activity's onPause().
    override fun onPause() {
        super.onPause()
        //Log.i("TitleFragment", "onPause Called")
    }

    //onStop(): Called when the fragment is no longer visible on screen;
    // parallel to the activity's onStop().
    override fun onStop() {
        super.onStop()
        //Log.i("TitleFragment", "onStop Called")
    }

    //onDestroyView(): Called when the fragment's view is no longer needed,
    // to clean up the resources associated with that view.
    override fun onDestroyView() {
        super.onDestroyView()
        //Log.i("TitleFragment", "onDestroyView Called")
    }

    override fun onDetach() {
        super.onDetach()
        //Log.i("TitleFragment", "onDetach Called")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }
}

