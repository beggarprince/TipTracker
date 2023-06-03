package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.Room.TripDao
import aandroid.paandroidportfolio.tiptracker.Room.TripDatabase
import aandroid.paandroidportfolio.tiptracker.usecase.StatFragment
import aandroid.paandroidportfolio.tiptracker.usecase.HomeFragment
import aandroid.paandroidportfolio.tiptracker.usecase.AddTrip
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val sharedvm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedvm.roomSetup(this)

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