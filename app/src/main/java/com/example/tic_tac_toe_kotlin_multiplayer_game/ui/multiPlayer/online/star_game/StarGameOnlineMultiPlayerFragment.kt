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
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.snackBar
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.toEditable
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
        activePlayers(database, view)
        fetchCurrentUser(onlinePlayerName)
        d("sjhds", activePlayers(database, view).toString())
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
//        playersList.add(PlayerModel("Giorgi ediberidze", null, null, null))
//        playersAdapter.notifyDataSetChanged()


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

    }


    private fun activePlayers(database: FirebaseDatabase, view: View) {
        playersList.clear()

        database.reference.child("Players")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val map = (dataSnapshot.value as Map<*, *>?)
                    playersList.clear()
                    d("fdfdvd", dataSnapshot.toString())

                    map?.forEach {
                        val player = it.value as Map<*, *>?

                        d("activeStatusX", player?.get("activeStatus").toString())
                        d("activeStatusX", player?.get("email").toString())
                        d("activeStatusX", player?.get("name").toString())
                        d("activeStatusX", player?.get("uid").toString())

                        d("fdfvmapcgfdfgfgdfgddwd", it.key.toString())
                        d("fdfvmapcfdfgfddwd", it.value.toString())
                        //  d("fdfvmapcffddwd", (it.value as PlayerModel).toString())


                        if (player?.get("activeStatus") == true) {
                            playersList.add(
                                PlayerModel(
                                    player["uid"].toString(),
                                    player["email"].toString(),
                                    player["name"].toString(),
                                    true
                                )
                            )
                        }


                    }

                    d("dcsjhkdf", playersList.toString())
                    playersAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    view.snackBar("Internet Connection ERROR")
                }
            })


    }


    private fun fetchCurrentUser(onlinePlayerName:EditText) {
        val uid = auth.currentUser!!.uid
        val ref = FirebaseDatabase.getInstance().getReference("Players/$uid")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser = snapshot.getValue(PlayerModel::class.java)

                d("sdas", currentUser.toString())
                 onlinePlayerName.text =currentUser?.name?.toEditable()
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