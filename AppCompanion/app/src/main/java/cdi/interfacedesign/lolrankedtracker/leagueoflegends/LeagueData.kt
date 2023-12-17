package cdi.interfacedesign.lolrankedtracker.leagueoflegends

data class LeagueData(val tier: String, val rank: String, val leaguePoints: Int, val wins: Int,
                      val losses: Int)
