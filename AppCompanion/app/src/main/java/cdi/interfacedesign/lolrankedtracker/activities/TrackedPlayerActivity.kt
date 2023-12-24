package cdi.interfacedesign.lolrankedtracker.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.navigation.fragment.NavHostFragment
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppToolbar
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData

class TrackedPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_tracked_player)

        MyApp.get().currentActivity = this

        val player: PlayerData?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            player = intent.getSerializableExtra("Player", PlayerData::class.java)
        } else {
            player = intent.getSerializableExtra("Player") as? PlayerData
        }
    }
}