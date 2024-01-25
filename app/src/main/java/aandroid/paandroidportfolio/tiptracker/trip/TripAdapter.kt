package aandroid.paandroidportfolio.tiptracker.trip

import aandroid.paandroidportfolio.tiptracker.Room.RoomDelete
import aandroid.paandroidportfolio.tiptracker.databinding.TripBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripAdapter(
    private var recyclerViewTripList: MutableList<Trip>,
    private val deleteTripListener: RoomDelete,
    initMPG: Float
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val mpg = initMPG

    fun deleteItem(trip: Trip) {
        deleteTripListener.deleteTripFromRoomDatabase(trip)
    }

    inner class TripViewHolder(val binding: TripBinding) : RecyclerView.ViewHolder(binding.root)

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

    private fun formatToTwoDecimals(num: Float): String {
        return "%.2f".format(num)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val currentTrip = recyclerViewTripList[position]
        holder.binding.apply {
            textDistance.text = formatToTwoDecimals(currentTrip.mileage)
            textMoneyAmount.text = formatToTwoDecimals(currentTrip.money)
            textHours.text = formatToTwoDecimals(currentTrip.hours)
            date.text = currentTrip.date.substring(5).replace("-", "/")
            dateYy.text = currentTrip.date.substring(0, 4)

            gasPrice.text = formatToTwoDecimals(currentTrip.gasprice)
            gasExpense.text = formatToTwoDecimals(currentTrip.mileage / mpg * currentTrip.gasprice)
        }
    }

    // Swipe to delete functionality
    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            scope.launch {
                deleteItem(recyclerViewTripList[position])
                recyclerViewTripList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}
