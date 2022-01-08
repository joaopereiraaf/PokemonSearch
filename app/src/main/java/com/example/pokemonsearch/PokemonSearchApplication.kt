package com.example.pokemonsearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

//@HiltAndroidApp
class PokemonSearchApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}