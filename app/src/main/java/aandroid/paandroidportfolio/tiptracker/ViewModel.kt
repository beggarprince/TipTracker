package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.Room.TripDao
import aandroid.paandroidportfolio.tiptracker.Room.TripDatabase
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel: ViewModel() {
    val vmTripList: LiveData<List<Trip>> get() = _vmTripList
    private val _vmTripList= MutableLiveData<List<Trip>>()
    var tripList :MutableList<Trip> = mutableListOf<Trip>()

    private var database: TripDatabase? = null
    private var daoReference: TripDao? = null

    fun roomSetup(applicationContext: Context){        //Room Database
        CoroutineScope(Dispatchers.IO).launch {

            database = Room.databaseBuilder(
                applicationContext,
                TripDatabase::class.java, "my-database"
            ).build()
            daoReference = database?.tripDao()
            Log.d(TAG, "Room  Init  Setup")
            tripList = getInitialList()

            Log.d(TAG,"Room Setup Finished")
        }

    }

    fun addTrip(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {

            tripList.add(trip)

            //dao check idk man
        if(daoReference != null){daoReference?.insert(trip); Log.d(TAG,"INSERTING INTO DB")}
            else Log.d(TAG,"dao reference is null")
        }
    }

    private fun getInitialList(): MutableList<Trip>{
        val trips = daoReference?.getAll() as MutableList<Trip>
        //_vmTripList.postValue(trips)

        Log.d(TAG,"middle")
        return trips
    }


}