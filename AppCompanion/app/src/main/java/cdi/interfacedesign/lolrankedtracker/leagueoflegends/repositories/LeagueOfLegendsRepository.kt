package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.PlayerData

interface LeagueOfLegendsRepository {

    suspend fun GetProfile(summonerName: String) : PlayerData?
    suspend fun GetLeague()
    suspend fun GetMatchList()
    suspend fun GetMatch()
    suspend fun GetLeaderboard()


}