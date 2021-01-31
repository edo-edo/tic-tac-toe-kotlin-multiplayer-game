package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.tools.SharedPrefManager


class GameModeFragment : Fragment(R.layout.fragment_game_mode) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.local_Game_Button).setOnClickListener {
            findNavController().navigate(R.id.action_GameModeFragment_to_StarGameLocalMultiPlayerFragment)
        }

        view.findViewById<Button>(R.id.online_Game_Button).setOnClickListener {

            if ( SharedPrefManager.getInstance()?.isLoggedIn.toString()  == "true") {
                findNavController().navigate(R.id.action_GameModeFragment_to_StarGameOnlineMultiPlayerFragment)
            } else {
                findNavController().navigate(R.id.action_GameModeFragment_to_LogInToOnlineGameFragment)
            }
        }
    }
}