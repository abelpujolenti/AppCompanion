package cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppMainMenu
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppToolbar
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppTrackedPlayer
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.LeaderboardPlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.LeaderboardPlayerProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.TrackedPlayerProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder.LeaderboardPlayerViewHolder
import cdi.interfacedesign.lolrankedtracker.utils.SharedPreferencesManager
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
        viewHolder.itemView.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val player: PlayerData? = viewHolder.leaderboardPlayerData?.let { player ->
                    playerProvider.GetPlayer(player.summonerName)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    if (player != null) {
                        val appToolbar = AppToolbar.get()
                        val sharedPreferences = SharedPreferencesManager
                        sharedPreferences.player = player
                        sharedPreferences.previousScreen = sharedPreferences.LEADERBOARD_KEY
                        AppMainMenu.get().ReplaceScreen(AppTrackedPlayer(), false)
                        appToolbar.toolbar.title = MyApp.get().currentActivity.resources.getString(R.string.profile_title)
                        appToolbar.ShowBackItem()
                        appToolbar.HideNavigationItem()
                    }
                }
            }
        }
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

                val sortedLeaderboardPlayersList = leaderboardPlayersList.sortedWith(compareByDescending<LeaderboardPlayerData> { it.leaguePoints })

                for (leaderboardPlayer in sortedLeaderboardPlayersList)
                {
                    leaderboardPlayerList.add(leaderboardPlayer)
                }

                notifyDataSetChanged()

                requestingData = false

            }
        }
    }
}