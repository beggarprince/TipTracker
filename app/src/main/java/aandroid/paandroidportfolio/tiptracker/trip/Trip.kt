package aandroid.paandroidportfolio.tiptracker.trip

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "TripDatabase")
data class Trip(
    val money: Float,
    var mileage: Float,
    var date: String,
    var hours: Float,
    var gasprice: Float,
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null
)
