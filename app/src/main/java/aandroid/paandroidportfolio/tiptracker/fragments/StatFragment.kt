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
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.text.InputType
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class StatFragment : Fragment() {

    private val sharedViewModel: ViewModel by activityViewModels()
    private lateinit var userPreferences: UserPreferences

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
    }

    private fun formatToTwoDecimals(num: Float): String {

        val rounded = "%.2f".format(num)

        val decimalIndex = rounded.indexOf('.')
        Log.d(TAG, "formatToTwoDecimals:  $num decimal index is: $decimalIndex")
        //Log.d(TAG,"formatToTwoDecimals: $decimalIndex")
        // If the number ends with no ".", append 2 zeros
        if (decimalIndex == -1) {
            Log.d(TAG, "Modified formatToTwoDecimals: $rounded.00")
            return "$rounded.00"
        }

        // If there is only one digit after ".", append 1 zero
        // 10.1 -> 10.10
        if (rounded.length - decimalIndex == 2) {
            Log.d(TAG, "Modified formatToTwoDecimals: $rounded" + "0")
            return "$rounded" + "0"
        }
        Log.d(TAG, "Result: $rounded")
        return rounded
    }
    @SuppressLint("SetTextI18n")
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

        //Log.d(TAG, "Gas Expenses = $mileage / $userPreferences.floatMPG * $gasPrice = $gasExpense")
        hourlyRate = formatToTwoDecimals((amountEarned - gasExpense) / hours).toFloat()


        netEarned = amountEarned - gasExpense

        tvMiles.text = "Miles Driven: ${formatToTwoDecimals(mileage)}"
        tvHours.text = "Total Hours: ${formatToTwoDecimals(hours)}"
        tvAmountEarned.text = "Total Earned: ${formatToTwoDecimals(amountEarned)}"
        tvGasExpense.text = "Gas Expense: ${formatToTwoDecimals(gasExpense)}"
        tvNetEarned.text = "Net Earning: ${formatToTwoDecimals(netEarned)}"
        tvHourlyRate.text = "Hourly Rate: ${formatToTwoDecimals(hourlyRate)}"


    }

    private fun setupUIComponents() = with(binding) {


        tvMiles = statMiles // how many miles were driven
        tvHours = statHours // how many hours worked
        tvAmountEarned = statTotalEarned // how much money earned
        tvGasExpense = statGasExpense // gas cost based on miles/mpg * gas price
        tvNetEarned = statNetEarned // total earned - gas expenses
        tvHourlyRate = statHourly // total earned / hours worked
        val sfnMyMPG = myMpgEt // User provided MPG of their car
        sfnMyMPG.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        statDateRange.text = sharedViewModel.dateRange

        //Button to update mpg
        changeMpgBtn.setOnClickListener {
            try {
                1 / sfnMyMPG.text.toString().toFloat()
                userPreferences.floatMPG = sfnMyMPG.text.toString().toFloat()
                sharedViewModel.savedMPG = userPreferences.floatMPG
                updateStatView()
                sfnMyMPG.setText(formatToTwoDecimals(userPreferences.floatMPG))
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