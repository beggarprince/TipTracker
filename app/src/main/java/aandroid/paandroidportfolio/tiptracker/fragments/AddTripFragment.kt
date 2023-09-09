package aandroid.paandroidportfolio.tiptracker.fragments

import aandroid.paandroidportfolio.tiptracker.MainActivity
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.trip.Trip
import aandroid.paandroidportfolio.tiptracker.utility.DatePicker
import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class AddTripFragment : Fragment() {

    private val sharedViewModel: ViewModel by activityViewModels()
    private val successFullyAddedMessage = "Trip Successfully Added"
    private val duration = Toast.LENGTH_SHORT

    //ui
    private lateinit var editAmount: EditText
    private lateinit var mileageAmount: EditText
    private lateinit var hourAmount: EditText
    private lateinit var gasprice: EditText
    private lateinit var date: TextView
    private var dateOverrideButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_add_trip, container, false)

        bindUI(rootview)

        return rootview
    }
    private fun createTrip(): Trip? {
        return try {
            Trip(
                editAmount.text.toString().toFloat(),
                mileageAmount.text.toString().toFloat(),
                date.text.toString(),
                hourAmount.text.toString().toFloat(),
                gasprice.text.toString().toFloat()
            )
        } catch (e: NumberFormatException) {
            val fragmentContext = requireContext()
            Toast.makeText(
                fragmentContext,
                "Fill out form using numbers",
                duration
            ).show()
            null
        }
    }

    private fun addTrip(trip: Trip) {
        if (trip != null) {
            sharedViewModel.addTrip(trip)
            Toast.makeText(requireContext(), successFullyAddedMessage, duration).show()
            (activity as? MainActivity)?.switchFragment(FragmentType.HOME)
        }
    }

    private fun bindUI(rootview: View) {
        editAmount = rootview.findViewById(R.id.et_amount_earned)
        mileageAmount = rootview.findViewById(R.id.et_miles_driven)
        hourAmount = rootview.findViewById(R.id.et_hours_worked)
        gasprice = rootview.findViewById(R.id.et_price_per_gallon)
        date = rootview.findViewById<TextView>(R.id.dateOverride)

        val addTripBtn = rootview.findViewById<Button>(R.id.btn_add_trip)
        date.text = sharedViewModel.date

        addTripBtn.setOnClickListener {
            createTrip()?.let { trip ->
                addTrip(trip)
            }
        }

        dateOverrideButton = rootview.findViewById<Button>(R.id.btn_dateOverride)

        dateOverrideButton?.setOnClickListener {
            DatePicker.showDatePickerDialog(context, "Select Date"){ selectedDate->
                date.text = selectedDate
            }
        }
    }

}

