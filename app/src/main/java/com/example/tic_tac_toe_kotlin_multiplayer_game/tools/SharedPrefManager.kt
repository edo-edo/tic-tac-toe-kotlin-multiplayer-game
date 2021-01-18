package com.example.tic_tac_toe_kotlin_multiplayer_game.tools

import android.content.Context
import android.content.SharedPreferences
import com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game.PlayerModel


class SharedPrefManager private constructor(private val mCtx: Context) {
    fun saveUser(user: PlayerModel) {
        val sharedPreferences: SharedPreferences =
            mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", user.uid)
        editor.putString("email", user.Email)
        editor.putString("name", user.name)
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences: SharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }
    val user: PlayerModel
        get() {
            val sharedPreferences: SharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return PlayerModel(
                sharedPreferences.getString("id", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                null
            )
        }

    fun clear() {
        val sharedPreferences: SharedPreferences =
            mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager? {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance
        }
    }

}