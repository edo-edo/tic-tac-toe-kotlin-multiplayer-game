package com.example.tic_tac_toe_kotlin_multiplayer_game.extensions

import android.graphics.Paint
import android.text.Editable
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
        if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

fun View.myCustomSnackbar(text: String){
    Snackbar.make(this, text, Snackbar.LENGTH_LONG)
        .setAction("Action", null).show()
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)