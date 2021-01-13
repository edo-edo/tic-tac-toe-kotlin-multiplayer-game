package com.example.tic_tac_toe_kotlin_multiplayer_game

import android.app.Application
import android.content.Context

/**
 * Created by nikolozakhvlediani on 12/24/20.
 */
class RootApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }

    companion object {
        lateinit var instance: RootApp
        private lateinit var context: Context
    }

    fun getContext() = context
}