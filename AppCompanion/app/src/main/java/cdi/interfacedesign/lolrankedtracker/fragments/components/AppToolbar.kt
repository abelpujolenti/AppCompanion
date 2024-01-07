package cdi.interfacedesign.lolrankedtracker.fragments.components

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import cdi.interfacedesign.lolrankedtracker.fragments.screens.LeaderBoardScreen
import cdi.interfacedesign.lolrankedtracker.fragments.screens.TrackerScreen
import cdi.interfacedesign.lolrankedtracker.utils.SharedPreferencesManager
import com.google.android.material.appbar.MaterialToolbar

class AppToolbar: Fragment() {

    companion object{
        private lateinit var instance: AppToolbar
        fun get() = instance
    }

    lateinit var toolbar: MaterialToolbar
    lateinit var backItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.component_toolbar, container, false);
        toolbar = view.findViewById(R.id.app_toolbar)
        backItem = toolbar.menu.findItem(R.id.toolbar_exit_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HideNavigationItem()
        HideBackItem()

        toolbar.setNavigationOnClickListener {
            AppMainMenu.get().OpenDrawer()
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.toolbar_exit_button -> {
                    HideBackItem()
                    val sharedPreferences = SharedPreferencesManager
                    when(sharedPreferences.previousScreen){
                        sharedPreferences.TRACKER_KEY -> {
                            AppMainMenu.get().ReplaceScreen(TrackerScreen(), false)
                            toolbar.title = resources.getString(R.string.tracker_title)
                        }
                        sharedPreferences.LEADERBOARD_KEY -> {
                            AppMainMenu.get().ReplaceScreen(LeaderBoardScreen(), false)
                            toolbar.title = resources.getString(R.string.drawer_leaderboard_button)
                        }
                    }
                }
            }
            true
        }
    }

    fun HideNavigationItem(){
        toolbar.setNavigationIcon(null)
    }

    fun ShowNavigationItem(){
        toolbar.setNavigationIcon(resources.getDrawable(R.drawable.tool_bar_navigation_icon))
    }

    fun HideBackItem(){
        backItem.setVisible(false)
    }

    fun ShowBackItem(){
        backItem.setVisible(true)
    }
}