package cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class TrackedPlayerViewHolder(view: View, var player: PlayerData? = null) : ViewHolder(view) {

    val playerProfileIcon by lazy { view.findViewById<ShapeableImageView>(R.id.tracker_player_cell_profile_icon) }
    val playerSummonerName by lazy { view.findViewById<TextView>(R.id.tracker_player_cell_summoner_name) }
    val playerMatchesList by lazy { view.findViewById<TextView>(R.id.matches) }
    val playerSoloTierIcon by lazy { view.findViewById<ShapeableImageView>(R.id.tracker_player_cell_tier_icon) }
    val playerSoloTierRank by lazy { view.findViewById<TextView>(R.id.tracker_player_cell_tier_rank) }
    val playerSoloLeaguePoints by lazy { view.findViewById<TextView>(R.id.tracker_player_cell_league_points) }
    val discardButton by lazy { view.findViewById<ImageButton>(R.id.discard_button) }

    fun SetupWithTrackedPlayer(player: PlayerData){

        MyFirebase.storage.LoadImage("/profileIcon/${player.profileIconId}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(playerProfileIcon, uri)
            }

        playerSummonerName.text = player.name

        player.leagueData?.get(0)?.let { leagueData ->
            leagueData.tier?.let { tier ->

                MyFirebase.storage.LoadImage("/tier/${tier}.png").downloadUrl
                    .addOnSuccessListener { uri ->
                        LoadIcon(playerSoloTierIcon, uri)
                    }

                leagueData.rank?.let { rank ->
                    playerSoloTierRank.text = "$tier $rank"/*getString(R.string.tier_rank, tier, rank)*/
                }
            }

            playerSoloLeaguePoints.text = leagueData.leaguePoints.toString() + " LP"
        } ?: run{

            /*val width = LinearLayout.LayoutParams.WRAP_CONTENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            var layoutParams = LinearLayout.LayoutParams(width, height, 0.9f)
            playerSoloTierIcon.layoutParams = layoutParams

            layoutParams = LinearLayout.LayoutParams(width, height, 0.1f)
            playerSoloTierRank.layoutParams = layoutParams

            layoutParams = LinearLayout.LayoutParams(width, height, 0f)
            playerSoloLeaguePoints.layoutParams = layoutParams*/

            MyFirebase.storage.LoadImage("/tier/UNRANKED.png").downloadUrl
                .addOnSuccessListener { uri ->
                    LoadIcon(playerSoloTierIcon, uri)
                }

            playerSoloTierRank.text = "UNRANKED"
            playerSoloLeaguePoints.text = ""
        }

        discardButton.setOnClickListener {
            //TODO Discard
        }

        this.player = player
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