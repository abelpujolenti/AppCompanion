package cdi.interfacedesign.lolrankedtracker.leagueoflegends

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cdi.interfacedesign.lolrankedtracker.R

class PlayerViewHolder(view: View, var player: PlayerData? = null) : ViewHolder(view) {

    val id2 by lazy { view.findViewById<TextView>(R.id.id) }
    val puuid by lazy { view.findViewById<TextView>(R.id.puuid) }
    val profileIconId by lazy { view.findViewById<TextView>(R.id.profileIconId) }
    val summonerLevel by lazy { view.findViewById<TextView>(R.id.summonerLevel) }

    fun SetupWithPlayer(player: PlayerData){
        id2.text = player.id
        puuid.text = player.puuid
        profileIconId.text = player.profileIconId.toString()
        summonerLevel.text = player.summonerLevel.toString()
        this.player = player
    }


}