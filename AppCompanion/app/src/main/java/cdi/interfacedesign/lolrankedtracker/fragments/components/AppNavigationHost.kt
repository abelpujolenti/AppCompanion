package cdi.interfacedesign.lolrankedtracker.fragments.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import cdi.interfacedesign.lolrankedtracker.R

class AppNavigationHost : Fragment() {

    companion object{
        private lateinit var instance : AppNavigationHost
        fun get() = instance
    }

    lateinit var navigationHost : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.component_navigation_host, container, false)

        val navigationHostFragment = childFragmentManager.findFragmentById(R.id.app_navigation_host_fragment) as NavHostFragment
        navigationHost = navigationHostFragment.navController
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}