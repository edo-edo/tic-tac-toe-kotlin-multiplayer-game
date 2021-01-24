package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game

import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.tools.SharedPrefManager.Companion.getInstance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class StarGameOnlineMultiPlayerFragment :
    Fragment(R.layout.fragment_star_game_online_multi_player) {

    private var playersList = mutableListOf<PlayerModel>()
    private lateinit var playersAdapter: OnlinePlayersListAdapter

    lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onlinePlayerName = view.findViewById<EditText>(R.id.online_player_name)
        val saveButton = view.findViewById<Button>(R.id.save_button)

        auth = FirebaseAuth.getInstance()
        // auth.currentUser?.uid
        val onlineUser = auth.currentUser
        val uID = onlineUser?.uid
        val currentEmail = onlineUser?.email

        // Write a message to the database
        val database = Firebase.database

        val reference = database.reference

        val myRef = database.getReference("Players/$uID")
        val myRefActiveStatus = database.getReference("Players_ActiveStatus")


        context?.let {
            getInstance(it)?.saveUser(
                PlayerModel(
                    "irakli Chkhitunidze",
                    null,
                    null,
                    null
                )
            )
        }
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

        activeUsers(database)
        fetchCurrentUser()
        // fetchCurrentPlayers()

//        playersList.add(PlayerModel("fgdgfdg", null, null, null))
//        playersList.add(PlayerModel("null", null, "irakli Chkhitunidze", null))
//        playersList.add(PlayerModel("Giorgi ediberidze", null, null, null))
//        playersList.add(PlayerModel("fgdgfdg", null, null, null))
//        playersList.add(PlayerModel("null", null, "irakli Chkhitunidze", null))
//        playersList.add(PlayerModel("Giorgi ediberidze", null, null, null))
//        playersList.add(PlayerModel("fgdgfdg", null, null, null))
//        playersList.add(PlayerModel("null", null, "irakli Chkhitunidze", null))
//        playersList.add(PlayerModel("Giorgi ediberidze", null, null, null))
//        playersList.add(PlayerModel("fgdgfdg", null, null, null))
//        playersList.add(PlayerModel("null", null, "irakli Chkhitunidze", null))
//        playersList.add(PlayerModel("Giorgi ediberidze", null, null, null))
//        playersList.add(PlayerModel("fgdgfdg", null, null, null))
//        playersList.add(PlayerModel("null", null, "irakli Chkhitunidze", null))
//        playersList.add(PlayerModel("Giorgi ediberidze", null, null, null))
//        playersList.add(PlayerModel("fgdgfdg", null, null, null))
//        playersList.add(PlayerModel("null", null, "irakli Chkhitunidze", null))
//        playersList.add(PlayerModel("Giorgi ediberidze", null, null, null))
//        playersList.add(PlayerModel("fgdgfdg", null, null, null))
//        playersList.add(PlayerModel("null", null, "irakli Chkhitunidze", null))
//        playersList.add(PlayerModel("Giorgi ediberidze", null, null, null))
//        playersList.add(PlayerModel("fgdgfdg", null, null, null))
//        playersList.add(PlayerModel("null", null, "irakli Chkhitunidze", null))
        playersList.add(PlayerModel("Giorgi ediberidze", null, null, null))
        playersAdapter.notifyDataSetChanged()





        reference.child("Players").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = (dataSnapshot.value as Map<*, *>?)
                val point = map?.get(uID)
                //  playersList = point as MutableList<PlayerModel>
//                val sdf = map[uid] as PlayerModel?
                d("fdfvmapcddwd", map.toString())

                d("fdfvcddwd", point.toString())
                //d("fdfvcddwd", playersList.toString())

//                d("fdfvcddwd", sdf.toString())

                d("fdfdvd", dataSnapshot.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Get Post object and use the values to update the UI
//                 val post = (dataSnapshot.value as Map<*, *>?)
//                // ...
//                d("dsiuhfdsd", dataSnapshot.toString())
//                // onlinePlayerName.text =post?.name?.toEditable()
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                d("TAGdgergerg", "loadPost:onCancelled", databaseError.toException())
//                // ...
//            }
//        }
//        reference.addValueEventListener(postListener)


        //myRef.setValue(playersList)

        saveButton.setOnClickListener {

            myRef.setValue(
                PlayerModel(
                    uID,
                    currentEmail,
                    onlinePlayerName.text.toString(),
                    true
                )
            )
        }
        myRefActiveStatus.setValue(
            PlayerModel(
                uID,
                currentEmail,
                onlinePlayerName.text.toString(),
                true
            )
        )

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


    private fun activeUsers(database: FirebaseDatabase): List<PlayerModel>? {
        database.reference.child("Players")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val map = (dataSnapshot.value as Map<*, *>?)
                    d("fdfdvd", dataSnapshot.toString())
                    map?.forEach {
                        val model = it.value as Map<*, *>?

                        d("activeStatusX", model?.get("activeStatus").toString())
                        d("activeStatusX", model?.get("email").toString())
                        d("activeStatusX", model?.get("name").toString())
                        d("activeStatusX", model?.get("uid").toString())

                        d("fdfvmapcgfdfgfgdfgddwd", it.key.toString())
                        d("fdfvmapcfdfgfddwd", it.value.toString())
                        //  d("fdfvmapcffddwd", (it.value as PlayerModel).toString())
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        return null
    }


    private fun fetchCurrentUser() {
        val uid = auth.currentUser!!.uid
        val ref = FirebaseDatabase.getInstance().getReference("Players/$uid")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser = snapshot.getValue(PlayerModel::class.java)

                d("sdas", currentUser.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun fetchCurrentPlayers() {
        val uid = auth.currentUser!!.uid
        val ref = FirebaseDatabase.getInstance().getReference("Players")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                val  currentUser = snapshot.getValue(List<PlayerModel::class.java>())
                val currentUsers =
                    snapshot.getValue(mutableMapOf<String, PlayerModel>()::class.java)

                d("sdagfds", currentUsers.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}