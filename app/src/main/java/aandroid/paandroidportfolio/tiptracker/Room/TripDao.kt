package aandroid.paandroidportfolio.tiptracker.Room

import aandroid.paandroidportfolio.tiptracker.Trip
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TripDao {
    @Insert
    fun insert(trip: Trip)

    @Delete
    fun delete(trip: Trip)

    @Query("SELECT * FROM TripDatabase")
    fun getAll(): List<Trip>
}