package aandroid.paandroidportfolio.tiptracker.trip

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "TripDatabase")
data class Trip(
    val money: Int,
    var mileage: Int,
    var date: String,
    var hours: Int,
    var gasprice: Float,
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null
)
