package cdi.interfacedesign.lolrankedtracker.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.PlayerMatchHistoryAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository

class MatchHistoryScreen : Fragment() {

    lateinit var fragmentView: View
    private val table by lazy { fragmentView.findViewById<RecyclerView>(R.id.match_history_cells) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.screen_player_match_history, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        table.layoutManager = LinearLayoutManager(MyApp.get().currentActivity)

        //val repository = LeagueOfLegendsSharedDataBase()
        //val repository = LeagueOfLegendsMockRepository();
        val repository = LeagueOfLegendsApiRepository();

        val playerMatchHistoryAdapter = PlayerMatchHistoryAdapter(repository, MyApp.get().player.puuid)

        table.adapter = playerMatchHistoryAdapter
    }

}