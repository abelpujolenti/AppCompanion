package cdi.interfacedesign.lolrankedtracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_main_menu)

        MyApp.get().currentActivity = this
    }
}