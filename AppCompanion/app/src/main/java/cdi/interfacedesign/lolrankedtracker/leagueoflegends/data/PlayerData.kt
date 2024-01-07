package cdi.interfacedesign.lolrankedtracker.leagueoflegends.data

import java.io.Serializable

data class PlayerData(val id: String, val puuid: String, val name: String? = null,
                      val profileIconId: Int, val summonerLevel: Long,
                      val matchList: List<String>, val leagueData: Array<LeagueData?>? = null) : Serializable