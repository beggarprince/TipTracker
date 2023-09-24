package aandroid.paandroidportfolio.tiptracker.fragments

import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.databinding.FragmentHomeBinding
import aandroid.paandroidportfolio.tiptracker.trip.TripAdapter
import aandroid.paandroidportfolio.tiptracker.utility.DatePicker
import aandroid.paandroidportfolio.tiptracker.utility.getMonthEnd
import aandroid.paandroidportfolio.tiptracker.utility.getMonthStart
import android.database.sqlite.SQLiteException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    companion object{
        const val invalidDateRange = "Invalid Date Range"
    }
    private val sharedViewModel: ViewModel by activityViewModels()
    private lateinit var dateTextView: TextView
    private lateinit var recView: RecyclerView
    private lateinit var adapter: TripAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var tempNewDate: String
    private lateinit var tempEndDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupUIComponents()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        updateWithDateRange()
    }

    private fun setupUIComponents() = with(binding) {
        recView =homeRecView
        adapter = TripAdapter(sharedViewModel.tripList, sharedViewModel, sharedViewModel.savedMPG)
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(context)
        dateTextView = tvDateRange
        dateTextView.text = "${sharedViewModel.startDate} - ${sharedViewModel.endDate}"
        setDateRange.setOnClickListener {
            selectDateRange()
        }
        setMonthRange.setOnClickListener {
            selectMonthRange()
        }
    }

    private fun selectDateRange() {
        //Bc it's asynchronous android won't wait for datepicker to end, which means we have to nest the datepicker
        DatePicker.showDatePickerDialog(context, "Select Start Date") { selectedDate ->
            tempNewDate = selectedDate
            DatePicker.showDatePickerDialog(context, "Select End Date") { selectedDate ->
                tempEndDate = selectedDate

                if (validateDateRange(tempNewDate, tempEndDate)) {
                    updateUIDateRange()
                    updateWithDateRange()
                } else {
                    Toast.makeText(context, invalidDateRange, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUIDateRange(){
        dateTextView.text = "${sharedViewModel.startDate} - ${sharedViewModel.endDate}"
    }

    private fun selectMonthRange(){
       DatePicker.showDatePickerDialog(requireContext(), "Select Month"){ selectedDate ->
           tempNewDate = selectedDate
           tempEndDate = tempNewDate
           //Now we churn out the month
           sharedViewModel.startDate = Calendar.getInstance().getMonthStart(tempNewDate)
           sharedViewModel.endDate = Calendar.getInstance().getMonthEnd(tempEndDate)
           updateWithDateRange()
           updateUIDateRange()
       }

    }

    private fun validateDateRange(start: String, end: String): Boolean {
        //TODO handle single date ranges
        //if(start == end)return true
        val myformat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myformat, Locale.US)
        val startDateObj = sdf.parse(start)
        val endDateObj = sdf.parse(end)
        if (startDateObj != null && endDateObj != null) {
            if (endDateObj.after(startDateObj)) {
                sharedViewModel.startDate = start
                sharedViewModel.endDate = end
                return true
            }
        }
        return false
    }

    private fun updateWithDateRange() {
        // Get trip data within the date range and update the adapter
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val trips = sharedViewModel.getTripInRange(sharedViewModel.startDate, sharedViewModel.endDate)
                withContext(Dispatchers.Main) {
                    sharedViewModel.tripList = trips
                    adapter.resetData(sharedViewModel.tripList)
                }
            }catch (e: SQLiteException) {
                withContext(Dispatchers.Main) {
                    dateTextView.text = "Database error: ${e.message}"
                }
            }
            catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    dateTextView.text = "If this ran, i have died. That or room database is not working, which is unlikely. I'm dead."
                }
            }
        }
    }

}