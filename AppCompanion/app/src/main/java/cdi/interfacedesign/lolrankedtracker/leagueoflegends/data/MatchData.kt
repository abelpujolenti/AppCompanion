package cdi.interfacedesign.lolrankedtracker.leagueoflegends.data

import java.io.Serializable

data class MatchData(val queueId: Int, val win: Boolean, val gameDuration: Long,
                     val gameStartTimestamp: Long, val gameEndTimestamp: Long, val championName: String,
                     val summoner1Id: Int, val summoner2Id: Int, val perk: Int, val style: List<Int>,
                     val items: List<Int>) : Serializable
