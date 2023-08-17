package aandroid.paandroidportfolio.tiptracker.fragments

import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.trip.Trip
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTrip : Fragment() {

    private val sharedViewModel: ViewModel by activityViewModels()
    private val successFullyAddedMessage = "Trip Successfully Added"
    private val duration = Toast.LENGTH_SHORT

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
        val gasprice = rootview.findViewById<EditText>(R.id.et_price_per_gallon)
        val dateOverrideButton = rootview.findViewById<Button>(R.id.btn_dateOverride)

        val date = rootview.findViewById<TextView>(R.id.dateOverride)
        date.text = sharedViewModel.date

        //Create calendar for the date picker
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        dateOverrideButton.setOnClickListener {
            context?.let { it ->
                val datePickerDialog = DatePickerDialog(
                    it,
                    datePicker,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.setTitle("Date Override")
                datePickerDialog.show()
                datePickerDialog.setOnDateSetListener{_, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    date.setText(SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time))
                }
            }
        }

        addTripBtn.setOnClickListener {

            try {

                sharedViewModel.addTrip(
                    Trip(
                        editAmount.text.toString().toFloat(),
                        mileageAmount.text.toString().toFloat(),
                        date.text.toString(),
                        hourAmount.text.toString().toFloat(),
                        gasprice.text.toString().toFloat()
                    )
                )
                Toast.makeText(fragmentContext, successFullyAddedMessage, duration).show()

            } catch (e: NumberFormatException) {
                Toast.makeText(
                    fragmentContext,
                    "Trip Not Added\nComplete Form Using Numbers",
                    duration
                ).show()
            }


        }

        return rootview
    }

}