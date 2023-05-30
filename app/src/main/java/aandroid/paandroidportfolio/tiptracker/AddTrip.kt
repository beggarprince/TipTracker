package aandroid.paandroidportfolio.tiptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels

class AddTrip : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview =  inflater.inflate(R.layout.fragment_add_trip, container, false)

        val debugButton = rootview.findViewById<Button>(R.id.addTripDebug)

        debugButton.setOnClickListener{
            sharedViewModel.readTripVM(Trip(10,10,",",""))
            sharedViewModel.scopeTest = "TEST HATH SUCCEEDED"
        }

        return rootview
    }


}