package cdi.interfacedesign.lolrankedtracker.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import cdi.interfacedesign.lolrankedtracker.MyApp
import cdi.interfacedesign.lolrankedtracker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_main_activity) //Aquí veras que el botón de sign in no hace nada
        //setContentView(R.layout.screen_main_menu) //Aquí veras que el drawer no funciona

        MyApp.get().currentActivity = this
    }
}