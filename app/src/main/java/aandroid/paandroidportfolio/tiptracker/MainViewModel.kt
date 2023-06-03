package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.Room.TripDao
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var vmTripList= mutableListOf<Trip>()
    //var tripAdapter: TripAdapter? = null
    var tripAdapter = TripAdapter(vmTripList)
    private var daoReference: TripDao? = null

    fun addTrip(trip: Trip) {
        //Add and bind to view
        tripAdapter.addTrip(trip)
        //Add to room
        daoReference?.insert(trip)
    }

    fun debugReadTrip(){
        for(trip in vmTripList){
            Log.d(TAG, trip.money.toString())
        }
    }

    fun setDaoReferenceForViewmodel(dao: TripDao?){
        daoReference = dao
    }

    fun getInitialList(){
        vmTripList = daoReference?.getAll() as MutableList<Trip>
        debugReadTrip()
    }


}