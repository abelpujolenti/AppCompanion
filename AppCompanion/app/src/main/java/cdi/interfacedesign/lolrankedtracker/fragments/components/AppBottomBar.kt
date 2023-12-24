package cdi.interfacedesign.lolrankedtracker.fragments.components

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import cdi.interfacedesign.lolrankedtracker.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppBottomBar: Fragment() {

    companion object{
        private lateinit var instance: AppBottomBar
        fun get() = instance;
    }

    lateinit var bottomBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.component_bottom_bar, container, false);
        bottomBar = view.findViewById(R.id.player_navigation_bottom_bar);
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomBar.setOnItemSelectedListener { menuItem ->

            AppToolbar.get().toolbar.title = menuItem.title

            if (menuItem.itemId != bottomBar.selectedItemId)
            {

                when(menuItem.itemId){
                    R.id.player_profile_button -> {
                        when(bottomBar.selectedItemId){
                            R.id.player_champions_button -> {
                                AppNavigationHost.get().navigationHost.navigate(R.id.transition_champions_to_profile)
                            }
                            R.id.player_match_history_button -> {
                                AppNavigationHost.get().navigationHost.navigate(R.id.transition_match_history_to_profile)
                            }
                        }
                    }
                    R.id.player_champions_button -> {
                        when(bottomBar.selectedItemId){
                            R.id.player_profile_button -> {
                                AppNavigationHost.get().navigationHost.navigate(R.id.transition_profile_to_champions)
                            }
                            R.id.player_match_history_button -> {
                                AppNavigationHost.get().navigationHost.navigate(R.id.transition_match_history_to_champions)
                            }
                        }
                    }

                    R.id.player_match_history_button -> {
                        when(bottomBar.selectedItemId){
                            R.id.player_profile_button -> {
                                AppNavigationHost.get().navigationHost.navigate(R.id.transition_profile_to_match_history)
                            }
                            R.id.player_champions_button -> {
                                AppNavigationHost.get().navigationHost.navigate(R.id.transition_champions_to_match_history)
                            }
                        }
                    }
                }
            }
            true
        }
        bottomBar.selectedItemId = bottomBar.menu.getItem(0).itemId
    }

}