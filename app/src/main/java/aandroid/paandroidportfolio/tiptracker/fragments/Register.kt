package aandroid.paandroidportfolio.tiptracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import aandroid.paandroidportfolio.tiptracker.R
import aandroid.paandroidportfolio.tiptracker.ViewModel
import android.content.Context
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels

//User will register vehicle with mpg information. Can register more than one, and will be asked to
//register at least one to accurately use statfragment
class Register : Fragment() {


    private val sharedViewModel: ViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootview = inflater.inflate(R.layout.fragment_register, container, false)
        var etMPG = rootview.findViewById<EditText>(R.id.et_mpg)
        var etVehicleName = rootview.findViewById<EditText>(R.id.et_vehicle_name)
        var btnRegister = rootview.findViewById<Button>(R.id.btn_register)


        val sharedPreferences = requireActivity()
            .getSharedPreferences("savedata", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        btnRegister.setOnClickListener {
            editor.putInt("myInteger", etMPG.text.toString().toInt())
            editor.apply() // or editor.commit()
            sharedViewModel.sfnMPG = etMPG.text.toString().toInt()
        }
        return rootview
    }


}