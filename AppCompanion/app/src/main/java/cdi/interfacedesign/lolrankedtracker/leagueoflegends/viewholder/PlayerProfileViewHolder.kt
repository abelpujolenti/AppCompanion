package cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.widget.ImageView
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

class PlayerProfileViewHolder(view: View, var player: PlayerData) : ViewHolder(view) {

    val profileIcon by lazy { view.findViewById<ShapeableImageView>(R.id.player_profile_profile_icon) }
    val summonerName by lazy { view.findViewById<TextView>(R.id.player_profile_summoner_name) }
    val summonerLevel by lazy { view.findViewById<TextView>(R.id.player_summoner_level) }

    fun SetupWithPlayerProfile(player: PlayerData){

        MyFirebase.storage.LoadImage("/profileIcon/${player.profileIconId}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(profileIcon, uri)
            }

        summonerName.text = player.name

        summonerLevel.text = player.summonerLevel.toString()

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