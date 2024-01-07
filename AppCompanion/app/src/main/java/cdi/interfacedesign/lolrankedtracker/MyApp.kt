package cdi.interfacedesign.lolrankedtracker

import android.app.Activity
import android.app.Application
import android.view.View
import android.view.inputmethod.InputMethodManager
import cdi.interfacedesign.lolrankedtracker.firebase.MyFirebase
import cdi.interfacedesign.lolrankedtracker.fragments.screens.TrackerScreen
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.adapter.TrackedPlayerAdapter
import cdi.interfacedesign.lolrankedtracker.leagueoflegends.data.PlayerData

class MyApp : Application() {

    companion object{

        private lateinit var instance : MyApp
        public fun get() = instance
    }

    lateinit var currentActivity: Activity

    override fun onCreate() {
        super.onCreate()
        instance = this
        MyFirebase.Init(this)
        MyFirebase.analytics.LogOpenApp()

    }

    fun CloseKeyboard(focusedView: View)
    {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }

}