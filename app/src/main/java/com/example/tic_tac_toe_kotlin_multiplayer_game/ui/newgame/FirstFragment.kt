package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.newgame

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

    private val emptyImageButtons : MutableList<ImageButton> = ArrayList()
    private var playerCount: Int = 0
    private var playerFirstPoints: Int = 0
    private var playerSecondPoints: Int = 0


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
        Array(3) { row ->
            Array(3) { column ->
                emptyImageButtons.add(imageButtons[row][column])
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
            onButtonClick(imageBtn)
        }
        return imageBtn
    }

    private fun onButtonClick(imageBtn: ImageButton) {
        if (imageBtn.drawable != null) return

            imageBtn.setImageResource(R.mipmap.toe_x)
            emptyImageButtons.remove(imageBtn)
            if (checkForWin()){
                win(1)
                return
            }

            if (emptyImageButtons.size > 0){
                val index =  (0 until emptyImageButtons.size).random()
                emptyImageButtons[index].setImageResource(R.mipmap.toe_o)
                emptyImageButtons.removeAt(index)
            }

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

    private fun getField(imageButton: ImageButton): Char? {
        val drw: Drawable? = imageButton.drawable
        val drwCross = ResourcesCompat.getDrawable(resources, R.mipmap.toe_x, null)
        val drwZero = ResourcesCompat.getDrawable(resources, R.mipmap.toe_o, null)

        return when (drw?.constantState) {
            drwCross?.constantState -> 'x'
            drwZero?.constantState -> 'o'
            else -> null
        }

    }
}