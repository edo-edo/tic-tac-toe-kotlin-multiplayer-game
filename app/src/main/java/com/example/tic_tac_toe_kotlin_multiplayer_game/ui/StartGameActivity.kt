package com.example.tic_tac_toe_kotlin_multiplayer_game.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.MultiPlayerActivity
import com.example.tic_tac_toe_kotlin_multiplayer_game.ui.newgame.NewCPUGameActivity

class StartGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        findViewById<Button>(R.id.multiPlayer_Button).apply {
            setOnClickListener {
                val intent = Intent(context, MultiPlayerActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

        }
        findViewById<Button>(R.id.new_Game_Button).apply {
            setOnClickListener {
                val intent = Intent(context, NewCPUGameActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

        }
    }
}