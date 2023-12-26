package cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.MatchData
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class PlayerMatchHistoryViewHolder(view: View, var match: MatchData? = null) : ViewHolder(view) {

    val queueType by lazy { view.findViewById<MaterialTextView>(R.id.player_match_history_cell_queue_type) }
    val winLose by lazy { view.findViewById<MaterialTextView>(R.id.player_match_history_cell_win_lose) }
    val gameDuration by lazy { view.findViewById<MaterialTextView>(R.id.player_match_history_cell_game_duration) }
    val timeAgo by lazy { view.findViewById<MaterialTextView>(R.id.player_match_history_cell_time_ago) }
    val championPhoto by lazy { view.findViewById<ShapeableImageView>(R.id.player_match_history_cell_champion_photo) }
    val summoner1 by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_summoner_id_1) }
    val summoner2 by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_summoner_id_2) }
    val perk by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_perk) }
    val style by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_style) }
    val item0 by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_item0) }
    val item1 by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_item1) }
    val item2 by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_item2) }
    val item3 by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_item3) }
    val item4 by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_item4) }
    val item5 by lazy { view.findViewById<ShapeableImageView>(R.id.player_champions_cell_item5) }

    fun SetupWithPlayerMatch(match: MatchData){

        when(match.queueId){
            420 -> queueType.text = ContextCompat.getString(MyApp.get().currentActivity, R.string.ranked_solo_mode)
            440 -> queueType.text = ContextCompat.getString(MyApp.get().currentActivity, R.string.ranked_flex_mode)
            450 -> queueType.text = ContextCompat.getString(MyApp.get().currentActivity, R.string.aram_mode)
            400 -> queueType.text = ContextCompat.getString(MyApp.get().currentActivity, R.string.draft_mode)
            430 -> queueType.text = ContextCompat.getString(MyApp.get().currentActivity, R.string.blind_mode)
        }

        when(match.win){
            true ->{
                winLose.text = ContextCompat.getString(MyApp.get().currentActivity, R.string.victory)
                winLose.setTextColor(ContextCompat.getColor(MyApp.get().currentActivity, R.color.green))
            }
            false ->{
                winLose.text = ContextCompat.getString(MyApp.get().currentActivity, R.string.defeat)
                winLose.setTextColor(ContextCompat.getColor(MyApp.get().currentActivity, R.color.red))
            }
        }

        var minutes = match.gameDuration / 60
        val seconds = match.gameDuration % 60

        gameDuration.text = "${minutes}m ${seconds}s"


        MyFirebase.storage.LoadImage("/champion/${match.championName}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(championPhoto, uri)
            }



        MyFirebase.storage.LoadImage("/spell/${SummonerIdToSummonerSpellName(match.summoner1Id)}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(summoner1, uri)
            }

        MyFirebase.storage.LoadImage("/spell/${SummonerIdToSummonerSpellName(match.summoner2Id)}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(summoner2, uri)
            }

        MyFirebase.storage.LoadImage("/runes/primaryRunes/${StyleIdToStyleName(match.style[0])}/${PerkIdToPerkName(match.perk)}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(perk, uri)
            }


        MyFirebase.storage.LoadImage("/runes/secondaryRunes/${StyleIdToStyleName(match.style[1])}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(style, uri)
            }

        MyFirebase.storage.LoadImage("/item/${match.items[0]}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(item0, uri)
            }

        MyFirebase.storage.LoadImage("/item/${match.items[1]}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(item1, uri)
            }

        MyFirebase.storage.LoadImage("/item/${match.items[2]}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(item2, uri)
            }

        MyFirebase.storage.LoadImage("/item/${match.items[3]}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(item3, uri)
            }

        MyFirebase.storage.LoadImage("/item/${match.items[4]}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(item4, uri)
            }

        MyFirebase.storage.LoadImage("/item/${match.items[5]}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(item5, uri)
            }


        this.match = match

        val timeAgoInSeconds = (System.currentTimeMillis() - match.gameEndTimestamp) / 1000

        var time = timeAgoInSeconds / 31556952

        if (time >= 1)
        {
            timeAgo.text = "${time} years"
            return
        }

        time = timeAgoInSeconds / 2629746

        if (time >= 1)
        {
            timeAgo.text = "${time} months"
            return
        }

        time = timeAgoInSeconds / 86400

        if (time >= 1)
        {
            timeAgo.text = "${time} days"
            return
        }

        time = timeAgoInSeconds / 3600

        if (time >= 1)
        {
            timeAgo.text = "${time} hours"
            return
        }

        time = timeAgoInSeconds / 60

        if (time >= 1)
        {
            timeAgo.text = "${time} minutes"
            return
        }

        timeAgo.text = "${time/60} seconds"
    }

    private fun SummonerIdToSummonerSpellName(summonerId: Int): String{

        var spellName= "Summoner"

        when(summonerId){
            21 -> spellName += "Barrier"
            1 -> spellName += "Boost"
            14 -> spellName += "Dot"
            3 -> spellName += "Exhaust"
            4 -> spellName += "Flash"
            6 -> spellName += "Haste"
            7 -> spellName += "Heal"
            13 -> spellName += "Mana"
            11 -> spellName += "Smite"
            32 -> spellName += "Snowball"
            12 -> spellName += "Teleport"
        }

        return spellName
    }

    private fun StyleIdToStyleName(styleId: Int): String{

        lateinit var styleName: String

        when(styleId){
            8100 -> styleName = "Domination"
            8300 -> styleName = "Inspiration"
            8000 -> styleName = "Precision"
            8400 -> styleName = "Resolve"
            8200 -> styleName = "Sorcery"
        }

        return styleName
    }

    private fun PerkIdToPerkName(perkId: Int): String{

        lateinit var perkName: String

        when(perkId){
            8112 -> perkName = "Electrocute"
            8124 -> perkName = "Predator"
            8128 -> perkName = "DarkHarvest"
            9923 -> perkName = "HailOfBlades"
            8351 -> perkName = "GlacialAugment"
            8360 -> perkName = "UnsealedSpellbook"
            8369 -> perkName = "FirstStrike"
            8005 -> perkName = "PressTheAttack"
            8008 -> perkName = "LethalTempo"
            8021 -> perkName = "FleetFootwork"
            8010 -> perkName = "Conqueror"
            8437 -> perkName = "GraspOfTheUndying"
            8439 -> perkName = "Aftershock"
            8465 -> perkName = "Guardian"
            8214 -> perkName = "SummonAery"
            8229 -> perkName = "ArcaneComet"
            8230 -> perkName = "PhaseRush"
        }

        return perkName
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