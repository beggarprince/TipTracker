package aandroid.paandroidportfolio.tiptracker.Room

import aandroid.paandroidportfolio.tiptracker.trip.Trip
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Trip::class), version = 1)
abstract class TripDatabase: RoomDatabase() {
    abstract fun tripDao(): TripDao
}