package aandroid.paandroidportfolio.tiptracker

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var scopeTest: String ="THIS HATH FAILED"
    fun readTripVM(trip: Trip){
        Log.d(ContentValues.TAG,"Congrats you have " +trip.money.toString()+" cash money")
    }
}