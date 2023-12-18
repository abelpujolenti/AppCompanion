package cdi.interfacedesign.lolrankedtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.PlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsMockRepository
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsSharedDataBase

class MainActivity : AppCompatActivity() {

    val table by lazy { findViewById<RecyclerView>(R.id.myHeroesTable) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_screen)

        table.layoutManager = LinearLayoutManager(this)

        //val repository = LeagueOfLegendsSharedDataBase()
        val repository = LeagueOfLegendsMockRepository();
        //val repository = LeagueOfLegendsApiRepository();

        table.adapter = PlayerAdapter(repository)

    }
}