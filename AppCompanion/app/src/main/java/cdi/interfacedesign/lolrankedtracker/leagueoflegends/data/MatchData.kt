package cdi.interfacedesign.lolrankedtracker.leagueoflegends.data

data class MatchData(val queueId: Int, val win: Boolean, val gameDuration: Long,
                     val gameStartTimestamp: Long, val gameEndTimestamp: Long, val championName: String,
                     val summoner1Id: Int, val summoner2Id: Int, val perk: Int, val style: Int,
                     val item0: Int, val item1: Int, val item2: Int, val item3: Int, val item4: Int,
                     val item5: Int)
