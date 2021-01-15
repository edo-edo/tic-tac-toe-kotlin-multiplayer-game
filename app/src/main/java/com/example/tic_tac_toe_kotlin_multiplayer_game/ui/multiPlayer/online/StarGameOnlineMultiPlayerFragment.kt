package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.myCustomSnackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class StarGameOnlineMultiPlayerFragment : Fragment(R.layout.fragment_star_game_local_multi_player) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fistPlayerName = view.findViewById<EditText>(R.id.fist_player_name)
        val secondPlayerName = view.findViewById<EditText>(R.id.second_player_name)


        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            if (fistPlayerName.text.isNotEmpty() && secondPlayerName.text.isNotEmpty()) {

                val bundle = Bundle()
                bundle.putString("fistPlayerName", fistPlayerName.text.toString())
                bundle.putString("secondPlayerName", secondPlayerName.text.toString())

                findNavController().navigate(R.id.action_StarGameLocalMultiPlayerFragment_to_OfflinePlayerFragment,bundle)
            } else {
                it.myCustomSnackbar("please fill out this fields", R.id.button_second)
            }
        }
    }
}