package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.local

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
import java.util.ArrayList

/**
 * A simple [Fragment] subclass is class which handle two player game on one phone.
 */
class OfflinePlayerFragment : Fragment(R.layout.fragment_offline_player) {
    private lateinit var fistPlayerName: String
    private lateinit var secondPlayerName: String
    private lateinit var imageButtons: Array<Array<ImageButton>>
    private lateinit var playerFirst: TextView
    private lateinit var playerSecond: TextView
    private lateinit var playerFirstScore: TextView
    private lateinit var playerSecondScore: TextView
    private lateinit var sharedPref: SharedPreferences

    private var checkButtonList: MutableList<MutableList<String>> = ArrayList()
    private val cross = "X"
    private val zero = "O"
    private var firstPlayerIcon: Int = 0
    private var secondPlayerIcon: Int = 0
    private var playerTurn: Boolean = true
    private var playerCount: Int = 0
    private var playerFirstPoints: Int = 0
    private var playerSecondPoints: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        fistPlayerName = bundle?.getString("fistPlayerName") ?: "First"
        secondPlayerName = bundle?.getString("secondPlayerName") ?: "Second"
        sharedPref =
            activity?.getSharedPreferences(getString(R.string.themes), Context.MODE_PRIVATE)!!
        firstPlayerIcon = sharedPref.getInt(R.string.first_logo.toString(), R.mipmap.tic_01)
        secondPlayerIcon = sharedPref.getInt(R.string.second_logo.toString(), R.mipmap.tic_06)

        playerFirst = view.findViewById(R.id.offline_player_first)
        playerSecond = view.findViewById(R.id.offline_player_second)
        playerFirstScore = view.findViewById(R.id.offline_player_first_score)
        playerSecondScore = view.findViewById(R.id.offline_player_second_score)

        playerFirst.text = fistPlayerName
        playerSecond.text = secondPlayerName

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
        if (playerTurn) {
            imageBtn.setImageResource(firstPlayerIcon)
            checkButtonList[row][column] = cross
            if (checkForWinner(checkButtonList) == cross) {
                win(1)
                return
            }

        } else {
            imageBtn.setImageResource(secondPlayerIcon)
            checkButtonList[row][column] = zero
            if (checkForWinner(checkButtonList) == zero) {
                win(2)
                return
            }

        }
        playerCount++
        if (playerCount == 9) {
            draw()
        } else {
            playerTurn = !playerTurn
        }
    }

    private fun win(player: Int) {
        val winner: String = if (player == 1) {
            playerFirstPoints++
            "Winner is $fistPlayerName"
        } else {
            playerSecondPoints++
            "Winner is $secondPlayerName"
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
        playerFirstScore.text = playerFirstPoints.toString()
        playerSecondScore.text = playerSecondPoints.toString()


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
        playerTurn = true
        checkButtonList.clear()
        checkButtonList = MutableList(3) {
            MutableList(3) {
                getEmptyString()
            }
        }

    }

    private fun getEmptyString(): String {
        return " "
    }
}