package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.interceptors.HeaderInterceptor
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.LeagueData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.MatchData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.LeaderboardResponse
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.LeagueResponse
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.MatchResponse
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.MatchResponseParticipant
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.ProfileResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class LeagueOfLegendsApiRepository : LeagueOfLegendsRepository {

    val RANKED_SOLO = "RANKED_SOLO_5x5"

    companion object{
        const val API_KEY = "RGAPI-fca3bae3-c616-4228-bf6c-768f4ecfc22f"
        const val BASE_URL_EUW1 = "https://euw1.api.riotgames.com/lol/"
        const val BASE_URL_EUROPE = "https://europe.api.riotgames.com/lol/"

        private val client: OkHttpClient = OkHttpClient.Builder().apply {
            interceptors().add(HeaderInterceptor(API_KEY))
        }.build()

        val ApiPlatformService : RetrofitLeagueOfLegendsApiService by lazy {
            Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL_EUW1)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitLeagueOfLegendsApiService::class.java)
        }

        val ApiRegionalService : RetrofitLeagueOfLegendsApiService by lazy {
            Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL_EUROPE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitLeagueOfLegendsApiService::class.java)
        }
    }

    interface RetrofitLeagueOfLegendsApiService {
        @GET("summoner/v4/summoners/by-name/{summonerName}")
        suspend fun GetProfile(
            @Path("summonerName") summonerName: String
        ) : Response<ProfileResponse>

        @GET("league/v4/entries/by-summoner/{encryptedSummonerId}")
        suspend fun GetLeague(
            @Path("encryptedSummonerId") encryptedSummonerId: String
        ) : Response<List<LeagueResponse>>

        @GET("match/v5/matches/by-puuid/{puuid}/ids")
        suspend fun GetMatchList(
            @Path("puuid") puuid: String,
            @Query("start") start: Int,
            @Query("count") count: Int,
        ) : Response<List<String>>

        @GET("match/v5/matches/{matchId}")
        suspend fun GetMatch(
            @Path("matchId") matchId: String
        ) : Response<MatchResponse>

        @GET("league-exp/v4/entries/{queue}/{tier}/{division}")
        suspend fun GetLeaderboard(
            @Path("queue") queue: String,
            @Path("tier") tier: String,
            @Path("division") division: String,
            @Query("page") page: Int,
        ) : Response<List<LeaderboardResponse>>
    }

    override suspend fun GetPlayerProfile(summonerName: String): PlayerData?{

        val responseProfile = ApiPlatformService.GetProfile(summonerName = summonerName)

        if (!responseProfile.isSuccessful){
            return null
        }

        val response = responseProfile.body()?: run {
            return null
        }

        val puuid: String = response.puuid

        val id: String = response.id

        val name: String = response.name

        val profileIconId: Int = response.profileIconId

        val summonerLevel: Long = response.summonerLevel

        val matchesList = GetMatchesList(puuid, 0, 20)

        if (matchesList.isEmpty()){
            return null
        }

        val leagueData = GetLeague(id);

        if (leagueData.isEmpty()){
            return null
        }

        val leaguesData = FillLeagueData(leagueData)

        return PlayerData(id, puuid, name, profileIconId, summonerLevel, matchesList, leaguesData)
    }

    fun FillLeagueData(leagueData: List<LeagueResponse>): Array<LeagueData?>{

        var leaguesData = arrayOfNulls<LeagueData?>(2)

        for (i in leagueData.indices){
            val leagueQueueType = leagueData[i].queueType
            val leagueTier = leagueData[i].tier
            val leagueRank = leagueData[i].rank
            val leagueLeaguePoints = leagueData[i].leaguePoints
            val leagueWins = leagueData[i].wins
            val leagueLosses = leagueData[i].losses

            val leagueData = LeagueData(leagueQueueType, leagueTier, leagueRank,
                leagueLeaguePoints, leagueWins, leagueLosses)

            if (leagueQueueType == RANKED_SOLO)
            {
                leaguesData[0] = leagueData
                continue
            }

            leaguesData[1] = leagueData
        }

        return leaguesData;
    }

    override suspend fun GetLeague(summonerId: String): List<LeagueResponse> {

        val responseLeague = ApiPlatformService.GetLeague(encryptedSummonerId = summonerId)

        if (!responseLeague.isSuccessful){

            return emptyList<LeagueResponse>();
        }

        val leagueData = responseLeague.body()?: run {
            return emptyList<LeagueResponse>();
        }

        return leagueData

    }

    override suspend fun GetMatchesList(puuid: String, start: Int, count: Int): List<String> {

        val responseMatchesList = ApiRegionalService.GetMatchList(puuid = puuid, start = 0, count = 20)

        if (!responseMatchesList.isSuccessful){

            return emptyList<String>();
        }

        val matchList = responseMatchesList.body()?: run{
            return emptyList<String>();
        }

        return matchList
    }

    override suspend fun GetMatch(puuid: String, matchId: String): MatchData? {

        val responseMatch = ApiRegionalService.GetMatch(matchId)

        if (!responseMatch.isSuccessful){
            return null;
        }

        val info = responseMatch.body()?.info?: run{
            return null;
        }

        val participants = info.participants

        lateinit var participant: MatchResponseParticipant

        var match: Boolean = false

        for (auxParticipant in participants){
            if (auxParticipant.puuid == puuid)
            {
                participant = auxParticipant
                match = true;
                break;
            }
        }

        if (!match){
            return null;
        }

        val win = participant.win

        val queueId = info.queueId

        val gameDuration = info.gameDuration

        val gameStartTimestamp = info.gameStartTimestamp

        val gameEndTimestamp = info.gameEndTimestamp

        val championName = participant.championName

        val summoner1Id = participant.summoner1Id

        val summoner2Id = participant.summoner2Id

        val perk = participant.perks.styles[0].selections[0].perk

        val style = participant.perks.styles[1].style

        val item0 = participant.item0

        val item1 = participant.item1

        val item2 = participant.item2

        val item3 = participant.item3

        val item4 = participant.item4

        val item5 = participant.item5

        return MatchData(queueId, win, gameDuration, gameStartTimestamp, gameEndTimestamp, championName,
            summoner1Id, summoner2Id, perk, style, item0, item1, item2, item3, item4, item5)

    }

    override suspend fun GetLeaderboard(offset: Int, limit: Int): MutableList<PlayerData> {
        TODO("Not yet implemented")
    }

}