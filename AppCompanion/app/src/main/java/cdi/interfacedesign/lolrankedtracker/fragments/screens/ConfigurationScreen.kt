package cdi.interfacedesign.lolrankedtracker.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.utils.SharedPreferencesManager
import java.util.Locale

class ConfigurationScreen : Fragment() {

    lateinit var fragmentView: View
    val spinnerPlatform by lazy { fragmentView.findViewById<Spinner>(R.id.spinner_platforms) }
    val logoutButton by lazy { fragmentView.findViewById<ImageButton>(R.id.logout_button) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.screen_configuration, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val regions = resources.getStringArray(R.array.Regions)

        val adapter = ArrayAdapter(MyApp.get().currentActivity, android.R.layout.simple_spinner_item, regions)

        spinnerPlatform.adapter = adapter

        val spinnerPosition: Int = adapter.getPosition(SharedPreferencesManager.platformSelected.uppercase(Locale.ROOT))

        spinnerPlatform.setSelection(spinnerPosition)

        spinnerPlatform.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spinnerPlatform.selectedItem.toString())
                {
                    regions[0] -> {
                        SharedPreferencesManager.regionSelected = "americas"
                        SharedPreferencesManager.platformSelected ="br1"
                    }
                    regions[1] -> {
                        SharedPreferencesManager.regionSelected = "europe"
                        SharedPreferencesManager.platformSelected ="eun1"
                    }
                    regions[2] -> {
                        SharedPreferencesManager.regionSelected = "europe"
                        SharedPreferencesManager.platformSelected ="euw1"
                    }
                    regions[3] -> {
                        SharedPreferencesManager.regionSelected = "asia"
                        SharedPreferencesManager.platformSelected ="jp1"
                    }
                    regions[4] -> {
                        SharedPreferencesManager.regionSelected = "asia"
                        SharedPreferencesManager.platformSelected ="kr"
                    }
                    regions[5] -> {
                        SharedPreferencesManager.regionSelected = "americas"
                        SharedPreferencesManager.platformSelected ="la1"
                    }
                    regions[6] -> {
                        SharedPreferencesManager.regionSelected = "americas"
                        SharedPreferencesManager.platformSelected ="la2"
                    }
                    regions[7] -> {
                        SharedPreferencesManager.regionSelected = "americas"
                        SharedPreferencesManager.platformSelected ="na1"
                    }
                    regions[8] -> {
                        SharedPreferencesManager.regionSelected = "sea"
                        SharedPreferencesManager.platformSelected ="oc1"
                    }
                    regions[9] -> {
                        SharedPreferencesManager.regionSelected = "sea"
                        SharedPreferencesManager.platformSelected ="ph2"
                    }
                    regions[10] -> {
                        SharedPreferencesManager.regionSelected = "europe"
                        SharedPreferencesManager.platformSelected ="ru"
                    }
                    regions[11] -> {
                        SharedPreferencesManager.regionSelected = "sea"
                        SharedPreferencesManager.platformSelected ="sg2"
                    }
                    regions[12] -> {
                        SharedPreferencesManager.regionSelected = "sea"
                        SharedPreferencesManager.platformSelected ="th2"
                    }
                    regions[13] -> {
                        SharedPreferencesManager.regionSelected = "europe"
                        SharedPreferencesManager.platformSelected ="tr1"
                    }
                    regions[14] -> {
                        SharedPreferencesManager.regionSelected = "sea"
                        SharedPreferencesManager.platformSelected ="tw2"
                    }
                    regions[15] -> {
                        SharedPreferencesManager.regionSelected = "sea"
                        SharedPreferencesManager.platformSelected ="vn2"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        logoutButton.setOnClickListener {
            MyApp.get().currentActivity.finish()
        }

    }
}