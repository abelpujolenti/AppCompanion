package cdi.interfacedesign.lolrankedtracker.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.LeaderboardPlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository

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

        val queueTypes = resources.getStringArray(R.array.QueueType)
        val tiers = resources.getStringArray(R.array.Tiers)
        val ranks = resources.getStringArray(R.array.Ranks)

        queueTypeSelected = "RANKED_SOLO_5x5"
        tierSelected = "CHALLENGER"
        rankSelected = "I"

        spinnerQueueType.adapter = ArrayAdapter(MyApp.get().currentActivity, android.R.layout.simple_spinner_item, queueTypes)
        spinnerTier.adapter = ArrayAdapter(MyApp.get().currentActivity, android.R.layout.simple_spinner_item, tiers)
        spinnerRank.adapter = ArrayAdapter(MyApp.get().currentActivity, android.R.layout.simple_spinner_item, ranks)

        spinnerQueueType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spinnerQueueType.selectedItem.toString())
                {
                    queueTypes[0] -> queueTypeSelected = "RANKED_SOLO_5x5"
                    queueTypes[1] -> queueTypeSelected = "RANKED_FLEX_SR"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        spinnerTier.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spinnerQueueType.selectedItem.toString())
                {
                    tiers[0] -> tierSelected = "CHALLENGER"
                    tiers[1] -> tierSelected = "GRANDMASTER"
                    tiers[2] -> tierSelected = "MASTER"
                    tiers[3] -> tierSelected = "DIAMOND"
                    tiers[4] -> tierSelected = "EMERALD"
                    tiers[5] -> tierSelected = "PLATINUM"
                    tiers[6] -> tierSelected = "GOLD"
                    tiers[7] -> tierSelected = "SILVER"
                    tiers[8] -> tierSelected = "BRONZE"
                    tiers[9] -> tierSelected = "IRON"
                }

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
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        table.layoutManager = LinearLayoutManager(MyApp.get().currentActivity)

        val repository = LeagueOfLegendsApiRepository()

        val leaderboardPlayerAdapter = LeaderboardPlayerAdapter(repository)
        table.adapter = leaderboardPlayerAdapter

        searchButton.setOnClickListener {
            leaderboardPlayerAdapter.LoadLeaderboardPlayersData(queueTypeSelected, tierSelected, rankSelected, 1)
        }
    }
}