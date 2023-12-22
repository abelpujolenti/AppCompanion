package cdi.interfacedesign.lolrankedtracker.leagueoflegends.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData

class PlayerViewHolder(view: View, var player: PlayerData? = null) : ViewHolder(view) {

    val playerId by lazy { view.findViewById<TextView>(R.id.id) }
    val playerPuuid by lazy { view.findViewById<TextView>(R.id.puuid) }
    val playerProfileIconId by lazy { view.findViewById<TextView>(R.id.profileIconId) }
    val playerSummonerLevel by lazy { view.findViewById<TextView>(R.id.summonerLevel) }
    val playerMatchesList by lazy { view.findViewById<TextView>(R.id.matches) }
    val playerSoloTier by lazy { view.findViewById<TextView>(R.id.soloTier) }
    val playerSoloRank by lazy { view.findViewById<TextView>(R.id.soloRank) }
    val playerSolo by lazy { view.findViewById<TextView>(R.id.solo) }
    val playerFlexTier by lazy { view.findViewById<TextView>(R.id.flexTier) }
    val playerFlexRank by lazy { view.findViewById<TextView>(R.id.flexRank) }
    val playerFlex by lazy { view.findViewById<TextView>(R.id.flex) }

    fun SetupWithPlayer(player: PlayerData){
        playerId.text = player.id
        playerPuuid.text = player.puuid
        playerProfileIconId.text = player.profileIconId.toString()
        playerSummonerLevel.text = player.summonerLevel.toString()

        player.leagueData?.get(0)?.let { leagueData ->

            leagueData.tier?.let { tier ->
                playerSoloTier.text = tier
            }

            leagueData.rank?.let { rank ->
                playerSoloRank.text = rank
            }
            playerSolo.text = leagueData.leaguePoints.toString()
        }

        player.leagueData?.get(1)?.let { leagueData ->

            leagueData.tier?.let { tier ->
                playerFlexTier.text = tier
            }

            leagueData.rank?.let { rank ->
                playerFlexRank.text = rank
            }

            playerFlex.text = leagueData.leaguePoints.toString()
        }

        this.player = player
    }


}