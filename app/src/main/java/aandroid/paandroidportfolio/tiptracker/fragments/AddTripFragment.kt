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
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class AddTripFragment : Fragment() {
    companion object{
        const val successFullyAddedMessage = "Trip Successfully Added"
        const val duration = Toast.LENGTH_SHORT

    }

    private val sharedViewModel: ViewModel by activityViewModels()
    private lateinit var binding: FragmentAddTripBinding

    //ui
    private lateinit var editAmount: EditText
    private lateinit var mileageAmount: EditText
    private lateinit var hourAmount: EditText
    private lateinit var gasPrice: EditText
    private lateinit var date: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddTripBinding.inflate(inflater, container, false)
        setupUIComponents()

        return binding.root
    }

    private fun createTrip(): Trip? {
        return try {
            Trip(
                editAmount.text.toString().toFloat(),
                mileageAmount.text.toString().toFloat(),
                date.text.toString(),
                hourAmount.text.toString().toFloat(),
                gasPrice.text.toString().toFloat()
            )
        } catch (e: NumberFormatException) {
            Toast.makeText(
                requireContext(),
                "Use numbers to fill form",
                duration
            ).show()
            null
        }
    }

    private fun addTrip(trip: Trip) {
        sharedViewModel.addTrip(trip)
        Toast.makeText(requireContext(), successFullyAddedMessage, duration).show()
        //(activity as? MainActivity)?.switchFragment(FragmentType.HOME)
    }

    private fun setupUIComponents() = with(binding) {
        editAmount = etAmountEarned
        mileageAmount = etMilesDriven
        hourAmount = etHoursWorked
        gasPrice = etPricePerGallon
        date = currDate
        date.text = sharedViewModel.todayDate

        btnAddTrip.setOnClickListener {
            createTrip()?.let { trip ->
                addTrip(trip)
            }
        }
            btnDateOverride.setOnClickListener {
            DatePicker.showDatePickerDialog(context, "Select Date") { selectedDate ->
                date.text = selectedDate
            }
        }
    }

}

