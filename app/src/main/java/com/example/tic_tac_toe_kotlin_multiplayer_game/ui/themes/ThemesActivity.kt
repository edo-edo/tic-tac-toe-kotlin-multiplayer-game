package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.themes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.RootApp


class ThemesActivity : AppCompatActivity() {
    private lateinit var imageFirstButtons: Array<Array<ImageButton>>
    private lateinit var imageSecondButtons: Array<Array<ImageButton>>
    private lateinit var firstSelectedButton: ImageButton
    private lateinit var secondSelectedButton: ImageButton
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)
        sharedPref = getSharedPreferences(getString(R.string.themes), Context.MODE_PRIVATE)
        val firstSavedLogo = sharedPref.getInt(R.string.first_logo.toString(), R.mipmap.tic_01)
        val secondSavedLogo = sharedPref.getInt(R.string.second_logo.toString(), R.mipmap.tic_06)

        firstSelectedButton = findViewById(R.id.first_selected_button)
        secondSelectedButton = findViewById(R.id.second_selected_button)

        val firstTagName = getResourceTagName(firstSavedLogo)
        val secondTagName = getResourceTagName(secondSavedLogo)

        firstSelectedButton.tag = firstTagName
        secondSelectedButton.tag = secondTagName

        firstSelectedButton.background = ContextCompat.getDrawable(RootApp.instance, firstSavedLogo)
        secondSelectedButton.background =
            ContextCompat.getDrawable(RootApp.instance, secondSavedLogo)

        imageFirstButtons = Array(2) { row ->
            Array(3) { column ->
                initFirstButton(row, column)
            }

        }
        imageSecondButtons = Array(2) { row ->
            Array(3) { column ->
                initSecondButton(row, column)
            }

        }

    }


    private fun initFirstButton(row: Int, column: Int): ImageButton {
        val imageBtn: ImageButton = findViewById(
            resources.getIdentifier(
                "first_button_$row$column",
                "id",
                packageName
            )
        )
        imageBtn.setOnClickListener {
            onFirstButtonClick(imageBtn)
        }
        return imageBtn
    }

    private fun initSecondButton(row: Int, column: Int): ImageButton {
        val imageBtn: ImageButton = findViewById(
            resources.getIdentifier(
                "second_button_$row$column",
                "id",
                packageName
            )
        )
        imageBtn.setOnClickListener {
            onSecondButtonClick(imageBtn)
        }
        return imageBtn
    }

    private fun onFirstButtonClick(imageBtn: ImageButton) {
        val rec = imageBtn.tag
        val editor = sharedPref.edit()
        if (rec != secondSelectedButton.tag) {
            val recId = getResourceId(rec.toString())
            firstSelectedButton.background = ContextCompat.getDrawable(RootApp.instance, recId)
            firstSelectedButton.tag = rec
            editor.putInt(R.string.first_logo.toString(), recId)
            editor.apply()
        }

    }

    private fun onSecondButtonClick(imageBtn: ImageButton) {
        val rec = imageBtn.tag
        val editor = sharedPref.edit()
        if (firstSelectedButton.tag != rec) {
            val recId = getResourceId(rec.toString())
            secondSelectedButton.background = ContextCompat.getDrawable(RootApp.instance, recId)
            secondSelectedButton.tag = rec
            editor.putInt(R.string.second_logo.toString(), recId)
            editor.apply()
        }

    }

    private fun getResourceId(tagName: String): Int {
        return when (tagName) {
            "tac_00" -> R.mipmap.tic_01
            "tac_01" -> R.mipmap.tic_02
            "tac_02" -> R.mipmap.tic_03
            "tac_10" -> R.mipmap.tic_04
            "tac_11" -> R.mipmap.tic_05
            else -> {
                R.mipmap.tic_06
            }
        }
    }

    private fun getResourceTagName(rec: Int): String {
        return when (rec) {
            R.mipmap.tic_01 -> "tac_00"
            R.mipmap.tic_02 -> "tac_01"
            R.mipmap.tic_03 -> "tac_02"
            R.mipmap.tic_04 -> "tac_10"
            R.mipmap.tic_05 -> "tac_11"
            else -> {
                "tac_12"
            }
        }
    }
}