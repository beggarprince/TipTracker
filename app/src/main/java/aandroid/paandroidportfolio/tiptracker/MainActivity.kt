package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.fragments.StatFragment
import aandroid.paandroidportfolio.tiptracker.fragments.HomeFragment
import aandroid.paandroidportfolio.tiptracker.fragments.AddTrip
import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

            //Check for MPG saved value, set to 25 if it's missing
            var a: Int = sharedPreferences.getInt("myInteger", 1)
            if (a != 1) {
                sharedvm.sfnMPG = a
                Log.d(TAG,"A is not null")
                Log.d(TAG,a.toString())
            }
            else {
                Log.d(TAG,"A is null")
                sharedvm.sfnMPG = 25
            }

            sharedvm.roomSetup(this@MainActivity)

            sharedvm.date = LocalDate.now().toString()


            Log.d(TAG, "Home Fragment Initializing")
            val tipFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, tipFragment)
                .commit()
        }

        //Buttons
        val statSwitch = findViewById<Button>(R.id.mileageFragButton)
        statSwitch.setOnClickListener {
            val statFragment = StatFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, statFragment)
                .commit()
        }
        val tipSwitch = findViewById<Button>(R.id.tipFragButton)
        tipSwitch.setOnClickListener {
            val tipFragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, tipFragment)
                .commit()
        }
        val logTrip = findViewById<Button>(R.id.settingFragButton)
        logTrip.setOnClickListener {
            val logFragment = AddTrip()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, logFragment)
                .commit()
        }

    }

}