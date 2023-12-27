package cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.activities.TrackedPlayerActivity
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.LeaderboardPlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.LeaderboardPlayerProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.TrackedPlayerProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder.LeaderboardPlayerViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeaderboardPlayerAdapter(repository: LeagueOfLegendsRepository) : Adapter<LeaderboardPlayerViewHolder>() {


    private val leaderboardProvider: LeaderboardPlayerProvider = LeaderboardPlayerProvider(repository)
    private val playerProvider: TrackedPlayerProvider = TrackedPlayerProvider(repository)
    private var leaderboardPlayerList: MutableList<LeaderboardPlayerData> = mutableListOf()
    private var requestingData = false;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardPlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = LeaderboardPlayerViewHolder(layoutInflater.inflate(R.layout.cell_leaderboard_player, parent, false))
        /*viewHolder.itemView.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val player: PlayerData? = viewHolder.leaderboardPlayerData?.let { player ->
                    playerProvider.GetPlayer(player.summonerName)
                }
                if (player != null) {
                    MyApp.get().player = player
                    val intent = Intent(parent.context, TrackedPlayerActivity::class.java)
                    intent.putExtra("Player", player)
                    parent.context.startActivity(intent)
                }
            }
        }*/
        return viewHolder
    }

    override fun getItemCount(): Int = leaderboardPlayerList.count()

    override fun onBindViewHolder(holder: LeaderboardPlayerViewHolder, position: Int) {
        holder.SetupWithLeaderboardPlayers(leaderboardPlayerList[position], playerProvider)
    }

    fun LoadLeaderboardPlayersData(queueType: String, tier: String, rank: String, page: Int){

        if (requestingData)
        {
            return
        }

        leaderboardPlayerList.clear()

        requestingData = true

        CoroutineScope(Dispatchers.IO).launch {

            val leaderboardPlayersList: List<LeaderboardPlayerData> =
                leaderboardProvider.GetPaginatedPlayers(queueType, tier, rank, page)

            CoroutineScope(Dispatchers.Main).launch {

                for (leaderboardPlayer in leaderboardPlayersList)
                {
                    leaderboardPlayerList.add(leaderboardPlayer)
                }

                notifyDataSetChanged()

                requestingData = false

            }
        }
    }
}