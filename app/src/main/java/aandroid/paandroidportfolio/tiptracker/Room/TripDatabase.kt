package aandroid.paandroidportfolio.tiptracker.Room

import aandroid.paandroidportfolio.tiptracker.Trip
import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Trip::class), version = 1)
abstract class TripDatabase: RoomDatabase() {
    abstract fun tripDao(): TripDao
}