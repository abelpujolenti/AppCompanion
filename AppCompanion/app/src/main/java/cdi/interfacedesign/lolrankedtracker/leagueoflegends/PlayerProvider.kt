package cdi.interfacedesign.lolrankedtracker.leagueoflegends

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository

class PlayerProvider(val repository: LeagueOfLegendsRepository) {

    suspend fun GetPaginatedPlayer(summonerName: String) : PlayerData?{
        return repository.GetProfile(summonerName)
    }


}