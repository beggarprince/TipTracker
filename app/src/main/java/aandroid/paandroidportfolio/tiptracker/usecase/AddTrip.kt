package aandroid.paandroidportfolio.tiptracker.usecase

import aandroid.paandroidportfolio.tiptracker.MainActivity
import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.Trip
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels

class AddTrip : Fragment() {

    private val sharedViewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_add_trip, container, false)

        val debugButton = rootview.findViewById<Button>(R.id.addTripDebug)
        val addTripBtn = rootview.findViewById<Button>(R.id.btn_add_trip)

        val editAmount = rootview.findViewById<EditText>(R.id.et_amount_earned)
        val mileageAmount = rootview.findViewById<EditText>(R.id.et_miles_driven)

        debugButton.setOnClickListener {
            sharedViewModel.addTrip(Trip(10, 10, ",", 1))
        }

        addTripBtn.setOnClickListener {
            //TODO add checks to see if the inputs make sense
            sharedViewModel.addTrip(
                Trip(
                    editAmount.text.toString().toInt(),
                    mileageAmount.text.toString().toInt(),
                    "",
                    1))
        }

        return rootview
    }


}