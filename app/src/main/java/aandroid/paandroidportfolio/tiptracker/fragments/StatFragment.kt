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
        val sfnMiles = rootview.findViewById<TextView>(R.id.sfnMiles) // how many miles were driven
        val sfnHours = rootview.findViewById<TextView>(R.id.sfnHours) // how many hours worked
        val sfnTotalEarned = rootview.findViewById<TextView>(R.id.sfnTotalEarned) // how much money earned
        val sfnGasExpenses = rootview.findViewById<TextView>(R.id.sfnGasExpenses) // gas cost based on miles/mpg * gas price
        val sfnNetEarning = rootview.findViewById<TextView>(R.id.sfnNetEarning) // total earned - gas expenses
        val sfnHourlyRate = rootview.findViewById<TextView>(R.id.sfnHourly) // total earned / hours worked
        val sfnMyMPG = rootview.findViewById<TextView>(R.id.sfnMyMPG) // User provided MPG of their car

        //Button to switch to register fragment
        val mpgButton = rootview.findViewById<Button>(R.id.changeMPG)
        mpgButton.setOnClickListener{
            Log.d(TAG, "Register Fragment Initializing")
            val regFragment = Register()
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, regFragment)
                .commit()
        }

        //Update values to reflect viewmodel values
        sfnMiles.text = "Miles Driven: "+sharedViewModel.sfnMiles.toString()
        sfnHours.text = "Total Hours: " + sharedViewModel.sfnHours.toString()
        sfnTotalEarned.text = "Total Earned: " + sharedViewModel.sfnTotalMoney.toString()
        sfnGasExpenses.text = "Gas Expense: " + sharedViewModel.sfnGasExpenses.toString()
        sfnNetEarning.text = "Net Earning: " + sharedViewModel.sfnNetMoney.toString()
        sfnHourlyRate.text = "Hourly Rate: " + sharedViewModel.sfnHourlyRate.toString()
        sfnMyMPG.text = "My MPG: " + sharedViewModel.sfnMPG.toString()

        return rootview
    }



}