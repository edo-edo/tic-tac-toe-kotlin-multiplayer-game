package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
    private var nonActivePlayersList = mutableListOf<PlayerModel>()

    private lateinit var playersAdapter: OnlinePlayersListAdapter


    private var auth = FirebaseAuth.getInstance()

    // auth.currentUser?.uid
    private val onlineUser = auth.currentUser
    private val uID = onlineUser?.uid
    private val currentEmail = onlineUser?.email

    // Write a message to the database
    private val database = Firebase.database

    private val reference = database.reference

    private val myRef = database.getReference("Players/$uID")
    private val myRefActiveStatus = database.getReference("Players_ActiveStatus")


    private var currentUserName: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        activity?.title = ""

        val onlinePlayerName = view.findViewById<EditText>(R.id.online_player_name)
        val saveButton = view.findViewById<Button>(R.id.save_button)





        view.findViewById<RecyclerView>(R.id.online_Players_RecyclerView).apply {
            playersAdapter = OnlinePlayersListAdapter(playersList,
                object : ItemClickListener {
                    override fun viewClicked(onlineUID: String?) {

//                        playersList.removeAt(position)
//                        playersAdapter.notifyItemRemoved(position)

                        val intent = Intent(context, OnlineGameModeActivity::class.java)
                        intent.putExtra("onlinePlayerUID", onlineUID);
                        startActivity(intent)
                        activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }

                })
            layoutManager = LinearLayoutManager(context)
            adapter = playersAdapter
        }
        activePlayers(database, view)
        fetchCurrentUser(onlinePlayerName)
        d("sjhds", activePlayers(database, view).toString())

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

        view.dataUpdate()

        getInstance()?.saveUser(
            PlayerModel(
                uID,
                currentEmail,
                currentUserName,
                true
            )
        )
    }
    private fun View.dataUpdate(){
        val ref = FirebaseDatabase.getInstance().getReference("Players")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                activePlayers(database, this@dataUpdate)
            }

            override fun onCancelled(error: DatabaseError) {
                d("dsfEvent",error.toString())
            }
        })

    }

    private fun fetchCurrentUser(onlinePlayerName: EditText) {
        val uid = auth.currentUser!!.uid
        val ref = FirebaseDatabase.getInstance().getReference("Players/$uid")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser = snapshot.getValue(PlayerModel::class.java)

                d("sdas", currentUser.toString())
                currentUserName = currentUser?.name.toString()
                onlinePlayerName.text = currentUserName.toEditable()

//                myRef.setValue(
//                    PlayerModel(
//                        uID,
//                        currentEmail,
//                        currentUserName,
//                        true
//                    )
//                )




            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun activePlayers(database: FirebaseDatabase, view: View) {
        val uid = auth.currentUser!!.uid
        playersList.clear()
        nonActivePlayersList.clear()
        database.reference.child("Players")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val map = (dataSnapshot.value as Map<*, *>?)
                    playersList.clear()
                    nonActivePlayersList.clear()
                    d("fdfdvd", dataSnapshot.toString())


                    map?.forEach {
                        val player = it.value as Map<*, *>?

                        d("activeStatusX", player?.get("activeStatus").toString())
                        d("activeStatusX", player?.get("email").toString())
                        d("activeStatusX", player?.get("name").toString())
                        d("activeStatusX", player?.get("uid").toString())

                        d("fdfvmapcgfdfgfgdfgddwd", it.key.toString())
                        d("fdfvmapcfdfgfddwd", it.value.toString())

                        if (player?.get("uid") == uid || player?.get("name") == null || player["name"] == "") {
                            return@forEach
                        }

                        if (player?.get("activeStatus") == true) {
                            playersList.add(
                                PlayerModel(
                                    player["uid"].toString(),
                                    player["email"].toString(),
                                    player["name"].toString(),
                                    true
                                )
                            )
                        } else {
                            nonActivePlayersList.add(
                                PlayerModel(
                                    player?.get("uid").toString(),
                                    player?.get("email").toString(),
                                    player?.get("name").toString(),
                                    false
                                )
                            )
                        }


                    }
                    playersList.addAll(nonActivePlayersList)
                    d("dcsjhkdf", playersList.toString())
                    playersAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    view.snackBar("Internet Connection ERROR")
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

    override fun onPause() {
        super.onPause()
        playerActiveStatus(false)
    }

    override fun onResume() {
        super.onResume()
        playerActiveStatus(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        playerActiveStatus(false)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.log_out_button, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.user_log_out -> {
                getInstance()?.logOut()
                d("dfsfsgfdgfdf---->", getInstance()?.isLoggedIn.toString())
                playerActiveStatus(false)
                findNavController().navigate(R.id.action_StarGameOnlineMultiPlayerFragment_to_LogInToOnlineGameFragment)
                true
            }

            else -> false
        }

    private fun playerActiveStatus(isActive:Boolean){
        val myNewRef = database.getReference("Players/$uID/activeStatus")
        myNewRef.setValue(isActive)
    }


}