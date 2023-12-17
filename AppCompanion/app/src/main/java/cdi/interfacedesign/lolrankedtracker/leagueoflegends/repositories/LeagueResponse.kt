package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

data class LeagueResponse(val leaguesData: MutableList<LeagueResponseData>)

data class LeagueResponseData(val queueType: String, val tier: String, val rank: String,
                              val leaguePoints: Int, val wins: Int, val losses: Int)
