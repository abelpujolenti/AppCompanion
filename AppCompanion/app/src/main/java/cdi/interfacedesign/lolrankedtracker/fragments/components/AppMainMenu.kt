package cdi.interfacedesign.lolrankedtracker.fragments.components

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.fragments.screens.ConfigurationScreen
import cdi.interfacedesign.lolrankedtracker.fragments.screens.LeaderBoardScreen
import cdi.interfacedesign.lolrankedtracker.fragments.screens.LoginScreen
import cdi.interfacedesign.lolrankedtracker.fragments.screens.TrackerScreen
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.PlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.repositories.LeagueOfLegendsApiRepository
import com.google.android.material.navigation.NavigationView

class AppMainMenu : Fragment(), DrawerListener {

    companion object{

        private lateinit var instance : AppMainMenu
        fun get() = instance

    }

    lateinit var fragmentView: View
    private val drawer by lazy {fragmentView.findViewById<DrawerLayout>(R.id.app_drawer)}
    private val navigationDrawer by lazy {fragmentView.findViewById<NavigationView>(R.id.navigation_drawer)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.component_navigation_drawer, container, false);
        return fragmentView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var screen: Fragment = TrackerScreen()
        ReplaceScreen(screen, true)

        navigationDrawer.setNavigationItemSelectedListener { menuItem ->

            AppToolbar.get().toolbar.title = menuItem.title

            when(menuItem.itemId)
            {
                R.id.drawer_tracker_button -> {
                    screen = TrackerScreen()
                }
                R.id.drawer_leaderboard_button -> {
                    screen = LeaderBoardScreen()
                }
                R.id.drawer_configuration_button -> {
                    screen = ConfigurationScreen()
                }
            }
            AppToolbar.get().toolbar.title = menuItem.title
            ReplaceScreen(screen, false)
            drawer.close()
            true
        }
        //navigationDrawer.menu.getItem(0).itemId
    }

    fun ReplaceScreen(screen: Fragment, start: Boolean){
        val transaction = childFragmentManager.beginTransaction()
        if (!start){

            transaction.setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right
            )
        }
        transaction.replace(R.id.reusable_main_menu_container, screen)
            .addToBackStack(null)
            .commit()
    }

    fun OpenLogin(){
        val loginScreen = LoginScreen();
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right)
            .replace(R.id.reusable_main_menu_container, loginScreen)
            .addToBackStack(null)
            .commit()
    }

    fun OpenDrawer()
    {
        Log.e("Open", "open")
        drawer.open()
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        return
    }

    override fun onDrawerOpened(drawerView: View) {
        return
    }

    override fun onDrawerClosed(drawerView: View) {
        return
    }

    override fun onDrawerStateChanged(newState: Int) {
        return
    }
}