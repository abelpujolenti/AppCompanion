package cdi.interfacedesign.lolrankedtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.PlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsMockRepository
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsSharedDataBase
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    val table by lazy { findViewById<RecyclerView>(R.id.tracked_players) }
    val toolbar by lazy { findViewById<MaterialToolbar>(R.id.app_toolbar) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_main_activity)

        //table.layoutManager = LinearLayoutManager(this)

        //val repository = LeagueOfLegendsSharedDataBase()
        //val repository = LeagueOfLegendsMockRepository();
        val repository = LeagueOfLegendsApiRepository();

        //table.adapter = PlayerAdapter(repository)
    }
}