package cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.activities.MainMenuActivity
import cdi.interfacedesign.lolrankedtracker.activities.TrackedPlayerActivity
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder.PlayerViewHolder
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.PlayerProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerAdapter(private val repository: LeagueOfLegendsRepository) : Adapter<PlayerViewHolder>() {

    private val provider: PlayerProvider = PlayerProvider(repository)
    private var playerList: MutableList<PlayerData> = mutableListOf()
    private var requestingData = false;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = PlayerViewHolder(layoutInflater.inflate(R.layout.cell_tracker, parent, false))
        viewHolder.itemView.setOnClickListener{
            val intent = Intent(parent.context, TrackedPlayerActivity::class.java)
            intent.putExtra("Player", viewHolder.player)
            parent.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun getItemCount(): Int = playerList.count()

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.SetupWithPlayer(playerList[position])
    }

    fun LoadPlayerData(summonerName: String){

        if (requestingData)
        {
            return
        }

        requestingData = true

        CoroutineScope(Dispatchers.IO).launch {

            val player: PlayerData? = provider.GetPaginatedPlayer(summonerName)

            CoroutineScope(Dispatchers.Main).launch {

                if (player != null) {
                    for( i in 0..<playerList.size){
                        if (playerList[i].name == player.name){
                            playerList.removeAt(i)
                            break
                        }
                    }
                    playerList.add(0, player)
                    notifyDataSetChanged()
                }
                requestingData = false
            }
        }
    }
}