package com.example.tic_tac_toe_kotlin_multiplayer_game.tools

import android.content.Context
import android.content.SharedPreferences
import com.example.tic_tac_toe_kotlin_multiplayer_game.RootApp
import com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game.PlayerModel


class SharedPrefManager  {

    fun saveUser(user: PlayerModel) {
        val sharedPreferences: SharedPreferences =
            RootApp.instance.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("logged", true)
        editor.putString("id", user.uid)
        editor.putString("email", user.Email)
        editor.putString("name", user.name)
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences: SharedPreferences =
                RootApp.instance.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("logged", false)
        }


    val user: PlayerModel
        get() {
            val sharedPreferences: SharedPreferences =
                RootApp.instance.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return PlayerModel(
                sharedPreferences.getString("id", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                true
            )
        }

    fun clear() {
        val sharedPreferences: SharedPreferences =
            RootApp.instance.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("logged", false)
        editor.clear()
        editor.apply()
    }

    fun logOut() {
        val sharedPreferences: SharedPreferences =
            RootApp.instance.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.putBoolean("logged", false)
        editor.apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(): SharedPrefManager? {
            if (mInstance == null) {
                mInstance = SharedPrefManager()
            }
            return mInstance
        }
    }

}