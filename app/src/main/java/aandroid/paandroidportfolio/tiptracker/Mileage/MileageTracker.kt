package aandroid.paandroidportfolio.tiptracker.Mileage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aandroid.paandroidportfolio.tiptracker.R
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Button

class MileageTracker : Fragment() {

    companion object {
        fun newInstance() = MileageTracker()
    }

    private lateinit var viewModel: MileageTrackerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_mileage_tracker, container, false)

        val mileageButton = rootview.findViewById<Button>(R.id.mileageButton)
        mileageButton.setOnClickListener{
            Log.d(TAG,"mileage fuck you")
        }


return rootview
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MileageTrackerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}