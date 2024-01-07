package cdi.interfacedesign.lolrankedtracker.fragments.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.fragments.components.AppToolbar
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.LeaderboardPlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository
import cdi.interfacedesign.lolrankedtracker.utils.SharedPreferencesManager
import java.util.Locale

private const val RANKED_SOLO = "RANKED_SOLO_5x5"
private const val RANKED_FLEX = "RANKED_FLEX_SR"

class LeaderBoardScreen : Fragment() {

    lateinit var fragmentView: View
    val spinnerQueueType by lazy { fragmentView.findViewById<Spinner>(R.id.spinner_queue_type) }
    val spinnerTier by lazy { fragmentView.findViewById<Spinner>(R.id.spinner_tier) }
    val layoutRanks by lazy { fragmentView.findViewById<LinearLayout>(R.id.layout_ranks) }
    val spinnerRank by lazy { fragmentView.findViewById<Spinner>(R.id.spinner_rank) }
    val searchButton by lazy { fragmentView.findViewById<ImageButton>(R.id.leaderboard_search_button) }
    val table by lazy { fragmentView.findViewById<RecyclerView>(R.id.leaderboard_ranked_cells) }

    lateinit var queueTypeSelected: String
    lateinit var tierSelected: String
    lateinit var rankSelected: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.screen_ranked_leaderboard, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppToolbar.get().ShowNavigationItem()

        val queueTypes = resources.getStringArray(R.array.QueueType)
        val tiers = resources.getStringArray(R.array.Tiers)
        val ranks = resources.getStringArray(R.array.Ranks)

        queueTypeSelected = SharedPreferencesManager.queueTypeSelected
        tierSelected = SharedPreferencesManager.tierSelected
        rankSelected = SharedPreferencesManager.rankSelected

        val spinnerQueueTypeAdapter = ArrayAdapter(MyApp.get().currentActivity, android.R.layout.simple_spinner_item, queueTypes)
        val spinnerTierAdapter = ArrayAdapter(MyApp.get().currentActivity, android.R.layout.simple_spinner_item, tiers)
        val spinnerRankAdapter = ArrayAdapter(MyApp.get().currentActivity, android.R.layout.simple_spinner_item, ranks)

        spinnerQueueType.adapter = spinnerQueueTypeAdapter
        spinnerTier.adapter = spinnerTierAdapter
        spinnerRank.adapter = spinnerRankAdapter

        lateinit var value: String

        when(SharedPreferencesManager.queueTypeSelected){
            RANKED_SOLO -> value = "Ranked Solo/Duo"
            RANKED_FLEX -> value = "Ranked Flex"
        }

        var spinnerPosition = spinnerQueueTypeAdapter.getPosition(value);
        spinnerQueueType.setSelection(spinnerPosition)

        spinnerPosition = spinnerTierAdapter.getPosition(SharedPreferencesManager.tierSelected)
        spinnerTier.setSelection(spinnerPosition)

        value = SharedPreferencesManager.tierSelected

        value = if (value == tiers[0] || value == tiers[1] || value == tiers[2]){
            "I"
        } else{
            SharedPreferencesManager.rankSelected
        }

        spinnerPosition = spinnerRankAdapter.getPosition(value)
        spinnerRank.setSelection(spinnerPosition)

        spinnerQueueType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spinnerQueueType.selectedItem.toString())
                {
                    queueTypes[0] -> {
                        queueTypeSelected = RANKED_SOLO
                    }
                    queueTypes[1] -> {
                        SharedPreferencesManager
                        queueTypeSelected = RANKED_FLEX
                    }
                }
                SharedPreferencesManager.queueTypeSelected = queueTypeSelected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        spinnerTier.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                value = spinnerTier.selectedItem.toString()

                SharedPreferencesManager.tierSelected = value
                when(value)
                {
                    tiers[1] -> value = "GrandMaster"
                }
                tierSelected = value.uppercase()

                if (position == 0 || position == 1 || position == 2){
                    layoutRanks.visibility = View.INVISIBLE
                    spinnerRank.setSelection(spinnerRank.getItemIdAtPosition(0).toInt())
                    return
                }
                layoutRanks.visibility = View.VISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        spinnerRank.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                rankSelected = spinnerRank.selectedItem.toString()
                SharedPreferencesManager.rankSelected = rankSelected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        table.layoutManager = LinearLayoutManager(MyApp.get().currentActivity)
        table.adapter = SharedPreferencesManager.leaderboardPlayerAdapter

        searchButton.setOnClickListener {
            SharedPreferencesManager.leaderboardPlayerAdapter.LoadLeaderboardPlayersData(queueTypeSelected, tierSelected, rankSelected, 1)
        }
    }
}