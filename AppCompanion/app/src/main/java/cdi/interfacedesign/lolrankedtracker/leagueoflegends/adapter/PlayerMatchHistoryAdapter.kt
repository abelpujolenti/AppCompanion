package cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.PlayerMatchHistoryProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder.PlayerMatchHistoryViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.MatchData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerMatchHistoryAdapter(repository: LeagueOfLegendsRepository, private val puuid: String,
                                private val paginationSize: Int = 10) : Adapter<PlayerMatchHistoryViewHolder>() {

    private val provider: PlayerMatchHistoryProvider = PlayerMatchHistoryProvider(repository)
    private var matchList: MutableList<MatchData> = mutableListOf()
    private var requestingData = false;

    init {
        LoadMatchData(puuid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerMatchHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlayerMatchHistoryViewHolder(layoutInflater.inflate(R.layout.cell_match_history_match, parent, false))
    }

    override fun getItemCount(): Int = matchList.count()

    override fun onBindViewHolder(holder: PlayerMatchHistoryViewHolder, position: Int) {
        holder.SetupWithPlayerMatch(matchList[position])

        if (position >= matchList.size - paginationSize){
            LoadMatchData(puuid)
        }
    }

    fun LoadMatchData(puuid: String){

        if (requestingData)
        {
            return
        }

        requestingData = true

        CoroutineScope(Dispatchers.IO).launch {

            val matchesId: List<String> = provider.GetPaginatedMatches(puuid, matchList.size, paginationSize)

            CoroutineScope(Dispatchers.Main).launch {

                val matches: MutableList<MatchData?> = mutableListOf()

                for (matchId in matchesId){
                    matches.add(provider.GetMatch(puuid, matchId))
                }

                for (match in matches){
                    if (match != null &&
                        (match.queueId == 420 ||
                        match.queueId == 440 ||
                        match.queueId == 450 ||
                        match.queueId == 400 ||
                        match.queueId == 430))
                    {
                        matchList.add(match)
                    }
                }
                notifyDataSetChanged()

                requestingData = false
            }
        }

    }
}