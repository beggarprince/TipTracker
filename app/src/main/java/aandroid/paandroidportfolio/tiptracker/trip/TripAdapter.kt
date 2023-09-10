package aandroid.paandroidportfolio.tiptracker.trip

import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.Room.RoomDelete
import aandroid.paandroidportfolio.tiptracker.databinding.TripBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripAdapter(
    private var recyclerViewTripList: MutableList<Trip>,
    private val deleteTripListener: RoomDelete,
    private val initMPG: Float
) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {
    private val scope = CoroutineScope(Dispatchers.Main)
    private var mpg = initMPG
    fun deleteItem(trip: Trip) {
        deleteTripListener.deleteTripFromRoomDatabase(trip)
    }

    inner class TripViewHolder(val binding: TripBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnDeleteTrip.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentTrip = recyclerViewTripList[position]
                    scope.launch {
                        deleteItem(currentTrip)
                        recyclerViewTripList.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val binding = TripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recyclerViewTripList.size
    }

    fun resetData(newList: MutableList<Trip>) {
        recyclerViewTripList = newList
        notifyDataSetChanged()
    }
    fun mpgUpdate(newMpg: Float){
        mpg = newMpg
        notifyDataSetChanged()
    }

    private fun formatToTwoDecimals(num: Float): String {
        val rounded = "%.2f".format(num)
        return rounded
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val currentTrip = recyclerViewTripList[position]
        holder.binding.apply {
            textDistance.text = formatToTwoDecimals(currentTrip.mileage)
            textMoneyAmount.text = formatToTwoDecimals(currentTrip.money)
            textHours.text = formatToTwoDecimals(currentTrip.hours)
            textDate.text = currentTrip.date
            gasPrice.text = formatToTwoDecimals(currentTrip.gasprice)
            gasExpense.text = formatToTwoDecimals(currentTrip.mileage / mpg * currentTrip.gasprice)
        }
    }

}
