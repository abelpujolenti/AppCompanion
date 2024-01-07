package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses

data class LeaderboardResponse(val summonerName: String, val leaguePoints: Int,
                               val wins: Int, val losses: Int)
