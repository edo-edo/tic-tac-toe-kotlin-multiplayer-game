package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.toEditable
import com.example.tic_tac_toe_kotlin_multiplayer_game.tools.SharedPrefManager.Companion.getInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class StarGameOnlineMultiPlayerFragment : Fragment(R.layout.fragment_star_game_online_multi_player) {

    private  var playersList = mutableListOf<PlayerModel>()
    private  lateinit var playersAdapter: OnlinePlayersListAdapter

    lateinit var auth: FirebaseAuth



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onlinePlayerName = view.findViewById<EditText>(R.id.online_player_name)
        val saveButton = view.findViewById<Button>(R.id.save_button)



        context?.let { getInstance(it)?.saveUser(PlayerModel("irakli Chkhitunidze",null,null,null)) }
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

        playersList.add(PlayerModel("fgdgfdg",null,null,null))
        playersList.add(PlayerModel("null",null,"irakli Chkhitunidze",null))
        playersList.add(PlayerModel("Giorgi ediberidze",null,null,null))
        playersList.add(PlayerModel("fgdgfdg",null,null,null))
        playersList.add(PlayerModel("null",null,"irakli Chkhitunidze",null))
        playersList.add(PlayerModel("Giorgi ediberidze",null,null,null))
        playersList.add(PlayerModel("fgdgfdg",null,null,null))
        playersList.add(PlayerModel("null",null,"irakli Chkhitunidze",null))
        playersList.add(PlayerModel("Giorgi ediberidze",null,null,null))
        playersList.add(PlayerModel("fgdgfdg",null,null,null))
        playersList.add(PlayerModel("null",null,"irakli Chkhitunidze",null))
        playersList.add(PlayerModel("Giorgi ediberidze",null,null,null))
        playersList.add(PlayerModel("fgdgfdg",null,null,null))
        playersList.add(PlayerModel("null",null,"irakli Chkhitunidze",null))
        playersList.add(PlayerModel("Giorgi ediberidze",null,null,null))
        playersList.add(PlayerModel("fgdgfdg",null,null,null))
        playersList.add(PlayerModel("null",null,"irakli Chkhitunidze",null))
        playersList.add(PlayerModel("Giorgi ediberidze",null,null,null))
        playersList.add(PlayerModel("fgdgfdg",null,null,null))
        playersList.add(PlayerModel("null",null,"irakli Chkhitunidze",null))
        playersList.add(PlayerModel("Giorgi ediberidze",null,null,null))
        playersList.add(PlayerModel("fgdgfdg",null,null,null))
        playersList.add(PlayerModel("null",null,"irakli Chkhitunidze",null))
        playersList.add(PlayerModel("Giorgi ediberidze",null,null,null))
        playersAdapter.notifyDataSetChanged()



        auth = FirebaseAuth.getInstance()
       // auth.currentUser?.uid
        val onlineUser = auth.currentUser
        val uid = onlineUser?.uid

        val reference = Firebase.database.reference

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
               // val post = dataSnapshot.getValue<PlayerModel>()
                // ...
d("dsiuhfdsd", dataSnapshot.toString())
               // onlinePlayerName.text =post?.name?.toEditable()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.d("TAGdgergerg", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        reference.addValueEventListener(postListener)


        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("Players/$uid")


        //myRef.setValue(playersList)

        saveButton.setOnClickListener {

            myRef.setValue(PlayerModel("chlhktu Chkhitdsfsfsdfsdfunidzeikakooo","gmailikakoonull@Gmail.com",onlinePlayerName.text.toString(),true))
        }




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