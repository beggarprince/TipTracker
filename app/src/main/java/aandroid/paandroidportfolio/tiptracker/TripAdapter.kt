package aandroid.paandroidportfolio.tiptracker

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

class TripAdapter(private val tripList: MutableList<Trip>) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    interface OnDeleteClickListener {
        fun onDeleteClick(trip: Trip)
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

            }
        }
    }

    fun addTrip(trip: Trip){
        tripList.add(trip)
        notifyItemInserted(tripList.size -1)
    }

    fun poop(){
        notifyDataSetChanged()

        Log.d(TAG,"poop i pooped my pants")
    }

}
