package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.alphabetizedSort
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.snackBar
import com.example.tic_tac_toe_kotlin_multiplayer_game.ui.newgame.Move
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

    private val reference = database.reference

    companion object {
        var gameSessionID = "null"
        var myRef = Firebase.database.reference
        var myRefGameTurn = Firebase.database.reference
        var onlinePlayerUID = "null"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_game_mode)


        val extras = intent.extras
        onlinePlayerUID = extras?.getString("onlinePlayerUID", "null").toString()
        d("dfsdf", onlinePlayerUID.toString())
        gameSessionID = (onlinePlayerUID + uID).alphabetizedSort()
        d("dfsdf", gameSessionID)
        myRef = database.getReference("Players_GameSessions/$gameSessionID")
        myRefGameTurn = database.getReference("Players_GameSessions_Turn/$gameSessionID")

        dataUpdate(human, 1)

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
                initButton(row, column, myRef)
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

    private fun initButton(row: Int, column: Int, myRef: DatabaseReference): ImageButton {
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
                d("ddfsfdfEvent", snapshot.key.toString())
                val uid = snapshot.value.toString()
                d("ddfsxcfdfEvent", uid.toString())


                if (uid != uID) {
                    //   findViewById<LinearLayout>(R.id.gameBoard).isClickable = false


                    if (human == "O")
                        imageBtn.setImageResource(R.mipmap.toe_o)
                    else
                        imageBtn.setImageResource(R.mipmap.toe_x)
                    imageBtn.isClickable = false
                    checkButtonList[row][column] = human
                    d("list", checkButtonList.toString())
                    myRef.setValue(checkButtonList)


                    if (playerCount == 8) {
                        playerCount++
                    } else {
                        playerCount += 2
                    }

                    if (playerCount == 9) {
                        draw()
                    }

                    myRefGameTurn.setValue(uID)

                } else if (playerCount == 2) {
                    // findViewById<LinearLayout>(R.id.gameBoard).isClickable = true
                    onlinePlayer = "O"
                    human = "X"

//                    myRefGameTurn.setValue(onlinePlayerUID)
//                    imageBtn.setImageResource(R.mipmap.toe_o)
//                    imageBtn.isClickable = false
//                    checkButtonList[row][column] = onlinePlayer
//                    d("list", checkButtonList.toString())

                    //  dataUpdate(onlinePlayer,2)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                d("dsfEvent", error.toString())
            }
        })


    }

    private fun bestMove(myRef: DatabaseReference) {

        val move: Move = Move(0, 0)
        Array(3) { row ->
            Array(3) { column ->
                if (checkButtonList[row][column] == " ") {
                    checkButtonList[row][column] = onlinePlayer
                    checkButtonList[row][column] = " "
                    move.row = row
                    move.column = column

                }
            }
        }
        imageButtons[move.row][move.column].setImageResource(R.mipmap.toe_x)
        checkButtonList[move.row][move.column] = onlinePlayer
        myRef.setValue(checkButtonList)

    }


    private fun win(player: Int) {
        val winner: String = if (player == 1) {
            playerFirstPoints++
            "You won"
        } else {
            playerSecondPoints++
            "Winner is Android"
        }
        androidScore.snackBar(winner)
        Array(3) { row ->
            Array(3) { column ->
                imageButtons[row][column].isClickable = false
            }
        }
        updateScore()
        myRef.setValue(checkButtonList)
    }

    private fun updateScore() {
        youScore.text = playerFirstPoints.toString()
        androidScore.text = playerSecondPoints.toString()


    }

    private fun draw() {
        androidScore.snackBar("winner is friendship")
    }

    private fun clearBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                imageButtons[i][j].setImageResource(0)
            }
        }
        playerCount = 0
        checkButtonList.clear()
        checkButtonList = MutableList(3) {
            MutableList(3) {
                getEmptyString()
            }
        }
        myRef.setValue(checkButtonList)
    }


    private fun checkForAiWin(): String {
        var winner = " ";

        for (i in 0..2) {
            if (
                (checkButtonList[i][0] == checkButtonList[i][1]) &&
                (checkButtonList[i][0] == checkButtonList[i][2]) &&
                (checkButtonList[i][0] != " ")
            ) winner = checkButtonList[i][0]
        }

        for (i in 0..2) {
            if (
                (checkButtonList[0][i] == checkButtonList[1][i]) &&
                (checkButtonList[0][i] == checkButtonList[2][i]) &&
                (checkButtonList[0][i] != " ")
            ) winner = checkButtonList[0][i]
        }

        if (
            (checkButtonList[0][0] == checkButtonList[1][1]) &&
            (checkButtonList[0][0] == checkButtonList[2][2]) &&
            (checkButtonList[0][0] != " ")
        ) winner = checkButtonList[0][0]

        if (
            (checkButtonList[0][2] == checkButtonList[1][1]) &&
            (checkButtonList[0][2] == checkButtonList[2][0]) &&
            (checkButtonList[0][2] != " ")
        ) winner = checkButtonList[0][2]

        var openSpots: Int = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (checkButtonList[i][j] == " ") {
                    openSpots++
                }
            }
        }
        return if (winner == " " && openSpots == 0) {
            "tie";
        } else {
            winner;
        }

    }


    private fun dataUpdate(PlayersToe: String, player: Int) {
        val ref = FirebaseDatabase.getInstance().getReference("Players_GameSessions/$gameSessionID")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                d("ddfsfdfEvent", snapshot.key.toString())
                val firebaseOnlinePlayer = snapshot.value as MutableList<MutableList<String>>
                d("ddfsxcfccdfEvent", firebaseOnlinePlayer.toString())


                Array(3) { row ->
                    Array(3) { column ->
                        val firebaseToe = firebaseOnlinePlayer[row][column]
                        if (checkButtonList[row][column] != firebaseToe && firebaseToe != " ") {
                            // checkButtonList = firebaseOnlinePlayer
                            d("sfdsd", checkButtonList[row][column] + "<>" + firebaseToe)
                            if (firebaseToe == "O" || firebaseToe == "X") {
                                if (firebaseToe == "O") {
                                    d(
                                        "sfcvcvdsd",
                                        checkButtonList[row][column] + "<>" + firebaseToe
                                    )
                                    imageButtons[row][column].setImageResource(R.mipmap.toe_o)
                                } else
                                    imageButtons[row][column].setImageResource(R.mipmap.toe_x)

                                imageButtons[row][column].isClickable = false
                                checkButtonList[row][column] = firebaseToe
                                playerCount++

                            }


//                             else {
////                                imageButtons[row][column].isClickable = true
////                                checkButtonList[row][column] = " "
//                            }
                        }
                    }
                }


                if (checkForAiWin() == PlayersToe) {
                    win(player)
                    return
                }
            }

            override fun onCancelled(error: DatabaseError) {
                d("dsfEvent", error.toString())
            }
        })

    }
}