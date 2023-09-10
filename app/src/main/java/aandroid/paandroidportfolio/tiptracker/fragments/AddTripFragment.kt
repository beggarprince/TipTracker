package aandroid.paandroidportfolio.tiptracker.fragments

import aandroid.paandroidportfolio.tiptracker.MainActivity
import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.databinding.FragmentAddTripBinding
import aandroid.paandroidportfolio.tiptracker.trip.Trip
import aandroid.paandroidportfolio.tiptracker.utility.DatePicker
import android.os.Bundle
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
    private lateinit var binding: FragmentAddTripBinding

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
    ): View {

        binding = FragmentAddTripBinding.inflate(inflater, container, false)
        bindUI()

        return binding.root
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
        sharedViewModel.addTrip(trip)
        //todo: REMOVE THIS ONCE I FIX THE NULL ID BUG
        Toast.makeText(requireContext(), successFullyAddedMessage, duration).show()
        (activity as? MainActivity)?.switchFragment(FragmentType.HOME)
    }

    private fun bindUI() {
        editAmount = binding.etAmountEarned
        mileageAmount = binding.etMilesDriven
        hourAmount = binding.etHoursWorked
        gasprice = binding.etPricePerGallon
        date = binding.dateOverride
        date.text = sharedViewModel.date

        binding.btnAddTrip.setOnClickListener {
            createTrip()?.let { trip ->
                addTrip(trip)
            }
        }

        dateOverrideButton = binding.btnDateOverride

        dateOverrideButton?.setOnClickListener {
            DatePicker.showDatePickerDialog(context, "Select Date") { selectedDate ->
                date.text = selectedDate
            }
        }
    }

}

