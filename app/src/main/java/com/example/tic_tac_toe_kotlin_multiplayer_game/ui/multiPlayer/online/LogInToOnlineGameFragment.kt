package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.tic_tac_toe_kotlin_multiplayer_game.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LogInToOnlineGameFragment : Fragment(R.layout.fragment_log_in_to_online_game) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.logIn()
    }

    private fun View.logIn() {

        findViewById<Button>(R.id.LogInButton).setOnClickListener() {
            val email = findViewById<EditText>(R.id.LogInEmailEditTextsID).text.toString()
            val password = findViewById<EditText>(R.id.LogInPasswordEditTextsID).text.toString()
            if (email.length >= MIN_PASS_CHARACTER_LENGTH && password.length >= MIN_EMAIL_CHARACTER_LENGTH) {
                Toast.makeText(
                    context,
                    "please wait! before Email and Password checking",
                    Toast.LENGTH_LONG
                ).show()
//                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener {
//                        if (!it.isSuccessful) {
//                            Toast.makeText(
//                                context,
//                                "Password or Email doesn't match",
//                                Toast.LENGTH_LONG
//                            ).show()
//                            //return@addOnCompleteListener
//                        } else {
//                            startActivity(Intent(context, LoggedInNotesActivity::class.java))
//                        }
//
//                    }
//                .addOnFailureListener {
//                    Toast.makeText(this, "Password or Email doesn't match", Toast.LENGTH_LONG).show()
//                }
            } else {
                Toast.makeText(
                    context,
                    "Password or Email doesn't match",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
//        findViewById<TextView>(R.id.RegisterTextViewID).setOnClickListener() {
//            startActivity(Intent(context, RegisterInNotesActivity::class.java))
//        }
    }

    companion object {
        private const val MIN_PASS_CHARACTER_LENGTH = 8
        private const val MIN_EMAIL_CHARACTER_LENGTH = 5
    }

}