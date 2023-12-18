package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import android.util.Log
import cdi.interfacedesign.lolrankedtracker.interceptors.HeaderInterceptor
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.LeagueData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.PlayerData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class LeagueOfLegendsApiRepository : LeagueOfLegendsRepository {

    companion object{
        const val API_KEY = "RGAPI-fca3bae3-c616-4228-bf6c-768f4ecfc22f"
        const val BASE_URL_EUW1 = "euw1.api.riotgames.com/lol/"
        const val BASE_URL_EUROPE = "europe.api.riotgames.com/lol/"

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
                .baseUrl("https://$BASE_URL_EUROPE")
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
        ) : Response<Set<LeagueResponse>>

        @GET("match/v5/matches/by-puuid/{puuid}/ids")
        suspend fun GetMatchList(
            @Path("puuid") puuid: String,
            @Query("start") start: Int,
            @Query("count") count: Int,
        ) : //Call<MatchListResponse>
        Response<MatchListResponse>

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
        ) : Response<Set<LeaderboardResponse>>
    }

    override suspend fun GetProfile(summonerName: String): PlayerData?{

        val responseProfile = ApiPlatformService.GetProfile(summonerName = summonerName)

        if (!responseProfile.isSuccessful){

            return null;
        }

        val puuid: String = responseProfile.body()?.puuid?: run{
            return null;
        }

        Log.e("RIOTPuuid", puuid)

        val responseMatchesList = ApiRegionalService.GetMatchList(puuid = puuid, start = 0, count = 20)

        /*val responseMatchesList = ApiRegionalService.GetMatchList(puuid = puuid, start = 0, count = 20).enqueue(object : Callback<MatchListResponse>{
            override fun onFailure(call: Call<MatchListResponse>, t: Throwable) {
                Log.e("Error", "error", t)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<MatchListResponse>,
                response: Response<MatchListResponse>
            ) {
                Log.e("Error", "error")
                println(response)
            }
        })*/

        if (responseMatchesList.isSuccessful){

            return null;
        }

        val id: String = responseProfile.body()?.id?: run{
            return null;
        }

        Log.e("RIOTId", id)

        val responseLeague = ApiPlatformService.GetLeague(encryptedSummonerId = id)

        if (!responseLeague.isSuccessful){

            return null;
        }

        val name: String = responseProfile.body()?.name?: run{
            return null;
        }

        Log.e("RIOTname", name)

        val profileIconId: Int = responseProfile.body()?.profileIconId?: run{
            return null;
        }

        val summonerLevel: Long = responseProfile.body()?.summonerLevel?:run{
            return null;
        }

        val matchList = responseMatchesList.body()?.matchList?: run{
            return null;
        }

        /*val leagueData = responseLeague.body()?.leaguesData?: run {
            return null;
        }

        val leaguesData = FillLeagueData(leagueData)*/

        return PlayerData(id, puuid, name, profileIconId, summonerLevel/*, matchList, leaguesData[0],
            leaguesData[1]*/)
    }

    fun FillLeagueData(leagueData: MutableList<LeagueResponseData>): MutableList<LeagueData>{

        val leaguesData = mutableListOf<LeagueData>()

        for (i in leagueData.indices){
            val leagueTier = leagueData[i].tier
            val leagueRank = leagueData[i].rank
            val leagueLeaguePoints = leagueData[i].leaguePoints
            val leagueWins = leagueData[i].wins
            val leagueLosses = leagueData[i].losses

            val leagueData = LeagueData(leagueTier, leagueRank,
                leagueLeaguePoints, leagueWins, leagueLosses)

            leaguesData.add(leagueData)
        }

        return leaguesData;
    }

    override suspend fun GetLeague() {
        TODO("Not yet implemented")
    }

    override suspend fun GetMatchList() {
        TODO("Not yet implemented")
    }

    override suspend fun GetMatch() {
        TODO("Not yet implemented")
    }

    override suspend fun GetLeaderboard() {
        TODO("Not yet implemented")
    }

}