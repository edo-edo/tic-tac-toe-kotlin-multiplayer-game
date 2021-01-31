package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tic_tac_toe_kotlin_multiplayer_game.R

class MultiPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

    }
}