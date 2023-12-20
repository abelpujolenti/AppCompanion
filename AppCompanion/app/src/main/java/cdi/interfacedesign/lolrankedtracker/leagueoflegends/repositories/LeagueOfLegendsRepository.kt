package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.MatchData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.LeagueResponse

interface LeagueOfLegendsRepository {

    suspend fun GetPlayerProfile(summonerName: String): PlayerData?
    suspend fun GetLeague(summonerId: String): List<LeagueResponse>
    suspend fun GetMatchesList(puuid: String, start: Int, count: Int) : List<String>
    suspend fun GetMatch(puuid: String, matchId: String): MatchData?
    suspend fun GetLeaderboard(offset: Int, limit: Int): MutableList<PlayerData>


}