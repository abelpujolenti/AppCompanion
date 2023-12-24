package cdi.interfacedesign.lolrankedtracker.fragments.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cdi.interfacedesign.lolrankedtracker.R

class AppTrackedPlayer : Fragment() {

    companion object{
        private lateinit var instance: AppTrackedPlayer
        fun get() = instance
    }

    lateinit var fragmentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.component_navigation_bottom_bar, container, false)
        return fragmentView
    }


}