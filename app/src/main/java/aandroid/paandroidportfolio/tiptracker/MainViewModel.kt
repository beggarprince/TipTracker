package aandroid.paandroidportfolio.tiptracker

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var scopeTest: String ="THIS HATH FAILED"

    var tripAdapter = TripAdapter(mutableListOf())
    fun readTripVM(trip: Trip){
        Log.d(ContentValues.TAG,"Congrats you have " +trip.money.toString()+" cash money")
    }

    fun addTrip(trip: Trip) {
        tripAdapter.addTrip(trip)
    }
}