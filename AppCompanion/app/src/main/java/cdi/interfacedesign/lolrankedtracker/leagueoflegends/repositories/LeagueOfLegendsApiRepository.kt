package cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories

import cdi.interfacedesign.lolrankedtracker.leagueoflegends.LeagueData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.PlayerData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp
import retrofit2.http.GET
import retrofit2.http.Query

class LeagueOfLegendsApiRepository : LeagueOfLegendsRepository {

    companion object{
        const val BASE_URL = "https://euw1.api.riotgames.com/lol/"
        const val API_KEY = "RGAPI-fca3bae3-c616-4228-bf6c-768f4ecfc22f"

        val Timestamp : String get() = Timestamp(System.currentTimeMillis()).time.toString()

        val Hash : String get(){
            val input : String = "$Timestamp$API_KEY"
            val messageDigest = MessageDigest.getInstance("MD5")
            return BigInteger(1, messageDigest.digest(input.toByteArray()))
                .toString(16).padStart(32, '0')
        }

        val ApiService : RetrofitLeagueOfLegendsApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitLeagueOfLegendsApiService::class.java)
        }
    }

    interface RetrofitLeagueOfLegendsApiService {
        @GET("summoner/v4/summoners/by-name/")
        suspend fun GetProfile(
            @Query("ts") timestamp: String = Timestamp,
            @Query("apikey") apikey: String = API_KEY,
            @Query("hash") hash: String = Hash,
            @Query("summonerName") summonerName: String
        ) : Response<ProfileResponse>

        @GET("league/v4/entries/by-summoner/")
        suspend fun GetLeague(
            @Query("ts") timestamp: String = Timestamp,
            @Query("apikey") apikey: String = API_KEY,
            @Query("hash") hash: String = Hash,
            @Query("encryptedSummonerId") encryptedSummonerId: String
        ) : Response<LeagueResponse>

        @GET("match/v5/matches/by-puuid/")
        suspend fun GetMatchList(
            @Query("ts") timestamp: String = Timestamp,
            @Query("apikey") apikey: String = API_KEY,
            @Query("hash") hash: String = Hash,
            @Query("puuid") puuid: String,
            @Query("start") start: Int,
            @Query("count") count: Int,
        ) : Response<MatchListResponse>

        @GET("match/v5/matches/")
        suspend fun GetMatch(
            @Query("ts") timestamp: String = Timestamp,
            @Query("apikey") apikey: String = API_KEY,
            @Query("hash") hash: String = Hash,
            @Query("matchId") matchId: String
        ) : Response<MatchResponse>

        @GET("league-exp/v4/entries/")
        suspend fun GetLeaderboard(
            @Query("ts") timestamp: String = Timestamp,
            @Query("apikey") apikey: String = API_KEY,
            @Query("hash") hash: String = Hash,
            @Query("queue") queue: String,
            @Query("tier") tier: String,
            @Query("division") division: String,
            @Query("page") page: Int,
        ) : Response<LeaderboardResponse>
    }

    override suspend fun GetProfile(summonerName: String): PlayerData?{

        val responseProfile = ApiService.GetProfile(summonerName = summonerName)

        if (!responseProfile.isSuccessful){

            return null;
        }

        val puuid: String = responseProfile.body()?.puuid?: run{
            return null;
        }

        val responseMatchesList = ApiService.GetMatchList(puuid = puuid, start = 0, count = 20)

        if (responseMatchesList.isSuccessful){

            return null;
        }

        val id: String = responseProfile.body()?.id?: run{
            return null;
        }

        val responseLeague = ApiService.GetLeague(encryptedSummonerId = id)

        if (!responseLeague.isSuccessful){

            return null;
        }

        val name: String = responseProfile.body()?.name?: run{
            return null;
        }

        val profileIconId: Int = responseProfile.body()?.profileIconId?: run{
            return null;
        }

        val summonerLevel: Long = responseProfile.body()?.summonerLevel?:run{
            return null;
        }

        val matchList = responseMatchesList.body()?.matchList?: run{
            return null;
        }

        val leagueData = responseLeague.body()?.leaguesData?: run {
            return null;
        }

        val leaguesData = FillLeagueData(leagueData)

        return PlayerData(id, puuid, name, profileIconId, summonerLevel, matchList, leaguesData[0],
            leaguesData[1])
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