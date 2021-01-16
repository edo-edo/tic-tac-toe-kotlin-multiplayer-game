package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class StarGameOnlineMultiPlayerFragment : Fragment(R.layout.fragment_star_game_online_multi_player) {

    private  var playersList = mutableListOf<PlayerModel>()
    private  lateinit var playersAdapter: OnlinePlayersListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.online_Players_RecyclerView).apply {
            playersAdapter = OnlinePlayersListAdapter(playersList,
                object : ItemClickListener {
                    override fun viewClicked(position: Int) {
                        val onlinePlayer = playersList[position]
//                        playersList.removeAt(position)
//                        playersAdapter.notifyItemRemoved(position)
                    }

                })
            layoutManager = LinearLayoutManager(context)
            adapter = playersAdapter
        }

        playersList.add(PlayerModel("fgdgfdg",true))
        playersList.add(PlayerModel("irakli Chkhitunidze",true))
        playersList.add(PlayerModel("Giorgi ediberidze",true))
        playersAdapter.notifyDataSetChanged()




        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("Player")

        myRef.setValue(PlayerModel("irakli Chkhitunidze",false))







        //        val fistPlayerName = view.findViewById<EditText>(R.id.fist_player_name)
//        val secondPlayerName = view.findViewById<EditText>(R.id.second_player_name)
//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            if (fistPlayerName.text.isNotEmpty() && secondPlayerName.text.isNotEmpty()) {
//
//                val bundle = Bundle()
//                bundle.putString("fistPlayerName", fistPlayerName.text.toString())
//                bundle.putString("secondPlayerName", secondPlayerName.text.toString())
//
//                findNavController().navigate(R.id.action_StarGameLocalMultiPlayerFragment_to_OfflinePlayerFragment,bundle)
//            } else {
//                it.myCustomSnackbar("please fill out this fields", R.id.button_second)
//            }
//        }
    }
}