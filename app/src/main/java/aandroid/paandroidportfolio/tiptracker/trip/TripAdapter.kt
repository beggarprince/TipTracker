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
import java.text.DecimalFormat

class TripAdapter(private var tripList: MutableList<Trip>,
                  private val deleteTripListener : RoomDelete) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    private var mpg : Float=  25f

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
    fun setMPG(newMpg: Float){
        mpg = newMpg
        notifyDataSetChanged()
    }
    private fun formatToTwoDecimals(number: Float): String {
        val df = DecimalFormat("0.00")
        return df.format(number)
    }
    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val currentTrip = tripList[position]
        holder.itemView.apply{
            findViewById<TextView>(R.id.text_distance).text = formatToTwoDecimals(currentTrip.mileage)
            findViewById<TextView>(R.id.text_money_amount).text = formatToTwoDecimals(currentTrip.money)
            findViewById<TextView>(R.id.text_hours).text = formatToTwoDecimals(currentTrip.hours)
            findViewById<TextView>(R.id.text_date).text = currentTrip.date
            findViewById<TextView>(R.id.gas_price).text = formatToTwoDecimals(currentTrip.gasprice)
            findViewById<TextView>(R.id.gas_expense).text = formatToTwoDecimals(currentTrip.mileage / mpg * currentTrip.gasprice)

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
