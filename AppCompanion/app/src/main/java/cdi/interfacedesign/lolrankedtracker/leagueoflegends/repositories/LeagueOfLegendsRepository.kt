package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData

interface LeagueOfLegendsRepository {

    suspend fun GetPlayersProfile(offset: Int, limit: Int): MutableList<PlayerData>
    suspend fun GetPlayerProfile(summonerName: String) : PlayerData
    suspend fun GetLeague()
    suspend fun GetMatchList()
    suspend fun GetMatch()
    suspend fun GetLeaderboard()


}