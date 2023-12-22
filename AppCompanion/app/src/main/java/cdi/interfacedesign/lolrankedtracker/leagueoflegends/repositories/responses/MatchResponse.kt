package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses

data class MatchResponse(val info: MatchResponseInfo)

data class MatchResponseInfo(val gameDuration: Long, val gameEndTimestamp: Long,
                             val gameStartTimestamp: Long,
                             val participants: List<MatchResponseParticipant>, val queueId: Int)

data class MatchResponseParticipant(val championName: String, val item0: Int, val item1: Int,
                                    val item2: Int, val item3: Int, val item4: Int, val item5: Int,
                                    val perks: MatchResponsePerks, val puuid: String,
                                    val summoner1Id: Int, val summoner2Id: Int, val win: Boolean)

data class MatchResponsePerks(val styles: List<MatchResponseStyles>)

data class MatchResponseStyles(val selections: List<MatchResponseSelections>, val style: Int)

data class MatchResponseSelections(val perk: Int)
