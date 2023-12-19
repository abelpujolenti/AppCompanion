package cdi.interfacedesign.lolrankedtracker.leagueoflegends.data

data class PlayerData(val id: String? = null, val puuid: String? = null, val name: String? = null,
                      val profileIconId: Int? = null, val summonerLevel: Long? = null,
                      val matchList: List<String>? = null,
                      val leagueData: Array<LeagueData?>? = null)