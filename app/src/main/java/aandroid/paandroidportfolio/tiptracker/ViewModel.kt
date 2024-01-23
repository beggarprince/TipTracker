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
import java.io.File


class ViewModel : ViewModel(), RoomDelete {
    operator fun <T> List<T>.component6() = get(5)
    var savedMPG: Float = 25f
    var tripList: MutableList<Trip> = mutableListOf<Trip>()

    private var database: TripDatabase? = null
    private var daoReference: TripDao? = null
    var todayDate: String = ""
    var startDate: String =""
    var endDate: String = ""
    var dateRange: String =""

    fun updateDateRange(){
        dateRange = "${startDate} - ${endDate}"
    }
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
        }
    }

     fun getTripInRange(startDate: String, endDate: String): MutableList<Trip> {
        val trips = daoReference?.getTripsInRange(startDate, endDate) as MutableList<Trip>
        return trips
    }

    fun getAllTrips() : MutableList<Trip>{
        val trips = daoReference?.getAll() as MutableList<Trip>
        return trips
    }

    override fun deleteTripFromRoomDatabase(trip: Trip) {
        CoroutineScope(Dispatchers.IO).launch {
            val newtrip = trip.id?.let { daoReference?.getTrip(it) }
            if (newtrip != null) {
                daoReference?.delete(trip)
            } else Log.d(TAG, "TRIP ID IS NULL, NOT DELETED")

        }
    }
    fun toCsv(trip: Trip): String {
        return """"${trip.money}","${trip.mileage}","${trip.date}","${trip.hours}","${trip.gasprice}""""
    }
    fun toTrip(csv: String): Trip {
        val (money, mileage, date  , hours, gasprice) = csv.split(',', ignoreCase = false, limit = 5)
        return Trip(
            money.toFloat(), mileage.toFloat(), date, hours.toFloat(), gasprice.toFloat()
        )
    }

    //Csv would look like { money, mileage, date, hours, gasprice,
    suspend fun populateRoomWithCsv(inputStream: File) = withContext(Dispatchers.IO) {
        val reader = inputStream.bufferedReader()
        try {
            var header = reader.readLine()
            while (header != null) {
                toTrip(header)
                header = reader.readLine()
            }
        } finally {
            reader.close()
        }
    }

    suspend fun populateCsvWithRoom(outputStream : File) = withContext(Dispatchers.IO){
        val tripCopy = getAllTrips()
        for(trip in tripCopy){
            val csv = toCsv(trip)
            //Add to the csv
        }
    }

}