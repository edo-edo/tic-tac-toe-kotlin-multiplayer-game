package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.ImageButton
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
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_game_mode)


        val extras = intent.extras
        val onlinePlayerUID = extras?.getString("onlinePlayerUID", "null")
        d("dfsdf", onlinePlayerUID.toString())
        gameSessionID = (onlinePlayerUID + uID).alphabetizedSort()
        d("dfsdf", gameSessionID)
        myRef = database.getReference("Players_GameSessions/$gameSessionID")
        dataUpdate()

        youScore = findViewById(R.id.you_score)
        androidScore = findViewById(R.id.android_score)


        findViewById<Button>(R.id.offline_try_again).setOnClickListener {
            clearBoard(myRef)
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
            onButtonClick(myRef, imageBtn, row, column)
        }
        return imageBtn
    }

    private fun onButtonClick(
        myRef: DatabaseReference,
        imageBtn: ImageButton,
        row: Int,
        column: Int
    ) {
        if (imageBtn.drawable != null) return

        imageBtn.setImageResource(R.mipmap.toe_o)
        checkButtonList[row][column] = human
        d("list", checkButtonList.toString())
        if (checkForWin()) {
            win(1)
            return
        }
        bestMove(myRef)
        if (checkForWin()) {
            win(2)
            return
        }

        if (playerCount == 8) {
            playerCount++
        } else {
            playerCount += 2
        }

        if (playerCount == 9) {
            draw()
        }
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

    }

    private fun updateScore() {
        youScore.text = playerFirstPoints.toString()
        androidScore.text = playerSecondPoints.toString()


    }

    private fun draw() {
        androidScore.snackBar("winner is friendship")
    }

    private fun clearBoard(myRef: DatabaseReference) {
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


    private fun checkForWin(): Boolean {
        val fields = Array(3) { row ->
            Array(3) { column ->
                getField(imageButtons[row][column])
            }

        }
        for (i in 0..2) {
            if (
                (fields[i][0] == fields[i][1]) &&
                (fields[i][0] == fields[i][2]) &&
                (fields[i][0] != null)
            ) return true
        }

        for (i in 0..2) {
            if (
                (fields[0][i] == fields[1][i]) &&
                (fields[0][i] == fields[2][i]) &&
                (fields[0][i] != null)
            ) return true
        }

        if (
            (fields[0][0] == fields[1][1]) &&
            (fields[0][0] == fields[2][2]) &&
            (fields[0][0] != null)
        ) return true

        if (
            (fields[0][2] == fields[1][1]) &&
            (fields[0][2] == fields[2][0]) &&
            (fields[0][2] != null)
        ) return true

        return false

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

    private fun getField(imageButton: ImageButton): Char? {
        val drw: Drawable? = imageButton.drawable
        val drwCross = ResourcesCompat.getDrawable(resources, R.mipmap.toe_x, null)
        val drwZero = ResourcesCompat.getDrawable(resources, R.mipmap.toe_o, null)

        return when (drw?.constantState) {
            drwCross?.constantState -> 'X'
            drwZero?.constantState -> '0'
            else -> null
        }

    }


    private fun dataUpdate() {
        val ref = FirebaseDatabase.getInstance().getReference("Players_GameSessions/$gameSessionID")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                d("ddfsfdfEvent", snapshot.key.toString())
               val sdfhsf = snapshot.value as MutableList<*>
                d("ddfsxcfdfEvent", sdfhsf.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                d("dsfEvent", error.toString())
            }
        })

    }
}