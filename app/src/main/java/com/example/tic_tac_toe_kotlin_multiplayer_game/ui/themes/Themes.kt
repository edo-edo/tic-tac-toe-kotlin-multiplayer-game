package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.themes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tic_tac_toe_kotlin_multiplayer_game.R


class Themes : AppCompatActivity() {
    private lateinit var imageButtons: Array<Array<ImageButton>>
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)

        imageButtons = Array(2) { row ->
            Array(3) { column ->
                initButton(row, column)
            }

        }
        sharedPref = getSharedPreferences(
            getString(R.string.themes), Context.MODE_PRIVATE)
    }



private fun initButton(row: Int, column: Int): ImageButton {
    val imageBtn: ImageButton = findViewById(
        resources.getIdentifier(
            "theme_button_$row$column",
            "id",
            packageName
        )
    )
    imageBtn.setOnClickListener {
        onButtonClick(imageBtn)
    }
    return imageBtn
}

    private fun onButtonClick(imageBtn: ImageButton) {
        val rec =  imageBtn.tag
        val editor = sharedPref.edit()
        editor.putString(R.string.active_logo.toString(),rec.toString())
        editor.apply()
    }
}