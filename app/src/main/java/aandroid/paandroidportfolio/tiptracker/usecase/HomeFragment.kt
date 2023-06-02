package aandroid.paandroidportfolio.tiptracker.usecase

import aandroid.paandroidportfolio.tiptracker.MainViewModel
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.Trip
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }


    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(
            R.layout.fragment_home,
            container, false
        )
        val recView = rootview
            .findViewById<RecyclerView>(R.id.homeRecView)
        recView.adapter = sharedViewModel.tripAdapter
        recView.layoutManager = LinearLayoutManager(this.context)

        val homeFragmentButton = rootview.findViewById<Button>(R.id.homeButton)
        homeFragmentButton.setOnClickListener {
            Log.d(TAG, "HOME FRAGMENT BUTTON CLICKED")
            sharedViewModel.tripAdapter
                .addTrip(Trip(10, 10, "", ""))
        }

        return rootview
    }

}