package cdi.interfacedesign.lolrankedtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.PlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository

class MainActivity : AppCompatActivity() {

    val table by lazy { findViewById<RecyclerView>(R.id.myHeroesTable) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_screen)

        table.layoutManager = LinearLayoutManager(this)

        val repository = LeagueOfLegendsApiRepository();

        table.adapter = PlayerAdapter(repository)

    }
}