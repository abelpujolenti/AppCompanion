package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

data class ProfileResponse(val id: String, val puuid: String, val name: String,
                           val profileIconId: Int, val summonerLevel: Long)