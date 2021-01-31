package com.example.tic_tac_toe_kotlin_multiplayer_game.extensions


import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log.d
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.RootApp


fun EditText.statusIsEmailValid() {

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.toString().isEmailValid()) {
                val drawableEnd = ContextCompat.getDrawable(
                    RootApp.instance,
                    R.drawable.ic_baseline_check_circle_24
                )
                val drawableStart = ContextCompat.getDrawable(
                    RootApp.instance,
                    R.drawable.ic_baseline_email_42
                )

                setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, drawableEnd, null)

            } else {

                val drawableEnd = ContextCompat.getDrawable(
                    RootApp.instance,
                    R.drawable.ic_baseline_error_24
                )
                val drawableStart = ContextCompat.getDrawable(
                    RootApp.instance,
                    R.drawable.ic_baseline_email_42
                )
                setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, drawableEnd, null)
            }

        }
    })
}

fun EditText.statusIsPasswordValid() {

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.toString().length>7) {
                val drawableEnd = ContextCompat.getDrawable(
                    RootApp.instance,
                    R.drawable.ic_baseline_check_circle_24
                )
                val drawableStart = ContextCompat.getDrawable(
                    RootApp.instance,
                    R.drawable.ic_baseline_lock_42
                )

                setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, drawableEnd, null)

            } else {

                val drawableEnd = ContextCompat.getDrawable(
                    RootApp.instance,
                    R.drawable.ic_baseline_error_24
                )
                val drawableStart = ContextCompat.getDrawable(
                    RootApp.instance,
                    R.drawable.ic_baseline_lock_42
                )
                setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, drawableEnd, null)
            }

        }
    })
}
fun EditText.setPasswordVisibility() {
    inputType =
        if (tag == "1") InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD else InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    setSelection(length())
}