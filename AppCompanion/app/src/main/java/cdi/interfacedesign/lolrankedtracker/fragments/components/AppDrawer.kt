package cdi.interfacedesign.lolrankedtracker.fragments.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.fragments.screens.LoginScreen
import com.google.android.material.navigation.NavigationView

class AppDrawer : Fragment(), DrawerListener {

    companion object{

        private lateinit var instance : AppDrawer
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
    ): View? {
        fragmentView = inflater.inflate(R.layout.component_navigationdrawer, container, false);
        return fragmentView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawer.addDrawerListener(this);

        navigationDrawer.setNavigationItemSelectedListener { menuItem ->

            when(menuItem.itemId)
            {
                R.id.drawer_tracker_button -> {

                    //TODO

                    drawer.close()

                }
                R.id.drawer_leaderboard_button -> {
                    //TODO
                }
                R.id.drawer_configuration_button -> {
                    //TODO
                }
            }

            true
        }
    }

    fun OpenLogin(){
        val loginScreen = LoginScreen();
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right)
            .replace(R.id.reusable_dialog_container, loginScreen)
            .addToBackStack(null)
            .commit()
    }

    fun OpenDrawer()
    {
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