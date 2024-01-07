package cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter

import android.view.ViewGroup
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder.PlayerProfileViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder.PlayerMatchHistoryViewHolder

class PlayerProfileAdapter(repository: LeagueOfLegendsRepository) : Adapter<PlayerProfileViewHolder>() {

    private var requestingData = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerProfileViewHolder {
        TODO("Not yet implemented")
    }
    override fun onBindViewHolder(holder: PlayerProfileViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}