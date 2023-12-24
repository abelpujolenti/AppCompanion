package cdi.interfacedesign.lolrankedtracker.firebase

import android.app.Application

class MyFirebase {

    companion object{

        lateinit var analytics: MyFirebaseAnalytics;
        val authentication = MyFirebaseAuthentication();
        val crashlytics = MyCrashlytics();
        val dataBase = MyFirebaseDataBase();
        val storage = MyFirebaseStorage();

        fun Init(appContext: Application){
            analytics = MyFirebaseAnalytics(appContext)
        }

    }
}