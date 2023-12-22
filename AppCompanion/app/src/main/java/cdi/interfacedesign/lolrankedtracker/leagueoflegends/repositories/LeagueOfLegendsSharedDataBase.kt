package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.MatchData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.LeagueResponse
import cdi.interfacedesign.lolrankedtracker.utils.SharedPreferencesManager

class LeagueOfLegendsSharedDataBase : LeagueOfLegendsRepository {
    override suspend fun GetPlayerProfile(summonerName: String): PlayerData {

        val players = SharedPreferencesManager.Players
        TODO("Not yet implemented")
    }

    override suspend fun GetLeague(summonerId: String): List<LeagueResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun GetMatchesList(puuid: String, start: Int, count: Int): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun GetMatch(puuid: String, matchId: String): MatchData? {
        TODO("Not yet implemented")
    }

    override suspend fun GetLeaderboard(offset: Int, limit: Int): MutableList<PlayerData> {
        TODO("Not yet implemented")
    }
}