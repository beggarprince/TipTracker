package aandroid.paandroidportfolio.tiptracker.fragments

import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.databinding.FragmentAddTripBinding
import aandroid.paandroidportfolio.tiptracker.databinding.FragmentHomeBinding
import aandroid.paandroidportfolio.tiptracker.trip.TripAdapter
import aandroid.paandroidportfolio.tiptracker.utility.DatePicker
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private val sharedViewModel: ViewModel by activityViewModels()
    private lateinit var dateTextView: TextView
    private var startDate = ""
    private var endDate = ""
    private lateinit var recView: RecyclerView
    private lateinit var adapter: TripAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var tempNewDate: String
    private lateinit var tempEndDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        bindUI()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(sharedViewModel.mpgHomeFragmentCompare != sharedViewModel.savedMPG){
            sharedViewModel.mpgHomeFragmentCompare = sharedViewModel.savedMPG
            adapter.mpgUpdate(sharedViewModel.mpgHomeFragmentCompare)
        }
    }

    private fun bindUI() {
        recView = binding.homeRecView
        sharedViewModel.mpgHomeFragmentCompare = sharedViewModel.savedMPG
        adapter = TripAdapter(sharedViewModel.tripList, sharedViewModel, sharedViewModel.mpgHomeFragmentCompare)
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(this.context)
        dateTextView = binding.tvDateRange
        binding.setDateRange.setOnClickListener {
            setDate()
        }
    }

    private fun setDate() {
        //Bc it's asynchronous android won't wait for datepicker to end, which means we have to nest the datepicker
        DatePicker.showDatePickerDialog(context, "Select Start Date") { selectedDate ->
            tempNewDate = selectedDate
            DatePicker.showDatePickerDialog(context, "Select End Date") { selectedDate ->
                tempEndDate = selectedDate

                if (checkDate(tempNewDate, tempEndDate)) {
                    dateTextView.text = "$startDate - $endDate"
                    updateWithDateRange()
                } else {
                    //REPLACE WITH TOAST
                    dateTextView.text = "Invalid Date Range"
                }
            }
        }
    }

    private fun checkDate(start: String, end: String): Boolean {
        val myformat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myformat, Locale.US)
        val startDateObj = sdf.parse(start)
        val endDateObj = sdf.parse(end)
        if (startDateObj != null && endDateObj != null) {
            if (endDateObj.after(startDateObj)) {
                startDate = start
                endDate = end
                return true
            }
        }
        return false
    }

    private fun updateWithDateRange() {
        // Get trip data within the date range and update the adapter
        CoroutineScope(Dispatchers.IO).launch {
            val deferred = async { sharedViewModel.getTripInRange(startDate, endDate) }
            sharedViewModel.tripList = deferred.await()
            withContext(Dispatchers.Main) {
                adapter.resetData(sharedViewModel.tripList)
            }
        }
    }


}