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
    var date: String = ""
    var sfnHourlyRate: Float = 0.0f
    var sfnGasExpenses: Float = 0.0f
    var sfnTotalMoney: Float = 0.0f
    var sfnNetMoney: Float = 0.0f
    var sfnMPG: Float = 1.0f
    var sfnGasPrice: Float = 3.0f
    var sfnHours: Float = 0.0f
    var sfnMiles: Float = 0.0f

    //Creates an instance of database and dao
    // and fills the mutablelist tripList with trips.
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

    fun addTrip(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {
            //manually set the trip id
            //Otherwise the id isn't set for some reason, and if you delete a newly created trip it comes back up when you launch it
            //trips not created in a previous run are deletable with no bugs
            val tripwithid = trip.copy(id = daoReference?.insert(trip)?.toInt())
            tripList.add(tripwithid)
            statsForNerds(tripList)
        }

    }

    private fun getInitialList(): MutableList<Trip> {
        val trips = daoReference?.getLastWeekTrips() as MutableList<Trip>
        statsForNerds(trips)
        return trips
    }

    suspend fun getTripInRange(startDate: String, endDate: String): MutableList<Trip> {
        val trips = daoReference?.getTripsInRange(startDate, endDate) as MutableList<Trip>
        statsForNerds(trips)
        return trips
    }


    override fun deleteTripFromRoomDatabase(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {

            val newtrip = trip.id?.let { daoReference?.getTrip(it) }
            if (newtrip != null) {
                daoReference?.delete(newtrip)
            } else Log.d(TAG, "TRIP ID IS NULL")

            Log.d(TAG, "Deleting Trip")
            statsForNerds(tripList)
        }
    }

    private fun statReset() {
        sfnHours = 0f
        sfnMiles = 0f
        sfnTotalMoney = 0f
        sfnGasExpenses = 0f
        sfnHourlyRate = 0f
        sfnNetMoney = 0f
    }
//TODO check if there is any reason why I am passing the list: MutableList<Trip> instead of
    //simply accessing the tripList in the viewmodel
    fun sfn(){
        statsForNerds(tripList)
    }
    fun statsForNerds(list: MutableList<Trip>) {
        statReset()
        if(list.isEmpty())return

        for (l in list) {
            // hourly, gas, total earned
            sfnHours += l.hours
            sfnMiles += l.mileage
            sfnTotalMoney += l.money
        }

        //calc mileage
        sfnGasExpenses = sfnMiles / sfnMPG * sfnGasPrice

        //calc hourly and net
        sfnHourlyRate = (sfnTotalMoney - sfnGasExpenses) / sfnHours
        sfnNetMoney = sfnTotalMoney - sfnGasExpenses
        Log.d(TAG, "Hourly: " + sfnHourlyRate.toString())

        Log.d(TAG, "Gas Expenses: " + sfnGasExpenses.toString())

        Log.d(TAG, "Total Earned: " + sfnTotalMoney.toString())

        Log.d(TAG, "Net Earned: " + sfnNetMoney.toString())

    }

}