package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.databinding.ActivityMainBinding
import aandroid.paandroidportfolio.tiptracker.fragments.AddTripFragment
import aandroid.paandroidportfolio.tiptracker.utility.getCurrentMonday
import aandroid.paandroidportfolio.tiptracker.utility.getCurrentSunday
import aandroid.paandroidportfolio.tiptracker.fragments.FragmentType
import aandroid.paandroidportfolio.tiptracker.fragments.HomeFragment
import aandroid.paandroidportfolio.tiptracker.fragments.StatFragment
import aandroid.paandroidportfolio.tiptracker.utility.UserPreferences
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreferences = UserPreferences.getInstance(this)

        //RoomDB must be setup before we fill the recycler view with it's values
        scope.launch {
            sharedViewModel.startDate = Calendar.getInstance().getCurrentMonday()
            sharedViewModel.endDate = Calendar.getInstance().getCurrentSunday()

            sharedViewModel.savedMPG = userPreferences.floatMPG

            sharedViewModel.initializeRoom(this@MainActivity)
            sharedViewModel.todayDate = LocalDate.now().toString()

            switchFragment(FragmentType.HOME)
            binding.bottomNavigation.selectedItemId = R.id.tipsTab

        }
        uiBind()

    }

    private fun uiBind() {
        val bottomNavBar: BottomNavigationView = binding.bottomNavigation
        bottomNavBar.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.tipsTab -> {
                    switchFragment(FragmentType.HOME)
                    true
                }
                R.id.addTab -> {
                    switchFragment(FragmentType.ADD_TRIP)
                    true
                }
                R.id.statTab -> {
                    switchFragment(FragmentType.STAT)
                    true
                }
                else -> false
            }
        }
    }

    fun switchFragment(type: FragmentType) {
        val fragment = when (type) {
            FragmentType.HOME -> HomeFragment()
            FragmentType.ADD_TRIP -> AddTripFragment()
            FragmentType.STAT -> StatFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragment, fragment)
            .commit()
    }

}