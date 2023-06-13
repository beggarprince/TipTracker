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

        //Update values to reflect viewmodel values
        sfnMiles.text = "Miles Driven: "+sharedViewModel.sfnMiles.toString()
        sfnHours.text = "Total Hours: " + sharedViewModel.sfnHours.toString()
        sfnTotalEarned.text = "Total Earned: " + sharedViewModel.sfnTotalMoney.toString()
        sfnGasExpenses.text = "Gas Expense: " + sharedViewModel.sfnGasExpenses.toString()
        sfnNetEarning.text = "Net Earning: " + sharedViewModel.sfnNetMoney.toString()
        sfnHourlyRate.text = "Hourly Rate: " + sharedViewModel.sfnHourlyRate.toString()


        return rootview
    }



}