package cdi.interfacedesign.lolrankedtracker.utils

import android.content.Context
import android.content.SharedPreferences
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.LeaderboardPlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.TrackedPlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesManager {

    private const val MyPreferencesName = "MySharedPreferencesStorage"

    private val shared: SharedPreferences by lazy { MyApp.get().getSharedPreferences(
        MyPreferencesName, Context.MODE_PRIVATE) }

    private val editor: SharedPreferences.Editor by lazy { shared.edit() }
    const val PLAYER_KEY = "Player"
    const val MATCHES_LIST_KEY = "Matches List"
    const val MATCH_KEY = "Match"
    const val LEAGUE_KEY = "League"
    const val TRACKER_KEY = "Tracker"
    const val LEADERBOARD_KEY = "Leaderboard"

    var platformSelected: String = "euw1"
    var regionSelected: String = "europe"
    var queueTypeSelected: String = "RANKED_SOLO_5x5"
    var tierSelected: String = "Challenger"
    var rankSelected: String = "I"

    lateinit var player: PlayerData
    lateinit var trackedPlayerAdapter: TrackedPlayerAdapter
    lateinit var leaderboardPlayerAdapter: LeaderboardPlayerAdapter
    lateinit var previousScreen: String

    public var Players: MutableList<PlayerData>
        get(){
            val jsonString = shared.getString(PLAYER_KEY, "")
            val listType = object: TypeToken<MutableList<PlayerData>?>() {}.type
            val playersList: MutableList<PlayerData>? = Gson().fromJson(jsonString, listType)
            return playersList ?: mutableListOf()
        }
        set(value){
            val jsonString = Gson().toJson(value)
            editor.putString(PLAYER_KEY, jsonString)
            editor.apply()
        }
}