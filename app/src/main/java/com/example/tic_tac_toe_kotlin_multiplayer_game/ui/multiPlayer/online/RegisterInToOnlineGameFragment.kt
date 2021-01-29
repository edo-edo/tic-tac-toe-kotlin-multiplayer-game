package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.multiPlayer.online

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.os.Handler

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.isEmailValid
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.statusIsEmailValid
import com.example.tic_tac_toe_kotlin_multiplayer_game.extensions.statusIsPasswordValid
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegisterInToOnlineGameFragment : Fragment(R.layout.fragment_register_in_to_online_game) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.register()
        view.findViewById<TextView>(R.id.backToLogInTextViewID).setOnClickListener {
            findNavController().navigate(R.id.action_RegisterInToOnlineGameFragment_to_LogInToOnlineGameFragment)
        }
    }

    private fun View.register() {
        val emailEditText = findViewById<EditText>(R.id.EmailRegisterActivityEditTextsID)//.
        val passwordEditText = findViewById<EditText>(R.id.PasswordRegisterActivityEditTextsID)//
        val rePasswordEditText = findViewById<EditText>(R.id.ReenteredPasswordRegisterActivityEditTextsID)

        emailEditText.statusIsEmailValid()
        passwordEditText.statusIsPasswordValid()
        rePasswordEditText.statusIsPasswordValid()

        findViewById<Button>(R.id.RegisterButtonID).setOnClickListener() {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val rePassword = rePasswordEditText.text.toString()


            if ((password == rePassword) && (password.length >= MIN_PASS_CHARACTER_LENGTH)) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) {
                            Toast.makeText(
                                context,
                                "This Email does already exist",
                                Toast.LENGTH_LONG
                            ).show()
                            return@addOnCompleteListener
                        } else {
                            //d("jjksdfhd","ikakooo: ${it.result?.user?.uid}")
                            Toast.makeText(context, "Registered Successfully", Toast.LENGTH_LONG)
                                .show()
                            Handler().postDelayed({
                                findNavController().navigate(R.id.action_RegisterInToOnlineGameFragment_to_LogInToOnlineGameFragment)

                            }, 2000)
                        }

                    }
            } else if (!email.isEmailValid()) {
                Toast.makeText(context, "Please input correct Email", Toast.LENGTH_LONG).show()
            } else if ((password.length <= MIN_PASS_CHARACTER_LENGTH) && (rePassword.length <= MIN_PASS_CHARACTER_LENGTH)) {
                Toast.makeText(context, "Short password, input between 8-64", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(context, "Reentered Password doesn't match", Toast.LENGTH_LONG)
                    .show()
            }


        }

    }

    companion object {
        private const val MIN_PASS_CHARACTER_LENGTH = 8
    }

}