package aandroid.paandroidportfolio.tiptracker.Room

import aandroid.paandroidportfolio.tiptracker.trip.Trip
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TripDao {
    @Insert
    fun insert(trip: Trip): Long

    @Delete
    fun delete(trip: Trip) : Int

    @Query("DELETE FROM TripDatabase WHERE id = :id")
    fun deleteById(id: Int)

    @Query("DELETE FROM TripDatabase")
    fun deleteAll()

    @Query("SELECT * FROM TripDatabase")
    fun getAll(): List<Trip>

    @Query("SELECT * FROM TripDatabase WHERE id = :id")
    fun getTrip(id: Int): Trip

    @Query("SELECT * FROM TripDatabase WHERE date BETWEEN :startDate AND :endDate")
    fun getTripsInRange(startDate: String, endDate: String): List<Trip>
}