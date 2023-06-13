package aandroid.paandroidportfolio.tiptracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.ViewModel
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class StatFragment : Fragment() {

    companion object {
        fun newInstance() = StatFragment()
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
        val rootview = inflater.inflate(R.layout.fragment_stats, container, false)

        //stats for nerds
        val sfnMiles = rootview.findViewById<TextView>(R.id.sfnMiles)
        val sfnHours = rootview.findViewById<TextView>(R.id.sfnHours)
        val sfnTotalEarned = rootview.findViewById<TextView>(R.id.sfnTotalEarned)
        val sfnGasExpenses = rootview.findViewById<TextView>(R.id.sfnGasExpenses)
        val sfnNetEarning = rootview.findViewById<TextView>(R.id.sfnNetEarning)
        val sfnHourlyRate = rootview.findViewById<TextView>(R.id.sfnHourly)

        sfnMiles.text = "Miles Driven: "+sharedViewModel.sfnMiles.toString()
        sfnHours.text = "Total Hours: " + sharedViewModel.sfnHours.toString()
        sfnTotalEarned.text = "Total Earned: " + sharedViewModel.sfnTotalMoney.toString()
        sfnGasExpenses.text = "Gas Expense: " + sharedViewModel.sfnGasExpenses.toString()
        sfnNetEarning.text = "Net Earning: " + sharedViewModel.sfnNetMoney.toString()
        sfnHourlyRate.text = "Hourly Rate: " + sharedViewModel.sfnHourlyRate.toString()

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

                    // Set the end date DatePickerDialog here, after start date has been set
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
                        Log.d(TAG, "End date input received")
                        Log.d(TAG, "Start-end:   " + startDate + "- " + endDate)
                        CoroutineScope(Dispatchers.IO).launch {
                            sharedViewModel.tripList =
                                sharedViewModel.getTripInRange(startDate, endDate)

                            //Probably a more elegant way to do this

                            sfnMiles.text = "Miles Driven: "+sharedViewModel.sfnMiles.toString()
                            sfnHours.text = "Total Hours: " + sharedViewModel.sfnHours.toString()
                            sfnTotalEarned.text = "Total Earned: " + sharedViewModel.sfnTotalMoney.toString()
                            sfnGasExpenses.text = "Gas Expense: " + sharedViewModel.sfnGasExpenses.toString()
                            sfnNetEarning.text = "Net Earning: " + sharedViewModel.sfnNetMoney.toString()
                            sfnHourlyRate.text = "Hourly Rate: " + sharedViewModel.sfnHourlyRate.toString()

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

        dateTextView.text = "Date range: $startDate - $endDate"
    }

}