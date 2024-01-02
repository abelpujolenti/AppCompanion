package cdi.interfacedesign.lolrankedtracker.fragments.screens

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.LeagueData
//import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.PlayerProfileAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ProfileScreen : Fragment() {

    lateinit var fragmentView: View

    val rankedTierSoloIcon by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_profile_ranked_cell_tier_icon_solo) }
    val rankedTierRankSolo by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_ranked_cell_tier_rank_solo) }
    val rankedLeaguePointsLeaguePointsSolo by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_ranked_cell_league_points_solo) }
    val rankedWinsLossesSolo by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_ranked_cell_wins_losses_solo) }
    val rankedWinRateSolo by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_ranked_cell_win_rate_solo) }

    val rankedTierFlexIcon by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_profile_ranked_cell_tier_icon_flex) }
    val rankedTierRankFlex by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_ranked_cell_tier_rank_flex) }
    val rankedLeaguePointsLeaguePointsFlex by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_ranked_cell_league_points_flex) }
    val rankedWinsLossesFlex by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_ranked_cell_wins_losses_flex) }
    val rankedWinRateFlex by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_ranked_cell_win_rate_flex) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.screen_player_profile, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val player = MyApp.get().player

        FillLeagueData(player.leagueData?.get(0), rankedTierSoloIcon, rankedTierRankSolo,
            rankedLeaguePointsLeaguePointsSolo, rankedWinsLossesSolo, rankedWinRateSolo)

        FillLeagueData(player.leagueData?.get(1), rankedTierFlexIcon, rankedTierRankFlex,
            rankedLeaguePointsLeaguePointsFlex, rankedWinsLossesFlex, rankedWinRateFlex)
    }

    private fun FillLeagueData(leagueData: LeagueData?, icon: ShapeableImageView, tierRank: MaterialTextView,
                               leaguePoints: MaterialTextView, winsLosses: MaterialTextView, winRate: MaterialTextView){

        leagueData?.let { leagueData ->
            leagueData.tier?.let { tier ->

                MyFirebase.storage.LoadImage("/tier/${tier}.png").downloadUrl
                    .addOnSuccessListener { uri ->
                        LoadIcon(icon, uri)
                    }

                val tierTitle = tier.substring(0, 1).uppercase() + tier.substring(1).lowercase()

                tierRank.text = tierTitle

                if (tier != "CHALLENGER" && tier != "GRANDMASTER" && tier != "MASTER"){
                    leagueData.rank?.let { rank ->
                        tierRank.text = "${tierRank.text} $rank"/*getString(R.string.tier_rank, tier, rank)*/
                    }
                }
            }

            leaguePoints.text = leagueData.leaguePoints.toString() + " LP"
            winsLosses.text = "${leagueData.wins}W ${leagueData.losses}L"

            val totalMatches = leagueData.wins + leagueData.losses
            val rate = (leagueData.wins * 100) / totalMatches

            winRate.text = "${rate}% Win Rate"

        } ?: run{

            MyFirebase.storage.LoadImage("/tier/UNRANKED.png").downloadUrl
                .addOnSuccessListener { uri ->
                    LoadIcon(icon, uri)
                }

            tierRank.text = "Unranked"

            leaguePoints.visibility = View.GONE
            winsLosses.visibility = View.GONE
            winRate.visibility = View.GONE
        }
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