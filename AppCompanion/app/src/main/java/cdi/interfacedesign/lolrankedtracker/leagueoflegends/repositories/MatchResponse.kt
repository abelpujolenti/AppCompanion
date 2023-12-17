package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

data class MatchResponse(val info: MutableList<MatchResponseInfo>)

data class MatchResponseInfo(val gameDuration: Long, val gameEndTimestamp: Long,
                             val gameStartTimestamp: Long,
                             val participants: MutableList<MatchResponseParticipant>, val queueId: Int)

data class MatchResponseParticipant(val championName: String, val item0: Int, val intem1: Int,
                                    val item2: Int, val item3: Int, val item4: Int, val item5:Int,
                                    val perks: MatchResponsePerks, val summoner1Id: Int,
                                    val summoner2Id: Int, val win: Boolean)

data class MatchResponsePerks(val styles: MutableList<MatchResponseStyles>)

data class MatchResponseStyles(val selections: MutableList<MatchResponseSelections>, val style: MatchResponseStyle)

data class MatchResponseSelections(val perk: MatchResponsePerk)

data class MatchResponsePerk(val perk: Int)

data class MatchResponseStyle(val style: Int)
