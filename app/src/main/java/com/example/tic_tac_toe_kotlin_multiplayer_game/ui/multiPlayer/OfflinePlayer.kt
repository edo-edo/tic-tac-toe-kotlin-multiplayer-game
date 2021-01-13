package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass is class which handle two player game on one phone.
 */
class OfflinePlayer : Fragment(R.layout.fragment_offline_player) {
    private lateinit var fistPlayerName: String
    private lateinit var secondPlayerName: String


    private lateinit var imageButtons: Array<Array<ImageButton>>
    private lateinit var playerFirst: TextView
    private lateinit var playerSecond: TextView

    private var playerTurn: Boolean = true
    private var playerCount: Int = 0
    private var playerFirstPoints: Int = 0
    private var playerSecondPoints: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        fistPlayerName = bundle?.getString("fistPlayerName") ?: "First"
        secondPlayerName = bundle?.getString("secondPlayerName") ?: "Second"

        playerFirst = view.findViewById(R.id.offline_player_first)
        playerSecond = view.findViewById(R.id.offline_player_second)

        playerFirst.text = fistPlayerName
        playerSecond.text = secondPlayerName

        view.findViewById<Button>(R.id.offline_try_again).setOnClickListener {
            clearBoard()
        }

        imageButtons = Array(3) { row ->
            Array(3) { column ->
                initButton(row, column, view)
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
        if (playerTurn) {
            imageBtn.setImageResource(R.mipmap.toe_x)
        } else {
            imageBtn.setImageResource(R.mipmap.toe_o)
        }
        playerCount++

        if (checkForWin()) {
            if (playerTurn) win(1) else win(2)
        } else if (playerCount == 9) {
            draw()
        } else {
            playerTurn = !playerTurn
        }
    }

    private fun win(player: Int) {
        if (player == 1) playerFirstPoints++ else playerSecondPoints++
        view?.let {
            Snackbar.make(it, player.toString(), Snackbar.LENGTH_LONG).show()
        }
        updateScore()

    }

    @SuppressLint("SetTextI18n")
    private fun updateScore() {
        playerFirst.text = "$fistPlayerName: $playerFirstPoints"
        playerSecond.text = "$secondPlayerName: $playerSecondPoints"

    }

    private fun draw() {
        view?.let {
            Snackbar.make(it, "draw", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                imageButtons[i][j].setImageResource(0)
            }
        }
        playerCount = 0
        playerTurn = true

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