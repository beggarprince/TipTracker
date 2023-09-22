package aandroid.paandroidportfolio.tiptracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aandroid.paandroidportfolio.tiptracker.ViewModel
import aandroid.paandroidportfolio.tiptracker.databinding.FragmentStatsBinding
import aandroid.paandroidportfolio.tiptracker.trip.Trip
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
    private val duration = 2//Toast.LENGTH_SHORT
    private var hourlyRate: Float = 0.0f
    private var gasExpense: Float = 0.0f
    private var amountEarned: Float = 0.0f
    private var netEarned: Float = 0.0f
    private var mpg: Float = 25.0f
    private var gasPrice: Float = 3.0f
    private var hours: Float = 0.0f
    private var mileage: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mpg = sharedViewModel.savedMPG
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
        gasExpense = formatToTwoDecimals(mileage / mpg * gasPrice).toFloat()

        Log.d(TAG, "Gas Expenses = $mileage / $mpg * $gasPrice = $gasExpense")
        hourlyRate = formatToTwoDecimals((amountEarned - gasExpense) / hours).toFloat()

        netEarned = amountEarned - gasExpense
    }

    private fun setupUIComponents() = with(binding) {

        val sharedPreferences = requireActivity()
            .getSharedPreferences("savedata", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        tvMiles = statMiles // how many miles were driven
        tvHours = statHours // how many hours worked
        tvAmountEarned = statTotalEarned // how much money earned
        tvGasExpense = statGasExpense // gas cost based on miles/mpg * gas price
        tvNetEarned = statNetEarned // total earned - gas expenses
        tvHourlyRate = statHourly // total earned / hours worked
        val sfnMyMPG = myMpgEt // User provided MPG of their car

        //Button to switch to register fragment
        changeMpgBtn.setOnClickListener {
            try {
                mpg = sfnMyMPG.text.toString().toFloat()
                sharedViewModel.savedMPG = mpg
                updateMpg(editor, mpg)
                updateStatView()
                sfnMyMPG.setText(mpg.toString())
            } catch (e: NumberFormatException) {
                Toast.makeText(
                    requireContext(),
                    "MPG Not Updated",
                    duration
                ).show()
            }
        }
        updateStatView()
        sfnMyMPG.setText(mpg.toString())
    }

    private fun updateMpg(editor: SharedPreferences.Editor, mpg: Float) {
        editor.putFloat("myFloat", mpg)
        editor.apply()
    }

}