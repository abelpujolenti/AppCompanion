package cdi.interfacedesign.lolrankedtracker.firebase

import android.app.Application

class MyFirebase {

    companion object{

        lateinit var analytics: MyFirebaseAnalytics;
        lateinit var authentication: MyFirebaseAuthentication;
        val crashlytics = MyCrashlytics();
        val dataBase = MyFirebaseDatBase();
        val storage = MyFirebaseStorage();

        fun Init(appContext: Application){
            analytics = MyFirebaseAnalytics(appContext)
            authentication = MyFirebaseAuthentication(appContext)
        }

    }
}