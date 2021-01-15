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

    private lateinit var fistPlayerName: String
    private lateinit var secondPlayerName: String


    private lateinit var imageButtons: Array<Array<ImageButton>>
    private lateinit var playerFirst: TextView
    private lateinit var playerSecond: TextView
    private lateinit var playerFirstScore: TextView
    private lateinit var playerSecondScore: TextView

    private val emptyImageButtons : MutableList<ImageButton> = ArrayList()
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
            Log.d("buttons", emptyImageButtons.toString())
            if (checkForWin()){
                win(1)
            }

            var index = 0
            if (emptyImageButtons.size != 1){
                index =  (0 until emptyImageButtons.size).random()
            }
            emptyImageButtons[index].setImageResource(R.mipmap.toe_o)
            if (checkForWin()){
                win(2)
            }

        if (playerCount == 8){
            playerCount++
        }else{
            playerCount += 2
        }


//        if (checkForWin()) {
//            if (playerTurn) win(1) else win(2)
//        } else if (playerCount == 9) {
//            draw()
//        }
    }

    private fun win(player: Int) {
        val winner:String = if (player == 1) {
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
        emptyImageButtons.clear()

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