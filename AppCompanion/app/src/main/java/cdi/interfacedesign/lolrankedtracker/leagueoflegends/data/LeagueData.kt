package cdi.interfacedesign.lolrankedtracker.leagueoflegends.data

data class LeagueData(val queueType:String, val tier: String? = null, val rank: String? = null,
                      val leaguePoints: Int, val wins: Int, val losses: Int)
