plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "cdi.interfacedesign.lolrankedtracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "cdi.interfacedesign.lolrankedtracker"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //CircleImage
    implementation("com.google.android.material:material:1.11.0")


    /////////FireBase/////////
    implementation("com.google.firebase:firebase-config-ktx")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    //Analytics
    implementation("com.google.firebase:firebase-analytics")

    //Authentication
    implementation("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    //Firestore
    implementation("com.google.firebase:firebase-firestore")

    //Storage
    implementation("com.google.firebase:firebase-storage")
    implementation("com.firebaseui:firebase-ui-storage:7.2.0")

    //Crashlytics
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    //Messaging
    implementation("com.google.firebase:firebase-messaging")

    //API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Splash Screen
    implementation ("androidx.core:core-splashscreen:1.0.0")
}