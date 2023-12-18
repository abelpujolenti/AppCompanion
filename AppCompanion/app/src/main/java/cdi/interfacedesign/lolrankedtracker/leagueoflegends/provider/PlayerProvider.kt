package cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository

class PlayerProvider(val repository: LeagueOfLegendsRepository) {

    suspend fun GetPaginatedPlayer(summonerName: String) : PlayerData{
        return repository.GetPlayerProfile(summonerName)
    }


}