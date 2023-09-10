package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.Room.RoomDelete
import aandroid.paandroidportfolio.tiptracker.Room.TripDao
import aandroid.paandroidportfolio.tiptracker.Room.TripDatabase
import aandroid.paandroidportfolio.tiptracker.trip.Trip
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var mpgHomeFragmentCompare: Float = 0.0f //late init not allowed
    var date: String = ""

    suspend fun roomSetup(applicationContext: Context) = withContext(Dispatchers.IO)
    {        //Room Database
        database = Room.databaseBuilder(
            applicationContext,
            TripDatabase::class.java, "my-database"
        ).addMigrations(TripDatabase.Companion.MIGRATION_1_2)
            .build()
        daoReference = database?.tripDao()
        Log.d(TAG, "Room  Init  Setup")
        tripList = getInitialList()
        Log.d(TAG, "Room Setup Finished")
    }

    private fun inspectTripList() {
        tripList.forEachIndexed { index, trip ->
            trip?.let {
                Log.d(
                    "TripListInspector",
                    "Trip at index $index: Money=${it.money}, Mileage=${it.mileage}, Date=${it.date}, Hours=${it.hours}, GasPrice=${it.gasprice}, ID=${it.id}"
                )
            } ?: run {
                Log.d("TripListInspector", "Trip at index $index is null")
            }
        }
    }

    fun addTrip(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {
            daoReference?.insert(trip)
            //TODO: Add appropriate get based on the user's date range or if they have not specified, simply the current week.
            tripList = getInitialList()
        }
    }

    private fun getInitialList(): MutableList<Trip> {
        val trips = daoReference?.getLastWeekTrips() as MutableList<Trip>
        return trips
    }

    suspend fun getTripInRange(startDate: String, endDate: String): MutableList<Trip> {
        val trips = daoReference?.getTripsInRange(startDate, endDate) as MutableList<Trip>
        return trips
    }


    override fun deleteTripFromRoomDatabase(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {

            val newtrip = trip.id?.let { daoReference?.getTrip(it) }
            if (newtrip != null) {
                daoReference?.delete(newtrip)
            } else Log.d(TAG, "TRIP ID IS NULL")

            inspectTripList()
        }
    }


}