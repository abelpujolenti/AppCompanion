package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.MatchData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.LeagueResponse

class LeagueOfLegendsMockRepository : LeagueOfLegendsRepository {

    companion object
    {

        private val playersListHardcoded = mutableListOf<PlayerData>(
            PlayerData("1", "2", "abel", 33, 618)
        )

    }

    override suspend fun GetPlayerProfile(summonerName: String): PlayerData
    {

        var playerData = playersListHardcoded[0]

        for (player in playersListHardcoded)
        {
            if (player.name == summonerName)
            {
                playerData = player
            }
        }

        return playerData
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