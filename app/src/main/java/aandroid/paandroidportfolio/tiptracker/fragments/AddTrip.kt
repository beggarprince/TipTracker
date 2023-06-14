package aandroid.paandroidportfolio.tiptracker.fragments

import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.trip.Trip
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import java.lang.NumberFormatException

class AddTrip : Fragment() {

    private val sharedViewModel: ViewModel by activityViewModels()
    private val lock = Object()
    private var criticalSectionAddTripDone = false
    private val successFullyAddedMessage = "Trip Successfully Added"
    private val duration = Toast.LENGTH_SHORT
    private var tripFilledOuCorrectly = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_add_trip, container, false)

        val fragmentContext = requireContext()

        val addTripBtn = rootview.findViewById<Button>(R.id.btn_add_trip)

        val editAmount = rootview.findViewById<EditText>(R.id.et_amount_earned)
        val mileageAmount = rootview.findViewById<EditText>(R.id.et_miles_driven)
        val hourAmount = rootview.findViewById<EditText>(R.id.et_hours_worked)

        addTripBtn.setOnClickListener {
            //TODO add checks to see if the inputs make sense

            synchronized(lock){
                tripFilledOuCorrectly = true
             try {
                 editAmount.text.toString().toInt()
                 mileageAmount.text.toString().toInt()
                 hourAmount.text.toString().toInt()
             }catch (e: NumberFormatException){
                 Toast.makeText(fragmentContext, "Trip Not Added\nComplete Form Using Numbers", duration).show()
                 tripFilledOuCorrectly= false
             }
                criticalSectionAddTripDone = true

            }
            synchronized(lock){
                while(!criticalSectionAddTripDone){
                    try{
                        lock.wait()
                    }catch (e: InterruptedException){e.printStackTrace()}
                }
                if(tripFilledOuCorrectly){
                sharedViewModel.addTrip(
                    Trip(
                        editAmount.text.toString().toInt(),
                        mileageAmount.text.toString().toInt(),
                        sharedViewModel.date,
                        hourAmount.text.toString().toInt()
                    )
                )
                    Toast.makeText(fragmentContext, successFullyAddedMessage, duration).show()
                }
                tripFilledOuCorrectly = false
            }

        }

        return rootview
    }


}