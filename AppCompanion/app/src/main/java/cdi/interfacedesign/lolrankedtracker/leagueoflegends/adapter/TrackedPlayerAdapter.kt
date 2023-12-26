package cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.activities.TrackedPlayerActivity
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder.TrackedPlayerViewHolder
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.TrackedPlayerProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackedPlayerAdapter(repository: LeagueOfLegendsRepository) : Adapter<TrackedPlayerViewHolder>() {

    private val provider: TrackedPlayerProvider = TrackedPlayerProvider(repository)
    private var playerList: MutableList<PlayerData> = mutableListOf()
    private var requestingData = false;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackedPlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = TrackedPlayerViewHolder(layoutInflater.inflate(R.layout.cell_tracker, parent, false))
        viewHolder.itemView.setOnClickListener{
            MyApp.get().player = viewHolder.player!!
            val intent = Intent(parent.context, TrackedPlayerActivity::class.java)
            intent.putExtra("Player", viewHolder.player)
            parent.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun getItemCount(): Int = playerList.count()

    override fun onBindViewHolder(holder: TrackedPlayerViewHolder, position: Int) {
        holder.SetupWithTrackedPlayer(playerList[position])
    }

    fun LoadPlayerData(summonerName: String){

        if (requestingData)
        {
            return
        }

        requestingData = true

        CoroutineScope(Dispatchers.IO).launch {

            val player: PlayerData? = provider.GetPlayer(summonerName)

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