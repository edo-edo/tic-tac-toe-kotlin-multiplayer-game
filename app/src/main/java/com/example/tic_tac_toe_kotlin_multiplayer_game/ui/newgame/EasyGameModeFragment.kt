package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.newgame

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.myCustomSnackbar
import com.example.tic_tac_toe_kotlin_multiplayer_game.tools.checkForWinner
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EasyGameModeFragment : Fragment(R.layout.fragment_first) {

    private lateinit var imageButtons: Array<Array<ImageButton>>
    private lateinit var youScore: TextView
    private lateinit var androidScore: TextView
    private lateinit var sharedPref: SharedPreferences

    private val emptyImageButtons: MutableList<ImageButton> = ArrayList()
    private var checkButtonList: MutableList<MutableList<String>> = ArrayList()
    private var playerCount: Int = 0
    private val cross = "X"
    private val zero = "O"
    private var human: Int = 0
    private var android: Int = 0
    private var playerFirstPoints: Int = 0
    private var playerSecondPoints: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        youScore = view.findViewById(R.id.you_score)
        androidScore = view.findViewById(R.id.android_score)
        sharedPref =
            activity?.getSharedPreferences(getString(R.string.themes), Context.MODE_PRIVATE)!!
        human = sharedPref.getInt(R.string.first_logo.toString(), R.mipmap.tic_01)
        android = sharedPref.getInt(R.string.second_logo.toString(), R.mipmap.tic_06)


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

    private fun getEmptyString(): String {
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

        imageBtn.setImageResource(human)
        emptyImageButtons.remove(imageBtn)
        checkButtonList[row][column] = cross
        if (checkForWinner(checkButtonList)  == cross) {
            win(1)
            return
        }

        if (emptyImageButtons.size > 0) {
            val button = emptyImageButtons.random()
            button.setImageResource(android)
            Array(3) { row1 ->
                Array(3) { column1 ->
                    if (imageButtons[row1][column1] == button) {
                        checkButtonList[row1][column1] = zero
                    }
                }
            }

            emptyImageButtons.remove(button)
        }

        if (checkForWinner(checkButtonList) == zero) {
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

    private fun win(player: Int) {
        val winner: String = if (player == 1) {
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
        checkButtonList = MutableList(3) {
            MutableList(3) {
                getEmptyString()
            }
        }

    }

}