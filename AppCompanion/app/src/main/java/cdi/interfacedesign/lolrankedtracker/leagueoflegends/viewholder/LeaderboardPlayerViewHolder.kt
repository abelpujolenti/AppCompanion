package cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.PlayerProfileAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.LeaderboardPlayerData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.TrackedPlayerProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsRepository
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class LeaderboardPlayerViewHolder(view: View, var leaderboardPlayerData: LeaderboardPlayerData? = null) : ViewHolder(view) {

    val leaderboardPlayerProfileIcon by lazy { view.findViewById<ShapeableImageView>(R.id.leaderboard_player_cell_profile_icon) }
    val leaderboardPlayerSummonerName by lazy { view.findViewById<TextView>(R.id.leaderboard_player_cell_summoner_name) }
    val leaderboardPlayerSoloLeaguePoints by lazy { view.findViewById<TextView>(R.id.tracker_player_cell_league_points) }
    val leaderboardPlayerWinsLosses by lazy { view.findViewById<TextView>(R.id.leaderboard_player_wins_losses) }
    val leaderboardPlayerWinRate by lazy { view.findViewById<TextView>(R.id.leaderboard_player_win_rate) }


    fun SetupWithLeaderboardPlayers(leaderboardPlayerData: LeaderboardPlayerData,
                                    provider: TrackedPlayerProvider){

        CoroutineScope(Dispatchers.IO).launch {

            val profileIconId = provider.GetPlayer(leaderboardPlayerData.summonerName)?.profileIconId

            CoroutineScope(Dispatchers.Main).launch {
                MyFirebase.storage.LoadImage("/profileIcon/${profileIconId}.png").downloadUrl
                    .addOnSuccessListener { uri ->
                        LoadIcon(leaderboardPlayerProfileIcon, uri)
                    }
            }
        }

        leaderboardPlayerSummonerName.text = leaderboardPlayerData.summonerName
        leaderboardPlayerSoloLeaguePoints.text = "${leaderboardPlayerData.leaguePoints} LP"
        leaderboardPlayerWinsLosses.text = "${leaderboardPlayerData.wins}W ${leaderboardPlayerData.losses}L"

        val totalMatches = leaderboardPlayerData.wins + leaderboardPlayerData.losses

        leaderboardPlayerWinRate.text = "${(leaderboardPlayerData.wins * 100) / totalMatches} WR"

    }

    private fun LoadIcon(image: ShapeableImageView, uri: Uri){

        CoroutineScope(Dispatchers.IO).launch {
            val stream = withContext(Dispatchers.IO){
                URL(uri.toString()).openStream()
            }
            val profileIconBitMap = BitmapFactory.decodeStream(stream)

            CoroutineScope(Dispatchers.Main).launch {
                image.setImageBitmap(profileIconBitMap)
                image.visibility = View.VISIBLE
            }
        }
    }
}