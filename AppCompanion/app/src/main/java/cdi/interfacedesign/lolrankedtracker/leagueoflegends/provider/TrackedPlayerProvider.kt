package cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository

class TrackedPlayerProvider(val repository: LeagueOfLegendsRepository) {

    suspend fun GetPlayer(summonerName: String) : PlayerData?{
        return repository.GetPlayerProfile(summonerName)
    }


}