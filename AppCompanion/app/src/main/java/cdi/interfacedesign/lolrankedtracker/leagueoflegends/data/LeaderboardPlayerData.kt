package cdi.interfacedesign.lolrankedtracker.leagueoflegends.data

import java.io.Serializable

data class LeaderboardPlayerData(val summonerName: String, val leaguePoints: Int,
                                    val wins: Int, val losses: Int) : Serializable