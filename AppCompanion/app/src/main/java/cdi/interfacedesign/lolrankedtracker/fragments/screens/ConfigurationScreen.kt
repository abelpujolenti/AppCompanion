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
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppMainMenu
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppToolbar
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

        adapter.getPosition(SharedPreferencesManager.tierSelected)

        val spinnerPosition: Int = adapter.getPosition(SharedPreferencesManager.platformSelected.uppercase())

        spinnerPlatform.setSelection(spinnerPosition)

        spinnerPlatform.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spinnerPlatform.selectedItem.toString())
                {
                    regions[0], regions[5], regions[6], regions[7] ->
                        SharedPreferencesManager.regionSelected = "americas"

                    regions[1], regions[2], regions[10], regions[13] ->
                        SharedPreferencesManager.regionSelected = "europe"

                    regions[3], regions[4] ->
                        SharedPreferencesManager.regionSelected = "asia"

                    regions[8], regions[9], regions[11], regions[12], regions[14], regions[15] ->
                        SharedPreferencesManager.regionSelected = "sea"
                }
                SharedPreferencesManager.platformSelected = spinnerPlatform.selectedItem.toString().lowercase()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        logoutButton.setOnClickListener {
            AppMainMenu.get().ReplaceScreen(LoginScreen(), false)
            AppToolbar.get().toolbar.title = resources.getString(R.string.login_title)
            AppToolbar.get().HideNavigationItem()
        }

    }
}