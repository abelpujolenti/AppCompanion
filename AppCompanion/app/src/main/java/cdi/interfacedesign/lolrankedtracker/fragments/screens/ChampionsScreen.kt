package cdi.interfacedesign.lolrankedtracker.fragments.screens

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.MatchData
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.provider.PlayerMatchHistoryProvider
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.SortedMap

class ChampionsScreen : Fragment() {

    val MAX_CELLS = 4

    lateinit var fragmentView: View

    val mostPlayedChampion0Photo by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_champions_cell_most_played_champion_image_0) }
    val mostPlayedChampion0Text by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_champions_cell_most_played_champion_value_0)}
    val mostPlayedChampion1Photo by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_champions_cell_most_played_champion_image_1) }
    val mostPlayedChampion1Text by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_champions_cell_most_played_champion_value_1)}
    val mostPlayedChampion2Photo by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_champions_cell_most_played_champion_image_2) }
    val mostPlayedChampion2Text by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_champions_cell_most_played_champion_value_2)}
    val mostPlayedChampion3Photo by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_champions_cell_most_played_champion_image_3) }
    val mostPlayedChampion3Text by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_champions_cell_most_played_champion_value_3)}

    val highestWinRateChampion0Photo by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_champions_cell_highest_win_rate_champion_image_0) }
    val highestWinRateChampion0Text by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_champions_cell_highest_win_rate_champion_value_0) }
    val highestWinRateChampion1Photo by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_champions_cell_highest_win_rate_champion_image_1) }
    val highestWinRateChampion1Text by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_champions_cell_highest_win_rate_champion_value_1) }
    val highestWinRateChampion2Photo by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_champions_cell_highest_win_rate_champion_image_2) }
    val highestWinRateChampion2Text by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_champions_cell_highest_win_rate_champion_value_2) }
    val highestWinRateChampion3Photo by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_champions_cell_highest_win_rate_champion_image_3) }
    val highestWinRateChampion3Text by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_champions_cell_highest_win_rate_champion_value_3) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.screen_player_champions, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val player = MyApp.get().player

        val repository = LeagueOfLegendsApiRepository()
        val provider = PlayerMatchHistoryProvider(repository)

        CoroutineScope(Dispatchers.IO).launch {

            val matches: MutableList<MatchData?> = mutableListOf()

            for (matchId in player.matchList){
                matches.add(provider.GetMatch(player.puuid, matchId))
            }

            CoroutineScope(Dispatchers.Main).launch {

                val championsUsage: LinkedHashMap<String, Int> = linkedMapOf()
                val championsWins: LinkedHashMap<String, Int> = linkedMapOf()

                FillMaps(matches, championsUsage, championsWins)

                SortMapsByDescending(championsUsage, championsWins)
            }
        }
    }

    private fun FillMaps(matches: MutableList<MatchData?>, championsUsage: LinkedHashMap<String, Int>,
                         championsWins: LinkedHashMap<String, Int>)
    {

        var valueToAdd = 0

        for (match in matches){
            if (match != null &&
                (match.queueId == 420 ||
                        match.queueId == 440 ||
                        match.queueId == 450 ||
                        match.queueId == 400 ||
                        match.queueId == 430))
            {
                championsUsage.merge(match.championName, 1 , Int::plus)
                if (match.win)
                {
                    valueToAdd = 1
                }
                championsWins.merge(match.championName, valueToAdd, Int::plus)
                valueToAdd = 0
            }
        }
    }

    private fun SortMapsByDescending(championsUsage: LinkedHashMap<String, Int>,
                                     championsWins: LinkedHashMap<String, Int>)
    {
        var championName: String

        for (i in 0 ..< championsWins.size)
        {
            championName = championsWins.keys.elementAt(i)
            championsWins[championName] = (championsWins[championName]!! * 100) / championsUsage[championName]!!
        }

        val sortedChampionsUsage = championsUsage.toSortedMap(compareByDescending<String>{ championsUsage[it] }.thenByDescending { it })
        val sortedHighestWinRateChampions = championsWins.toSortedMap(compareByDescending<String> { championsWins[it] }.thenByDescending { it })

        FillLayoutData(sortedChampionsUsage, sortedHighestWinRateChampions)
    }

    private fun FillLayoutData(sortedChampionsUsage: SortedMap<String, Int>, sortedHighestWinRateChampions: SortedMap<String, Int>){

        val mostPlayedChampionsPhoto: List<ShapeableImageView> = listOf(
            mostPlayedChampion0Photo, mostPlayedChampion1Photo,
            mostPlayedChampion2Photo, mostPlayedChampion3Photo
        )
        val mostPlayedChampionsText: List<MaterialTextView> = listOf(
            mostPlayedChampion0Text, mostPlayedChampion1Text,
            mostPlayedChampion2Text, mostPlayedChampion3Text
        )

        val highestWinRateChampionsPhoto: List<ShapeableImageView> = listOf(
            highestWinRateChampion0Photo, highestWinRateChampion1Photo,
            highestWinRateChampion2Photo, highestWinRateChampion3Photo
        )
        val highestWinRateChampionsText: List<MaterialTextView> = listOf(
            highestWinRateChampion0Text, highestWinRateChampion1Text,
            highestWinRateChampion2Text, highestWinRateChampion3Text
        )

        val valueToIterate = if (sortedChampionsUsage.size >= MAX_CELLS) MAX_CELLS else sortedChampionsUsage.size
        var championName: String

        for (i in 0..< valueToIterate)
        {
            championName = sortedChampionsUsage.keys.elementAt(i)

            MyFirebase.storage.LoadImage("/champion/${championName}.png").downloadUrl
                .addOnSuccessListener { uri ->
                    LoadIcon(mostPlayedChampionsPhoto[i], uri)
                }

            mostPlayedChampionsText[i].text = sortedChampionsUsage[championName].toString()

            championName = sortedHighestWinRateChampions.keys.elementAt(i)

            MyFirebase.storage.LoadImage("/champion/${championName}.png").downloadUrl
                .addOnSuccessListener { uri ->
                    LoadIcon(highestWinRateChampionsPhoto[i], uri)
                }

            highestWinRateChampionsText[i].text = "${sortedHighestWinRateChampions[championName]}%"
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