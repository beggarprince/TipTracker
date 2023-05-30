package aandroid.paandroidportfolio.tiptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TripAdapter(private val tripList: MutableList<Trip>) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {
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

        }
    }

    fun addTrip(trip: Trip){
        tripList.add(trip)
        notifyItemInserted(tripList.size -1)
    }

}