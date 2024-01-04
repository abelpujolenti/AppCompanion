package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppMainMenu
import cdi.interfacedesign.lolrankedtracker.interceptors.HeaderInterceptor
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.LeaderboardPlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.LeagueData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.MatchData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.LeaderboardResponse
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.LeagueResponse
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.MatchResponse
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.MatchResponseParticipant
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.responses.ProfileResponse
import cdi.interfacedesign.lolrankedtracker.utils.SharedPreferencesManager
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class LeagueOfLegendsApiRepository : LeagueOfLegendsRepository {

    val RANKED_SOLO = "RANKED_SOLO_5x5"
    val RANKED_FLEX = "RANKED_FLEX_SR"

    companion object{
        const val API_KEY = "RGAPI-fca3bae3-c616-4228-bf6c-768f4ecfc22f"
        val BASE_URL_REGION = "https://${SharedPreferencesManager.regionSelected}.api.riotgames.com/lol/"
        val BASE_URL_PLATFORM = "https://${SharedPreferencesManager.platformSelected}.api.riotgames.com/lol/"

        private val client: OkHttpClient = OkHttpClient.Builder().apply {
            interceptors().add(HeaderInterceptor(API_KEY))
        }.build()

        val ApiRegionalService : RetrofitLeagueOfLegendsApiService by lazy {
            Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL_REGION)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitLeagueOfLegendsApiService::class.java)
        }

        val ApiPlatformService : RetrofitLeagueOfLegendsApiService by lazy {
            Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL_PLATFORM)
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

        val responseProfile = ApiPlatformService.GetProfile(summonerName)

        if (!responseProfile.isSuccessful){
            if (responseProfile.code() != 404){
                MyFirebase.crashlytics.logSimpleError("Api Response Error"){
                    key("Parameter Summoner Name", summonerName)
                    key("Code", responseProfile.code())
                    key("Response", SharedPreferencesManager.PLAYER_KEY)
                }
                Snackbar.make(AppMainMenu.get().fragmentView, MyApp.get().currentActivity.getString(R.string.error_searching_player), Snackbar.LENGTH_SHORT)
                    .show()
                return null
            }
            Snackbar.make(AppMainMenu.get().fragmentView, MyApp.get().currentActivity.getString(R.string.user_not_found, summonerName), Snackbar.LENGTH_SHORT)
                .show()
            return null
        }

        val response = responseProfile.body()?: run {
            return null
        }

        val id: String = response.id

        val puuid: String = response.puuid

        val name: String = response.name

        val profileIconId: Int = response.profileIconId

        val summonerLevel: Long = response.summonerLevel

        val matchesList = GetMatchesList(puuid, 0, 20)

        if (matchesList.isEmpty()){
            return null
        }

        val leagueData = GetLeague(id);

        if (leagueData.isEmpty()){
            return PlayerData(id, puuid, name, profileIconId, summonerLevel, matchesList)
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
            }
            else if (leagueQueueType == RANKED_FLEX)
            {
                leaguesData[1] = leagueData
            }
        }

        return leaguesData;
    }

    override suspend fun GetLeague(summonerId: String): List<LeagueResponse> {

        val responseLeague = ApiPlatformService.GetLeague(encryptedSummonerId = summonerId)

        if (!responseLeague.isSuccessful){
            MyFirebase.crashlytics.logSimpleError("Api Error"){
                key("Parameter Summoner Id", summonerId)
                key("Code", responseLeague.code())
                key("Response", SharedPreferencesManager.LEAGUE_KEY)
            }
            Snackbar.make(AppMainMenu.get().fragmentView, MyApp.get().currentActivity.getString(R.string.error_fetching_league_data), Snackbar.LENGTH_SHORT)
                .show()
            return emptyList<LeagueResponse>();
        }

        val leagueData = responseLeague.body()?: run {
            return emptyList<LeagueResponse>();
        }

        return leagueData
    }

    override suspend fun GetMatchesList(puuid: String, start: Int, count: Int): List<String> {

        val responseMatchesList = ApiRegionalService.GetMatchList(puuid, start, count)

        if (!responseMatchesList.isSuccessful){
            MyFirebase.crashlytics.logSimpleError("Api Error"){
                key("Parameter Count", count)
                key("Parameter Start", start)
                key("Parameter Puuid", puuid)
                key("Code", responseMatchesList.code())
                key("Response", SharedPreferencesManager.MATCHES_LIST_KEY)
            }
            Snackbar.make(AppMainMenu.get().fragmentView, MyApp.get().currentActivity.getString(R.string.error_fetching_matches_list), Snackbar.LENGTH_SHORT)
                .show()
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
            MyFirebase.crashlytics.logSimpleError("Api Error"){
                key("Parameter Match Id", matchId)
                key("Parameter Puuid", puuid)
                key("Code", responseMatch.code())
                key("Response", SharedPreferencesManager.MATCH_KEY)
            }
            Snackbar.make(AppMainMenu.get().fragmentView, MyApp.get().currentActivity.getString(R.string.error_fetching_match_data), Snackbar.LENGTH_SHORT)
                .show()
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

        val styles: List<Int> = listOf(participant.perks.styles[0].style, participant.perks.styles[1].style)

        val items: List<Int> = listOf(participant.item0, participant.item1, participant.item2,
            participant.item3, participant.item4, participant.item5)

        return MatchData(queueId, win, gameDuration, gameStartTimestamp, gameEndTimestamp, championName,
            summoner1Id, summoner2Id, perk, styles, items)

    }

    override suspend fun GetLeaderboard(queue: String, tier: String, rank: String, page: Int)
        : List<LeaderboardPlayerData> {

        val responseLeaderboard = ApiPlatformService.GetLeaderboard(queue, tier, rank, page)

        if (!responseLeaderboard.isSuccessful){
            if (responseLeaderboard.code() != 404){
                MyFirebase.crashlytics.logSimpleError("Api Error"){
                    key("Parameter Patg", page)
                    key("Parameter Rank", rank)
                    key("Parameter Tier", tier)
                    key("Parameter Queue", queue)
                    key("Code", responseLeaderboard.code())
                    key("Response", SharedPreferencesManager.MATCH_KEY)
                }
            }
            Snackbar.make(AppMainMenu.get().fragmentView, MyApp.get().currentActivity.getString(R.string.error_fetching_league_data), Snackbar.LENGTH_SHORT)
                .show()
            return emptyList<LeaderboardPlayerData>();
        }

        val leaderboardResponseList = responseLeaderboard.body()?: run{
            return emptyList<LeaderboardPlayerData>();
        }

        val leaderboardPlayersList: MutableList<LeaderboardPlayerData> = mutableListOf()

        var summonerName: String
        var leaguePoints: Int
        var wins: Int
        var losses: Int
        var leaderboardPlayer: LeaderboardPlayerData

        for (leaderboardResponse in leaderboardResponseList){

            summonerName = leaderboardResponse.summonerName
            leaguePoints = leaderboardResponse.leaguePoints
            wins = leaderboardResponse.wins
            losses = leaderboardResponse.losses

            leaderboardPlayer = LeaderboardPlayerData(summonerName, leaguePoints, wins, losses)
            leaderboardPlayersList.add(leaderboardPlayer)
        }

        return leaderboardPlayersList
    }

}