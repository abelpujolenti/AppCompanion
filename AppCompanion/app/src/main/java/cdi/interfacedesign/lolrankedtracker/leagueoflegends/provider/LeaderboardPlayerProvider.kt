package cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.LeaderboardPlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository

class LeaderboardPlayerProvider(val repository: LeagueOfLegendsRepository) {

    suspend fun GetPaginatedPlayers(queue: String, tier: String, rank: String, page: Int = 1) : List<LeaderboardPlayerData>{
        return repository.GetLeaderboard(queue, tier, rank, page)
    }

}