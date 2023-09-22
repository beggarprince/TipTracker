package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.databinding.ActivityMainBinding
import aandroid.paandroidportfolio.tiptracker.fragments.AddTripFragment
import aandroid.paandroidportfolio.tiptracker.utility.getCurrentMonday
import aandroid.paandroidportfolio.tiptracker.utility.getCurrentSunday
import aandroid.paandroidportfolio.tiptracker.fragments.FragmentType
import aandroid.paandroidportfolio.tiptracker.fragments.HomeFragment
import aandroid.paandroidportfolio.tiptracker.fragments.StatFragment
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val sharedViewModel: ViewModel by viewModels()
    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO: Change savedata to a more appropriate name
        val sharedPreferences = getSharedPreferences("savedata", Context.MODE_PRIVATE)

        //RoomDB must be setup before we fill the recycler view with it's values
        scope.launch {
            sharedViewModel.startDate = Calendar.getInstance().getCurrentMonday()
            sharedViewModel.endDate = Calendar.getInstance().getCurrentSunday()

            sharedViewModel.savedMPG = sharedPreferences.getFloat("myFloat", 25f)
            Log.d(TAG, "Value retrieved: ${sharedViewModel.savedMPG}")

            sharedViewModel.initializeRoom(this@MainActivity)
            sharedViewModel.date = LocalDate.now().toString()

            switchFragment(FragmentType.HOME)
        }
        uiBind()

    }

    private fun uiBind() {
        binding.mileageFragButton.setOnClickListener {
            switchFragment(FragmentType.STAT)
        }

        binding.tipFragButton.setOnClickListener {
            switchFragment(FragmentType.HOME)
        }

        binding.addTripFragButton.setOnClickListener {
            switchFragment(FragmentType.ADDTRIP)
        }
    }

    fun switchFragment(type: FragmentType) {
        val fragment = when (type) {
            FragmentType.HOME -> HomeFragment()
            FragmentType.ADDTRIP -> AddTripFragment()
            FragmentType.STAT -> StatFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragment, fragment)
            .commit()
    }

}