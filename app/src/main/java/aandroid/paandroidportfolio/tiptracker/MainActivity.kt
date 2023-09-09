package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.fragments.StatFragment
import aandroid.paandroidportfolio.tiptracker.fragments.HomeFragment
import aandroid.paandroidportfolio.tiptracker.fragments.AddTripFragment
import aandroid.paandroidportfolio.tiptracker.fragments.FragmentType
import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    val sharedvm: ViewModel by viewModels()
    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences
        = getSharedPreferences("savedata", Context.MODE_PRIVATE)

        //RoomDB must be setup before we fill the recycler view with it's values
        scope.launch {
                sharedvm.savedMPG = sharedPreferences.getFloat("myFloat", 25f)
                Log.d(TAG, "Value retrieved: ${sharedvm.savedMPG}")

            sharedvm.roomSetup(this@MainActivity)
            sharedvm.date = LocalDate.now().toString()

            switchFragment(FragmentType.HOME)
        }

        uiBind(findViewById<View>(android.R.id.content).rootView)

    }

    private fun uiBind(rootview: View){
        //Buttons
        val statTab = findViewById<Button>(R.id.mileageFragButton)
        val homeTab = findViewById<Button>(R.id.tipFragButton)
        val addTripTap = findViewById<Button>(R.id.settingFragButton)

        statTab.setOnClickListener {
            switchFragment(FragmentType.STAT)
        }

        homeTab.setOnClickListener {
            switchFragment(FragmentType.HOME)
        }

        addTripTap.setOnClickListener {
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