package com.example.tic_tac_toe_kotlin_multiplayer_game.extensions

import android.annotation.SuppressLint
import android.graphics.Paint
import android.text.Editable
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.google.android.material.snackbar.Snackbar


fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
        if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

@SuppressLint("ResourceAsColor")
fun View.myCustomSnackbar(text: String, id:Int){
    Snackbar.make(this, text, Snackbar.LENGTH_LONG).setBackgroundTint(R.color.purple).setAnchorView(this.findViewById(id))
        .setAction("Action", null).show()
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun String.isEmailValid(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
