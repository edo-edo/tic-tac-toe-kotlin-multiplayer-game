package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.newgame

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.myCustomSnackbar
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(R.layout.fragment_second) {


    private lateinit var imageButtons: Array<Array<ImageButton>>
    private lateinit var youScore: TextView
    private lateinit var androidScore: TextView

    private var checkButtonList : MutableList<MutableList<String>> = ArrayList()
    private var playerCount: Int = 0
    private var playerFirstPoints: Int = 0
    private var playerSecondPoints: Int = 0
    private  var ai = "X"
    private  var human = "O"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        youScore = view.findViewById(R.id.you_score)
        androidScore = view.findViewById(R.id.android_score)


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
        checkButtonList = MutableList(3) {
            MutableList(3) {
                getEmptyString()
            }
        }


    }
    private fun getEmptyString():String{
        return " "
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
            onButtonClick(imageBtn, row, column)
        }
        return imageBtn
    }

    private fun onButtonClick(imageBtn: ImageButton, row: Int, column: Int) {
        if (imageBtn.drawable != null) return

        imageBtn.setImageResource(R.mipmap.toe_o)
        checkButtonList[row][column] = human
        Log.d("list", checkButtonList.toString())
        if (checkForWin()){
            win(1)
            return
        }
            bestMove()
        if (checkForWin()){
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

    private fun bestMove(){
        var bestScore = -100
        val move:Move = Move(0,0)
        Array(3) { row ->
            Array(3) { column ->
                if (checkButtonList[row][column] == " "){
                    checkButtonList[row][column] = ai
                    val score = minMax(0, false)
                    checkButtonList[row][column] = " "
                    if (score > bestScore){
                        bestScore = score
                        move.row = row
                        move.column = column
                    }
                }
            }
        }
        imageButtons[move.row][move.column].setImageResource(R.mipmap.toe_x)
        checkButtonList[move.row][move.column] = ai
    }

    private fun minMax(depth: Int, isMaximizing: Boolean):Int{
        when {
            (checkForAiWin() == ai && checkForAiWin() != " ") -> {
                return 10
            }
            (checkForAiWin() == human && checkForAiWin() != " ") -> {
                return -10
            }
            (checkForAiWin() == "tie" && checkForAiWin() != " ") -> {
                return 0
            }
            else -> {
                if (isMaximizing){
                    var bestScore = -100

                    Array(3) { row ->
                        Array(3) { column ->
                            if (checkButtonList[row][column] == " ") {
                                checkButtonList[row][column] = ai
                                val score = minMax(depth + 1, false)
                                checkButtonList[row][column] = " "
                                if (score > bestScore){
                                    bestScore = score
                                }

                            }
                        }
                    }
                    return bestScore
                } else {
                    var bestScore = 100

                    Array(3) { row ->
                        Array(3) { column ->
                            if (checkButtonList[row][column] == " ") {
                                checkButtonList[row][column] = human
                                val score = minMax(depth + 1, true)
                                checkButtonList[row][column] = " "
                                if (score < bestScore){
                                    bestScore = score
                                }
                            }
                        }
                    }
                    return bestScore
                }

            }
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
        checkButtonList.clear()
        checkButtonList =  MutableList(3) {
            MutableList(3) {
               getEmptyString()
            }
        }
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
}