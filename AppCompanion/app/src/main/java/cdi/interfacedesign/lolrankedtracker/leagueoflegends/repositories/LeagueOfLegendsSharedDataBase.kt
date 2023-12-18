package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.utils.SharedPreferencesManager

class LeagueOfLegendsSharedDataBase : LeagueOfLegendsRepository {
    override suspend fun GetPlayersProfile(offset: Int, limit: Int): MutableList<PlayerData> {
        TODO("Not yet implemented")
    }

    override suspend fun GetPlayerProfile(summonerName: String): PlayerData {

        val players = SharedPreferencesManager.Players
        TODO("Not yet implemented")
    }

    override suspend fun GetLeague() {
        TODO("Not yet implemented")
    }

    override suspend fun GetMatchList() {
        TODO("Not yet implemented")
    }

    override suspend fun GetMatch() {
        TODO("Not yet implemented")
    }

    override suspend fun GetLeaderboard() {
        TODO("Not yet implemented")
    }
}