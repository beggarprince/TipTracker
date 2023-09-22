package aandroid.paandroidportfolio.tiptracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.databinding.FragmentStatsBinding
import aandroid.paandroidportfolio.tiptracker.trip.Trip
import aandroid.paandroidportfolio.tiptracker.utility.UserPreferences
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class StatFragment : Fragment() {

    private val sharedViewModel: ViewModel by activityViewModels()
    private lateinit var binding: FragmentStatsBinding
    private lateinit var tvMiles: TextView
    private lateinit var tvHours: TextView
    private lateinit var tvAmountEarned: TextView
    private lateinit var tvGasExpense: TextView
    private lateinit var tvNetEarned: TextView
    private lateinit var tvHourlyRate: TextView
    private var hourlyRate: Float = 0.0f
    private var gasExpense: Float = 0.0f
    private var amountEarned: Float = 0.0f
    private var netEarned: Float = 0.0f
    private var gasPrice: Float = 3.0f
    private var hours: Float = 0.0f
    private var mileage: Float = 0.0f
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences.getInstance(requireContext())
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        setupUIComponents()

        return binding.root
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
    private fun formatToTwoDecimals(num: Float): String {
        val rounded = "%.2f".format(num)
        return rounded
    }
    private fun calculateStats(list: MutableList<Trip>) {
        hours = 0f
        mileage = 0f
        amountEarned = 0f
        if (list.isEmpty()) return
        for (l in list) {
            // hourly, gas, total earned
            hours += l.hours
            mileage += l.mileage
            amountEarned += l.money
        }
        gasExpense = formatToTwoDecimals(mileage / userPreferences.floatMPG * gasPrice).toFloat()

        Log.d(TAG, "Gas Expenses = $mileage / $userPreferences.floatMPG * $gasPrice = $gasExpense")
        hourlyRate = formatToTwoDecimals((amountEarned - gasExpense) / hours).toFloat()

        netEarned = amountEarned - gasExpense
    }

    private fun setupUIComponents() = with(binding) {


        tvMiles = statMiles // how many miles were driven
        tvHours = statHours // how many hours worked
        tvAmountEarned = statTotalEarned // how much money earned
        tvGasExpense = statGasExpense // gas cost based on miles/mpg * gas price
        tvNetEarned = statNetEarned // total earned - gas expenses
        tvHourlyRate = statHourly // total earned / hours worked
        val sfnMyMPG = myMpgEt // User provided MPG of their car

        //Button to update mpg
        changeMpgBtn.setOnClickListener {
            try {
                userPreferences.floatMPG = sfnMyMPG.text.toString().toFloat()
                sharedViewModel.savedMPG = userPreferences.floatMPG
                updateStatView()
                sfnMyMPG.setText(userPreferences.floatMPG.toString())
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    requireContext(),
                    "MPG Not Updated",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        updateStatView()
        sfnMyMPG.setText(userPreferences.floatMPG.toString())
    }


}