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
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.TrackedPlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository

class TrackerScreen : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        table.layoutManager = LinearLayoutManager(MyApp.get().currentActivity)

        //val repository = LeagueOfLegendsSharedDataBase()
        //val repository = LeagueOfLegendsMockRepository();
        val repository = LeagueOfLegendsApiRepository();

        val trackedPlayerAdapter = TrackedPlayerAdapter(repository)

        table.adapter = trackedPlayerAdapter

        addButton.setOnClickListener {
            trackedPlayerAdapter.LoadPlayerData(searchBox.text.toString())
            searchBox.text.clear()
        }
    }
}