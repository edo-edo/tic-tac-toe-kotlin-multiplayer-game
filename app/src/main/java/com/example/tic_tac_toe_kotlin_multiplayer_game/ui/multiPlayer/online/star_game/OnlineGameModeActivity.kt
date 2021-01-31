package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game

import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.alphabetizedSort
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.snackBar
import com.example.tic_tac_toe_kotlin_multiplayer_game.tools.checkForWinner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class OnlineGameModeActivity : AppCompatActivity() {

    private lateinit var imageButtons: Array<Array<ImageButton>>

    private lateinit var youScore: TextView
    private lateinit var androidScore: TextView


    private var checkButtonList: MutableList<MutableList<String>> = ArrayList()
    private var playerCount: Int = 0
    private var playerFirstPoints: Int = 0
    private var playerSecondPoints: Int = 0
    private var onlinePlayer = "X"
    private var human = "O"

    private var auth = FirebaseAuth.getInstance()

    // auth.currentUser?.uid
    private val onlineUser = auth.currentUser
    private val uID = onlineUser?.uid

    // Write a message to the database
    private val database = Firebase.database


    companion object {
        var gameSessionID = "null"
        var myRef = Firebase.database.reference
        var myRefGameTurn = Firebase.database.reference
        var myRefGameTurnPlayersToes = Firebase.database.reference
        var onlinePlayerUID = "null"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_game_mode)


        val extras = intent.extras
        onlinePlayerUID = extras?.getString("onlinePlayerUID", "null").toString()
        gameSessionID = (onlinePlayerUID + uID).alphabetizedSort()

        myRef = database.getReference("Players_GameSessions/$gameSessionID")
        myRefGameTurn = database.getReference("Players_GameSessions_Turn/$gameSessionID")
        myRefGameTurnPlayersToes =
            database.getReference("Players_GameSessions_Turn_Players_Toes/$gameSessionID")


        if (uID != null) {
            setPlayersNames(findViewById(R.id.onlinePlayer_textView), onlinePlayerUID)
        }

        dataUpdate()

        youScore = findViewById(R.id.you_score)
        androidScore = findViewById(R.id.android_score)



        findViewById<Button>(R.id.offline_try_again).setOnClickListener {
            clearBoard()
            Array(3) { row ->
                Array(3) { column ->
                    imageButtons[row][column].isClickable = true
                }
            }
        }

        imageButtons = Array(3) { row ->
            Array(3) { column ->
                initButton(row, column)
            }
        }
        checkButtonList = MutableList(3) {
            MutableList(3) {
                getEmptyString()
            }
        }

        myRef.setValue(checkButtonList)
    }

    private fun getEmptyString(): String {
        return " "
    }

    private fun initButton(row: Int, column: Int): ImageButton {
        val imageBtn: ImageButton = findViewById(
            resources.getIdentifier(
                "offline_button_$row$column",
                "id",
                packageName
            )
        )
        imageBtn.setOnClickListener {
            onButtonClick(imageBtn, row, column)
        }
        return imageBtn
    }

    private fun onButtonClick(
        imageBtn: ImageButton,
        row: Int,
        column: Int
    ) {
        if (imageBtn.drawable != null) return


        val refGameTurn =
            FirebaseDatabase.getInstance().getReference("Players_GameSessions_Turn/$gameSessionID")
        refGameTurn.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val uid = snapshot.value.toString()


                if (uid != uID) {
                    if (playerCount < 1) {
                        onlinePlayer = "O"
                        human = "X"
                        val toes = mapOf(uID to human, onlinePlayerUID to onlinePlayer)
                        myRefGameTurnPlayersToes.setValue(toes)

                    }

                    toeUpdate()

                    if (human == "O")
                        imageBtn.setImageResource(R.mipmap.tic_06)
                    else
                        imageBtn.setImageResource(R.mipmap.tic_03)

                    imageBtn.isClickable = false
                    checkButtonList[row][column] = human



                    myRef.setValue(checkButtonList)
                    myRefGameTurn.setValue(uID)
                } else {
                    playerCount++
                }

                if (checkForWinner(checkButtonList) == human) {
                    win(1)
                }
                if (checkForWinner(checkButtonList) == "tie") {
                    draw()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                d("dsfEvent", error.toString())
            }
        })
    }

    private fun win(player: Int) {
        val winner: String = if (player == 1) {
            playerFirstPoints++
            "You won"
        } else {
            playerSecondPoints++
            "You Are loser"
        }
        androidScore.snackBar(winner)
        Array(3) { row ->
            Array(3) { column ->
                imageButtons[row][column].isClickable = false
            }
        }
        updateScore()

    }

    private fun updateScore() {
        youScore.text = playerFirstPoints.toString()
        androidScore.text = playerSecondPoints.toString()


    }

    private fun draw() {
        androidScore.snackBar("winner is friendship")
        Array(3) { row ->
            Array(3) { column ->
                imageButtons[row][column].isClickable = false
            }
        }
    }

    private fun clearBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                imageButtons[i][j].setImageResource(0)
            }
        }

        checkButtonList.clear()
        checkButtonList = MutableList(3) {
            MutableList(3) {
                getEmptyString()
            }
        }
        myRef.setValue(checkButtonList)
    }

    private fun dataUpdate() {
        val ref = FirebaseDatabase.getInstance().getReference("Players_GameSessions/$gameSessionID")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val firebaseOnlinePlayer = snapshot.value as MutableList<MutableList<String>>




                Array(3) { row ->
                    Array(3) { column ->
                        val firebaseToe = firebaseOnlinePlayer[row][column]
                        if (checkButtonList[row][column] != firebaseToe && firebaseToe != " ") {

                            if (firebaseToe == "O" || firebaseToe == "X") {
                                if (firebaseToe == "O") {
                                    imageButtons[row][column].setImageResource(R.mipmap.tic_06)
                                } else
                                    imageButtons[row][column].setImageResource(R.mipmap.tic_03)

                                imageButtons[row][column].isClickable = false
                                checkButtonList[row][column] = firebaseToe
                                playerCount++

                            }
                        }
                    }
                }

                if (checkForWinner(checkButtonList) == "tie") {
                    draw()
                }

                if (checkForWinner(checkButtonList) == onlinePlayer) {
                    win(2)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                d("dsfEvent", error.toString())
            }
        })

    }

    fun toeUpdate() {
        val refGameTurn =
            FirebaseDatabase.getInstance()
                .getReference("Players_GameSessions_Turn_Players_Toes/$gameSessionID")
        refGameTurn.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val uid = snapshot.value as Map<String, String>

                onlinePlayer = uid[onlinePlayerUID].toString()
                human = uid[uID].toString()

            }

            override fun onCancelled(error: DatabaseError) {
                d("dsfEvent", error.toString())
            }
        })
    }

    private fun setPlayersNames(textView: TextView, uid: String) {
        val refGameTurn =
            FirebaseDatabase.getInstance().getReference("Players/$uid/name")
        refGameTurn.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.value.toString()
                textView.text = name


            }

            override fun onCancelled(error: DatabaseError) {
                d("dsfEvent", error.toString())
            }
        })
    }
}