package aandroid.paandroidportfolio.tiptracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.trip.Trip
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class StatFragment : Fragment() {

    private val sharedViewModel: ViewModel by activityViewModels()
    private lateinit var tvMiles: TextView
    private lateinit var tvHours: TextView
    private lateinit var tvAmountEarned: TextView
    private lateinit var tvGasExpense: TextView
    private lateinit var tvNetEarned: TextView
    private lateinit var tvHourlyRate: TextView
    private val duration = Toast.LENGTH_SHORT

    private var hourlyRate: Float = 0.0f
    private  var gasExpense: Float = 0.0f
    private var amountEarned: Float = 0.0f
    private   var netEarned: Float = 0.0f
    private  var mpg: Float = 25.0f
    private  var gasPrice: Float = 3.0f
    private  var hours: Float = 0.0f
    private  var mileage: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mpg = sharedViewModel.savedMPG
        val rootview = inflater.inflate(R.layout.fragment_stats, container, false)

        val fragmentContext = requireContext()
        bindUI(rootview)

        return rootview
    }
    private fun updateStatView() {
        calculateStats(sharedViewModel.tripList)
        tvMiles.text = "Miles Driven: $mileage"
        tvHours.text = "Total Hours: $hours"
        tvAmountEarned.text = "Total Earned: $amountEarned"
        tvGasExpense.text = "Gas Expense: $gasExpense"
        tvNetEarned.text = "Net Earning: $netEarned"
        tvHourlyRate.text = "Hourly Rate: $hourlyRate"
    }

    private fun calculateStats(list: MutableList<Trip>) {
        hours = 0f
        mileage = 0f
        amountEarned = 0f
        if(list.isEmpty())return

        for (l in list) {
            // hourly, gas, total earned
            hours += l.hours
            mileage += l.mileage
            amountEarned += l.money
        }

        //calc mileage
        gasExpense = mileage / mpg * gasPrice
        Log.d(TAG, "Gas Expenses = $mileage / $mpg * $gasPrice = $gasExpense")
        //calc hourly and net
        hourlyRate = (amountEarned - gasExpense) / hours
        //Log.d(TAG, "Hourly Rate: $sfnHourlyRateFloat")
        netEarned = amountEarned - gasExpense
    }

    private fun bindUI(rootview: View){

        val sharedPreferences = requireActivity()
            .getSharedPreferences("savedata", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        tvMiles = rootview.findViewById<TextView>(R.id.sfnMiles) // how many miles were driven
        tvHours = rootview.findViewById<TextView>(R.id.sfnHours) // how many hours worked
        tvAmountEarned =
            rootview.findViewById<TextView>(R.id.sfnTotalEarned) // how much money earned
        tvGasExpense =
            rootview.findViewById<TextView>(R.id.sfnGasExpenses) // gas cost based on miles/mpg * gas price
        tvNetEarned =
            rootview.findViewById<TextView>(R.id.sfnNetEarning) // total earned - gas expenses
        tvHourlyRate =
            rootview.findViewById<TextView>(R.id.sfnHourly) // total earned / hours worked
        val sfnMyMPG =
            rootview.findViewById<EditText>(R.id.sfnMyMPG) // User provided MPG of their car

        //Button to switch to register fragment
        val mpgButton = rootview.findViewById<Button>(R.id.changeMPG)
        mpgButton.setOnClickListener {

            try {
                mpg = sfnMyMPG.text.toString().toFloat()
                sharedViewModel.savedMPG = mpg
                editor.putFloat("myFloat",mpg)
                editor.apply()
                updateStatView()
                sfnMyMPG.setText(mpg.toString())
            }
            catch (e: NumberFormatException) {
                Toast.makeText(
                    requireContext(),
                    "MPG Not Updated",
                    duration
                ).show()
            }
        }

        updateStatView()
        //statsForNerds(sharedViewModel.tripList)
        sfnMyMPG.setText(mpg.toString())
    }
    private fun updateMpg(editor: SharedPreferences.Editor, mpg: Float){

        editor.putFloat("myFloat",mpg)
        editor.apply()
    }

}