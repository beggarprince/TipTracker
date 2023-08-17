package aandroid.paandroidportfolio.tiptracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.ViewModel
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class StatFragment : Fragment() {

    private val sharedViewModel: ViewModel by activityViewModels()
    private lateinit var sfnMiles: TextView
    private lateinit var sfnHours: TextView
    private lateinit var sfnTotalEarned: TextView
    private lateinit var sfnGasExpenses: TextView
    private lateinit var sfnNetEarning: TextView
    private lateinit var sfnHourlyRate: TextView
    private val duration = Toast.LENGTH_SHORT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_stats, container, false)

        val fragmentContext = requireContext()
        //stats for nerds
        sfnMiles = rootview.findViewById<TextView>(R.id.sfnMiles) // how many miles were driven
        sfnHours = rootview.findViewById<TextView>(R.id.sfnHours) // how many hours worked
        sfnTotalEarned =
            rootview.findViewById<TextView>(R.id.sfnTotalEarned) // how much money earned
        sfnGasExpenses =
            rootview.findViewById<TextView>(R.id.sfnGasExpenses) // gas cost based on miles/mpg * gas price
        sfnNetEarning =
            rootview.findViewById<TextView>(R.id.sfnNetEarning) // total earned - gas expenses
        sfnHourlyRate =
            rootview.findViewById<TextView>(R.id.sfnHourly) // total earned / hours worked
        val sfnMyMPG =
            rootview.findViewById<EditText>(R.id.sfnMyMPG) // User provided MPG of their car

        val sharedPreferences = requireActivity()
            .getSharedPreferences("savedata", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //Button to switch to register fragment
        val mpgButton = rootview.findViewById<Button>(R.id.changeMPG)
        mpgButton.setOnClickListener {

            try {
                sharedViewModel.sfnMPG = sfnMyMPG.text.toString().toFloat()
            }
            catch (e: NumberFormatException) {
                Toast.makeText(
                    fragmentContext,
                    "MPG Not Updated",
                    duration
                ).show()
            }

            editor.putFloat("myFloat", sharedViewModel.sfnMPG)
            editor.apply()
            sharedViewModel.sfn()
            sfnUpdate()
            // sfnMyMPG.text = "My MPG: " + sharedViewModel.sfnMPG.toString()
            sfnMyMPG.setText(sharedViewModel.sfnMPG.toString())
        }

        sfnUpdate()
        sfnMyMPG.setText(sharedViewModel.sfnMPG.toString())

        return rootview
    }
//val number2digits:Double = String.format("%.2f", number).toDouble()
    private fun sfnUpdate() {
        sfnMiles.text = "Miles Driven: " + sharedViewModel.sfnMiles.toString()
        sfnHours.text = "Total Hours: " + sharedViewModel.sfnHours.toString()
        sfnTotalEarned.text = "Total Earned: " + sharedViewModel.sfnTotalMoney.toString()
        sfnGasExpenses.text = "Gas Expense: " + sharedViewModel.sfnGasExpenses.toString()
        sfnNetEarning.text = "Net Earning: " + sharedViewModel.sfnNetMoney.toString()
        sfnHourlyRate.text = "Hourly Rate: " + sharedViewModel.sfnHourlyRate.toString()
    }
}