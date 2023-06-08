package aandroid.paandroidportfolio.tiptracker.Room

import aandroid.paandroidportfolio.tiptracker.Trip

interface RoomDelete {
     //Interface so the tripadapter can access the viewmodel to call a delete using the dao
     fun deleteTripFromRoomDatabase(trip: Trip)
}