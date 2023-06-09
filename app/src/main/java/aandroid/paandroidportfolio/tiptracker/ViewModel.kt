package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.Room.RoomDelete
import aandroid.paandroidportfolio.tiptracker.Room.TripDao
import aandroid.paandroidportfolio.tiptracker.Room.TripDatabase
import aandroid.paandroidportfolio.tiptracker.trip.Trip
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ViewModel : ViewModel(), RoomDelete {

    var tripList: MutableList<Trip> = mutableListOf<Trip>()

    private var database: TripDatabase? = null
    private var daoReference: TripDao? = null
    var date: String =""
    private val ioScope = CoroutineScope(Dispatchers.IO)

    suspend fun roomSetup(applicationContext: Context) = withContext(Dispatchers.IO)
    {        //Room Database
        database = Room.databaseBuilder(
            applicationContext,
            TripDatabase::class.java, "my-database"
        ).build()
        daoReference = database?.tripDao()
        Log.d(TAG, "Room  Init  Setup")
        tripList = getInitialList()
        Log.d(TAG, "Room Setup Finished")
    }

    fun addTrip(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {
            //manually set the trip id
            val tripwithid = trip.copy(id = daoReference?.insert(trip)?.toInt())
            tripList.add(tripwithid)

        }

    }

    private fun getInitialList(): MutableList<Trip> {
        val trips = daoReference?.getAll() as MutableList<Trip>
        return trips
    }

    suspend fun getTripInRange(startDate: String, endDate: String): MutableList<Trip>{
        val trips = daoReference?.getTripsInRange(startDate,endDate) as MutableList<Trip>
        return trips
    }


    override fun deleteTripFromRoomDatabase(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {
            //daoReference?.delete(trip)
            // daoReference?.deleteAll()
            val trip = trip.id?.let { daoReference?.getTrip(it) }
            if (trip != null) {
                daoReference?.delete(trip)
            } else Log.d(TAG, "TRIP ID IS NULL")

            Log.d(TAG, "Deleting Trip")
        }
    }



}