package aandroid.paandroidportfolio.tiptracker.fragments

import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.trip.TripAdapter
import android.app.DatePickerDialog
import android.content.ContentValues
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

    companion object {
        fun newInstance() = HomeFragment()
    }


    private val sharedViewModel: ViewModel by activityViewModels()
    private lateinit var dateTextView: TextView
    var dateControl = 0
    private var startDate = ""
    private var endDate = ""

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


        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(calendar)
        }
        dateTextView = rootview.findViewById<TextView>(R.id.tv_dateRange)
        val setDateRange = rootview.findViewById<Button>(R.id.setDateRange)
        dateTextView.text = sharedViewModel.date

        setDateRange.setOnClickListener {
            context?.let { it1 ->
                val datePickerDialog = DatePickerDialog(
                    it1, datePicker, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.setTitle("Choose starting date")
                datePickerDialog.show()
                datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateLabel(calendar)

                    // end date
                    val endDatePickerDialog = DatePickerDialog(
                        it1, datePicker, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    endDatePickerDialog.setTitle("Choose end date")
                    endDatePickerDialog.show()
                    endDatePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, month)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateLabel(calendar)
                        Log.d(ContentValues.TAG, "End date input received")
                        Log.d(ContentValues.TAG, "Start-end:   " + startDate + "- " + endDate)
//                        CoroutineScope(Dispatchers.IO).launch {
//                            sharedViewModel.tripList = sharedViewModel.getTripInRange(startDate, endDate)
//                        }
//                        adapter.resetData()
                        CoroutineScope(Dispatchers.IO).launch {
                            val deferred = async { sharedViewModel.getTripInRange(startDate, endDate) }
                            sharedViewModel.tripList = deferred.await()

                            withContext(Dispatchers.Main) {
                                adapter.resetData(sharedViewModel.tripList)
                            }
                        }


                    }
                }
            }

        }

        return rootview
    }
    private fun updateLabel(calendar: Calendar) {
        val myformat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myformat, Locale.US)
        //Set the date text to the start
        if (dateControl == 0) {
            dateTextView.setText(sdf.format(calendar.time))
            startDate = sdf.format(calendar.time)
            dateControl++
        } else
            endDate = sdf.format(calendar.time)

        dateTextView.text = "$startDate - $endDate"
    }


}