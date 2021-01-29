package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.statusIsEmailValid
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LogInToOnlineGameFragment : Fragment(R.layout.fragment_log_in_to_online_game) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.logIn()
    }

    private fun View.logIn() {
        val emailEditText =   findViewById<EditText>(R.id.LogInEmailEditTextsID)
        val passwordEditText = findViewById<EditText>(R.id.LogInPasswordEditTextsID)
        emailEditText.statusIsEmailValid()

        findViewById<Button>(R.id.LogInButton).setOnClickListener() {

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (email.length >= MIN_PASS_CHARACTER_LENGTH && password.length >= MIN_EMAIL_CHARACTER_LENGTH) {
                Toast.makeText(
                    context,
                    "please wait! before Email and Password checking",
                    Toast.LENGTH_LONG
                ).show()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Password or Email doesn't match",
                                Toast.LENGTH_LONG
                            ).show()

                            //it.result?.user?.uid

                            //return@addOnCompleteListener
                        } else {
                            Toast.makeText(context, "Logged in", Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_LogInToOnlineGameFragment_to_StarGameOnlineMultiPlayerFragment)
                            it.result?.user?.uid?.let { uid -> d("sdffsdf", uid) }
                        }

                    }
                .addOnFailureListener {
                    Toast.makeText(context, "Password or Email doesn't match", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(
                    context,
                    "Password or Email doesn't match",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        findViewById<TextView>(R.id.RegisterTextViewID).setOnClickListener() {
             findNavController().navigate(R.id.action_LogInToOnlineGameFragment_to_RegisterInToOnlineGameFragment)

        }
    }

    companion object {
        private const val MIN_PASS_CHARACTER_LENGTH = 8
        private const val MIN_EMAIL_CHARACTER_LENGTH = 5
    }

}