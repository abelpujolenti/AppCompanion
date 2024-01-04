package cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.MatchData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository

class PlayerMatchHistoryProvider(val repository: LeagueOfLegendsRepository) {

    suspend fun GetPaginatedMatches(puuid: String, start: Int, count: Int) : List<String>{
        return repository.GetMatchesList(puuid, start, count)
    }

    suspend fun GetMatch(puuid: String, matchId: String) : MatchData?{
        return repository.GetMatch(puuid, matchId)
    }

}