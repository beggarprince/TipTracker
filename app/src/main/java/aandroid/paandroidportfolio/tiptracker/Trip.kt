package aandroid.paandroidportfolio.tiptracker

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "TripDatabase")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val money: Int,
    var mileage: Int,
    var date: String,
    var hours: Int
)