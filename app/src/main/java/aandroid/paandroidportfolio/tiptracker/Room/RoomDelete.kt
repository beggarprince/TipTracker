package aandroid.paandroidportfolio.tiptracker.Room

import aandroid.paandroidportfolio.tiptracker.Trip

interface RoomDelete {
     fun deleteTripFromRoomDatabase(trip: Trip)
}