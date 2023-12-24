package cdi.interfacedesign.lolrankedtracker.fragments.components

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import com.google.android.material.appbar.MaterialToolbar

class AppToolbar: Fragment() {

    companion object{
        private lateinit var instance: AppToolbar
        fun get() = instance
    }

    lateinit var toolbar: MaterialToolbar

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            AppMainMenu.get().OpenDrawer()
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.toolbar_exit_button -> {
                    MyApp.get().currentActivity.finish()
                    //throw RuntimeException("Test Crash") // Force a crash
                    MyFirebase.crashlytics.logSimpleError("Subnormal Error"){
                        key("Subnormal Name", "Abraham")
                        key("IsSubnormal", true)
                        key("Level of Subnormality", 9001)
                    };
                }
            }
            true
        }
    }
}