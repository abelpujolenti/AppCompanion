package cdi.interfacedesign.lolrankedtracker

import android.app.Application

class MyApp : Application() {

    companion object{

        private lateinit var instance : MyApp;
        public fun get() = instance;
    }

    override fun onCreate() {
        super.onCreate()
        instance = this;

    }

}