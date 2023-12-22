package cdi.interfacedesign.lolrankedtracker.utils

import android.content.Context
import android.content.SharedPreferences
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesManager {

    private const val MyPreferencesName = "MySharedPreferencesStorage"

    private val shared: SharedPreferences by lazy { MyApp.get().getSharedPreferences(
        MyPreferencesName, Context.MODE_PRIVATE) }

    private val editor: SharedPreferences.Editor by lazy { shared.edit() }
    private const val PLAYER_KEY = "Player"
    private const val MATCH_KEY = "Match"
    private const val LEAGUE_KEY = "League"


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