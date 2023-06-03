package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.Room.TripDao
import aandroid.paandroidportfolio.tiptracker.Room.TripDatabase
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private var vmTripList= mutableListOf<Trip>()
     var tripAdapter = TripAdapter(vmTripList)
    private var database: TripDatabase? = null
    private var daoReference: TripDao? = null

    fun roomSetup(applicationContext: Context){        //Room Database
        CoroutineScope(Dispatchers.IO).launch {

            database = Room.databaseBuilder(
                applicationContext,
                TripDatabase::class.java, "my-database"
            ).build()
            daoReference = database?.tripDao()
            Log.d(TAG, "Room Setup")
            getInitialList()
        }

    }

    fun addTrip(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {
        tripAdapter.addTrip(trip)
        if(daoReference != null){daoReference?.insert(trip); Log.d(TAG,"INSERTING INTO DB")}
            else Log.d(TAG,"dao reference is null")
        }
    }

    fun debugReadTrip(){

        Log.d(TAG,"Reading initial list")
        for(trip in vmTripList){
            Log.d(TAG, trip.money.toString())
            tripAdapter.addTrip(trip)
        }
    }
    fun getInitialList(){
        vmTripList = daoReference?.getAll() as MutableList<Trip>
        debugReadTrip()
        Log.d(TAG,"Room Initialize List")
    }


}