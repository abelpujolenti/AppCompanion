package cdi.interfacedesign.lolrankedtracker.fragments.components

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
import cdi.interfacedesign.lolrankedtracker.utils.SharedPreferencesManager
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class AppTrackedPlayer : Fragment() {

    lateinit var fragmentView: View

    val profileIcon by lazy { fragmentView.findViewById<ShapeableImageView>(R.id.player_profile_icon) }
    val summonerName by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_summoner_name) }
    val summonerLevel by lazy { fragmentView.findViewById<MaterialTextView>(R.id.player_profile_summoner_level) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.component_navigation_bottom_bar, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val player = SharedPreferencesManager.player

        MyFirebase.storage.LoadImage("/profileIcon/${player.profileIconId}.png").downloadUrl
            .addOnSuccessListener { uri ->
                LoadIcon(profileIcon, uri)
            }

        summonerName.text = player.name
        summonerLevel.text = "Level ${player.summonerLevel}"
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