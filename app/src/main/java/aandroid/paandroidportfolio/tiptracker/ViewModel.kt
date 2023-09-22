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

    var savedMPG: Float = 25f
    var tripList: MutableList<Trip> = mutableListOf<Trip>()

    private var database: TripDatabase? = null
    private var daoReference: TripDao? = null
    var date: String = ""
    var startDate: String =""
    var endDate: String = ""
    suspend fun initializeRoom(applicationContext: Context) = withContext(Dispatchers.IO)
    {        //Room Database
        database = Room.databaseBuilder(
            applicationContext,
            TripDatabase::class.java, "my-database"
        ).addMigrations(TripDatabase.Companion.MIGRATION_1_2)
            .build()
        daoReference = database?.tripDao()
        tripList = getTripInRange(startDate,endDate)
    }

    fun addTrip(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {
            daoReference?.insert(trip)
            tripList = getInitialList()
        }
    }

    private fun getInitialList(): MutableList<Trip> {
        val trips = daoReference?.getLastWeekTrips() as MutableList<Trip>
        return trips
    }

     fun getTripInRange(startDate: String, endDate: String): MutableList<Trip> {
        val trips = daoReference?.getTripsInRange(startDate, endDate) as MutableList<Trip>
        return trips
    }

    override fun deleteTripFromRoomDatabase(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {

            val newtrip = trip.id?.let { daoReference?.getTrip(it) }
            if (newtrip != null) {
                daoReference?.delete(newtrip)
            } else Log.d(TAG, "TRIP ID IS NULL")

        }
    }

}