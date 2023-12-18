package cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.activities.MainMenuActivity
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder.PlayerViewHolder
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.PlayerProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerAdapter(private val repository: LeagueOfLegendsRepository,
                    private val paginationSize: Int = 20) : Adapter<PlayerViewHolder>() {

    private val provider: PlayerProvider = PlayerProvider(repository)
    private var playerList: MutableList<PlayerData> = mutableListOf()
    private var requestingData = false;

    init {
        GetMoreData()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = PlayerViewHolder(layoutInflater.inflate(R.layout.test_screen_2, parent, false))
        viewHolder.itemView.setOnClickListener{
            val intent = Intent(parent.context, MainMenuActivity::class.java)
            //intent.putExtra("Player", viewHolder.player)
            parent.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.SetupWithPlayer(playerList[position])
    }

    fun GetMoreData(){

        if (requestingData)
        {
            return
        }

        requestingData = true

        CoroutineScope(Dispatchers.IO).launch {

            val player2: PlayerData = provider.GetPaginatedPlayer("abeltron999")

            Log.e("RIOTCoroutine", "coroutine")

            CoroutineScope(Dispatchers.Main).launch {

                playerList.add(player2)
                notifyDataSetChanged()
                requestingData = false
            }

        }

    }
}