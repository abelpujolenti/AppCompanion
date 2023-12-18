package cdi.interfacedesign.lolrankedtracker.leagueoflegends

data class PlayerData(val id: String, val puuid: String, val name: String,
                      val profileIconId: Int, val summonerLevel: Long/*,
                      val matchList: List<String>, val leagueDataSolo: LeagueData,
                      val leagueDataFlex: LeagueData*/)