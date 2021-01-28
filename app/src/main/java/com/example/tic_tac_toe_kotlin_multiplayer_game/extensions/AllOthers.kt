package com.example.tic_tac_toe_kotlin_multiplayer_game.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Paint
import android.text.Editable
import android.view.View
import android.widget.TextView
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online.star_game.PlayerModel
import com.google.android.material.snackbar.Snackbar


fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
        if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

@SuppressLint("ResourceAsColor")
fun View.myCustomSnackbar(text: String, id: Int){
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).setBackgroundTint(R.color.purple).setAnchorView(
        this.findViewById(
            id
        )
    )
        .setAction("Action", null).show()
}

fun View.snackBar(text: String){
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).setAction("Action", null).show()
}


fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


fun String.isEmailValid(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.alphabetizedSort() = String(toCharArray().apply { sort() })
