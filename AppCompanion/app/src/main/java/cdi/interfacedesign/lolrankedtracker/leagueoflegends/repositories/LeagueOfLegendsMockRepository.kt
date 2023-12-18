package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData

class LeagueOfLegendsMockRepository : LeagueOfLegendsRepository {

    companion object
    {

        private val playersListHardcoded = mutableListOf<PlayerData>(
            PlayerData("1", "2", "abel", 33, 618)
        )

    }

    override suspend fun GetPlayersProfile(offset: Int, limit: Int): MutableList<PlayerData>
    {
        val playersList = playersListHardcoded

        val listSize = playersList.size

        if (listSize <= offset)
        {
            return mutableListOf()
        }

        if (listSize <= offset + limit)
        {
            return playersList.subList(offset, listSize - 1)
        }

        return playersList.subList(offset, limit)
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