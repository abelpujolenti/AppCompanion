package cdi.interfacedesign.lolrankedtracker.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppToolbar
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.TrackedPlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository
import cdi.interfacedesign.lolrankedtracker.utils.SharedPreferencesManager

class TrackerScreen : Fragment() {

    lateinit var fragmentView: View
    private val table by lazy { fragmentView.findViewById<RecyclerView>(R.id.tracked_players) }
    private val searchBox by lazy { fragmentView.findViewById<EditText>(R.id.search_box) }
    private val addButton by lazy { fragmentView.findViewById<ImageButton>(R.id.add_button) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.screen_tracker, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppToolbar.get().ShowNavigationItem()

        table.layoutManager = LinearLayoutManager(MyApp.get().currentActivity)

        table.adapter = SharedPreferencesManager.trackedPlayerAdapter

        addButton.setOnClickListener {
            SharedPreferencesManager.trackedPlayerAdapter.LoadPlayerData(searchBox.text.toString())
            searchBox.text.clear()
        }
    }
}