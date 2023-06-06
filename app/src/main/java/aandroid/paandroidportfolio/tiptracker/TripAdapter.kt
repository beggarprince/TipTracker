package aandroid.paandroidportfolio.tiptracker

import aandroid.paandroidportfolio.tiptracker.Room.RoomDelete
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

class TripAdapter(private val tripList: MutableList<Trip>,
                  private val deleteTripListener : RoomDelete) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    private fun deleteItem(trip: Trip){
        deleteTripListener.deleteTripFromRoomDatabase(trip)
    }
    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.trip,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val currentTrip = tripList[position]
        holder.itemView.apply{
            findViewById<TextView>(R.id.text_distance).text = currentTrip.mileage.toString()
            findViewById<TextView>(R.id.text_money_amount).text = currentTrip.money.toString()
            findViewById<TextView>(R.id.text_hours).text = currentTrip.hours.toString()

            val deleteButton = findViewById<Button>(R.id.btn_delete_trip)
            deleteButton.setOnClickListener{
                deleteItem(currentTrip)
                tripList.remove(currentTrip)
                Log.d(TAG,"Deleted Trip")
                notifyDataSetChanged()
            }
        }
    }

}
