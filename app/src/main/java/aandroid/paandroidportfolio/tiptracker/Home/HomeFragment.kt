package aandroid.paandroidportfolio.tiptracker.Home

import aandroid.paandroidportfolio.tiptracker.MainViewModel
import aandroid.paandroidportfolio.tiptracker.R
import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel


    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview =  inflater.inflate(
            R.layout.fragment_home,
            container, false)
        val homeFragmentButton = rootview.findViewById<Button>(R.id.homeButton)
        homeFragmentButton.setOnClickListener{
            Log.d(TAG, "fuck you nigga")
        }
        val textScopeTest = rootview.findViewById<TextView>(R.id.homefragtext)
        textScopeTest.text = sharedViewModel.scopeTest
        return rootview
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

    }

}