package com.example.moviesfilmroomfirebase

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

class App: Application() {

    //??lateinit var petsInteractor: PetsInteractor
    //??    private set
    lateinit var firebaseAnalytics: FirebaseAnalytics
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        //??initDatabase()
        //??initInteractors()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        FirebaseCrashlytics.getInstance().setUserId("12345")


    }

    companion object {
        const val TAG = "App"
        lateinit var instance: App
            private set
    }
}