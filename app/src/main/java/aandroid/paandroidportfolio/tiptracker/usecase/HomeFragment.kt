package aandroid.paandroidportfolio.tiptracker.usecase

import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.TripAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }


    private val sharedViewModel: ViewModel by activityViewModels()

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
        val adapter = TripAdapter(sharedViewModel.tripList,
        sharedViewModel )
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(this.context)

        val homeFragmentButton = rootview.findViewById<Button>(R.id.homeButton)
        homeFragmentButton.setOnClickListener {

        }

        return rootview
    }

}