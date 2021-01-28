package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.newgame

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.myCustomSnackbar
import java.util.*
import kotlin.random.Random.Default.nextInt

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var imageButtons: Array<Array<ImageButton>>
    private lateinit var youScore: TextView
    private lateinit var androidScore: TextView
    private lateinit var sharedPref: SharedPreferences

    private val emptyImageButtons : MutableList<ImageButton> = ArrayList()
    private var checkButtonList : MutableList<MutableList<String>> = ArrayList()
    private var playerCount: Int = 0
    private var human: Int = 0
    private var playerFirstPoints: Int = 0
    private var playerSecondPoints: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        youScore = view.findViewById(R.id.you_score)
        androidScore = view.findViewById(R.id.android_score)
        sharedPref = activity?.getSharedPreferences(
            getString(R.string.themes), Context.MODE_PRIVATE)!!

        val highScore = sharedPref.getString(R.string.active_logo.toString(), "tac_00")
                when (highScore.toString()) {
                    "tac_00" -> humanIcon(R.mipmap.tic_01)
                    "tac_01" -> humanIcon(R.mipmap.tic_02)
                    "tac_02" -> humanIcon(R.mipmap.tic_03)
                    "tac_10" -> humanIcon(R.mipmap.tic_04)
                    "tac_11" -> humanIcon(R.mipmap.tic_05)
                    else -> {
                        humanIcon(R.mipmap.tic_06)
                    }
                }

        view.findViewById<Button>(R.id.offline_try_again).setOnClickListener {
            clearBoard()
            Array(3) { row ->
                Array(3) { column ->
                    imageButtons[row][column].isClickable = true
                }
            }
        }

        imageButtons = Array(3) { row ->
            Array(3) { column ->
                initButton(row, column, view)
            }
        }
        Array(3) { row ->
            Array(3) { column ->
                emptyImageButtons.add(imageButtons[row][column])
            }
        }
        checkButtonList = MutableList(3) {
            MutableList(3) {
                getEmptyString()
            }
        }



    }

    private fun getEmptyString():String{
        return " "
    }

    private fun humanIcon(logo: Int) {
        human = logo

    }

    private fun initButton(row: Int, column: Int, view: View): ImageButton {
        val imageBtn: ImageButton = view.findViewById(
            resources.getIdentifier(
                "offline_button_$row$column",
                "id",
                activity?.packageName
            )
        )
        imageBtn.setOnClickListener {
            onButtonClick(imageBtn,row, column)
        }
        return imageBtn
    }

    private fun onButtonClick(imageBtn: ImageButton, row: Int, column: Int) {
        if (imageBtn.drawable != null) return

            imageBtn.setImageResource(human)
            emptyImageButtons.remove(imageBtn)
            checkButtonList[row][column] = "X"
            if (checkForWin() == "X"){
                win(1)
                return
            }

            if (emptyImageButtons.size > 0){
                val button =  emptyImageButtons.random()
                button.setImageResource(R.mipmap.toe_o)
                Array(3) { row1 ->
                    Array(3) { column1 ->
                       if( imageButtons[row1][column1] == button){
                           checkButtonList[row1][column1] = "O"
                       }
                    }
                }

                emptyImageButtons.remove(button)
            }

            if (checkForWin() == "O"){
                win(2)
                return
            }

        if (playerCount == 8){
            playerCount++
        }else{
            playerCount += 2
        }

        if (playerCount == 9) {
            draw()
        }
    }

    private fun win(player: Int) {
        val winner:String = if (player == 1) {
            playerFirstPoints++
            "You won"
        } else {
            playerSecondPoints++
            "Winner is Android"
        }
        view?.myCustomSnackbar(winner, R.id.first_row_button)
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
        view?.myCustomSnackbar("winner is friendship", R.id.first_row_button)
    }

    private fun clearBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                imageButtons[i][j].setImageResource(0)
            }
        }
        playerCount = 0
        emptyImageButtons.clear()
        Array(3) { row ->
            Array(3) { column ->
                emptyImageButtons.add(imageButtons[row][column])
            }
        }
        checkButtonList.clear()
        checkButtonList =  MutableList(3) {
            MutableList(3) {
                getEmptyString()
            }
        }

    }


    private fun checkForWin(): String {
        var winner = " ";
        Log.d("arr",checkButtonList.toString())

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

        var openSpots:Int = 0
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

}