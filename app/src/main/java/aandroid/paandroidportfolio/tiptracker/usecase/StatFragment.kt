package aandroid.paandroidportfolio.tiptracker.usecase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aandroid.paandroidportfolio.tiptracker.R
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Button

class StatFragment : Fragment() {

    companion object {
        fun newInstance() = StatFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootview = inflater.inflate(R.layout.fragment_stats, container, false)

        val mileageButton = rootview.findViewById<Button>(R.id.mileageButton)
        mileageButton.setOnClickListener{
            Log.d(TAG,"mileage button pressed")
        }


return rootview
    }

}