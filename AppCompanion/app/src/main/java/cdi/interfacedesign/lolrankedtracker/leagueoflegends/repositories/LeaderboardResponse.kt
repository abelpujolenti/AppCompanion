package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

data class LeaderboardResponse(val summonerName: String, val tier: String, val rank: String,
                               val leaguePoints: Int, val wins: Int, val losses: Int)
