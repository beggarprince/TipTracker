package aandroid.paandroidportfolio.tiptracker.trip

import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.Room.RoomDelete
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class TripAdapter(private var tripList: MutableList<Trip>,
                  private val deleteTripListener : RoomDelete) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.Main)
    suspend fun deleteItem(trip: Trip){
        deleteTripListener.deleteTripFromRoomDatabase(trip)
    }
    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)//laptop test

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
    fun resetData(newList: MutableList<Trip>){
        tripList = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val currentTrip = tripList[position]
        holder.itemView.apply{
            findViewById<TextView>(R.id.text_distance).text = currentTrip.mileage.toString()
            findViewById<TextView>(R.id.text_money_amount).text = currentTrip.money.toString()
            findViewById<TextView>(R.id.text_hours).text = currentTrip.hours.toString()
            findViewById<TextView>(R.id.text_date).text = currentTrip.date.toString()
            findViewById<TextView>(R.id.gas_price).text = currentTrip.gasprice.toString()

            val deleteButton = findViewById<Button>(R.id.btn_delete_trip)
            deleteButton.setOnClickListener{
                scope.launch {
                    deleteItem(currentTrip)
                    //Update view
                    tripList.remove(currentTrip)
                    //Should probably update
                    notifyItemRemoved(position)
                }
            }
        }
    }

}
