package com.example.tic_tac_toe_kotlin_multiplayer_game.extensions

import android.annotation.SuppressLint
import android.text.Editable
import android.view.View
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.google.android.material.snackbar.Snackbar




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

