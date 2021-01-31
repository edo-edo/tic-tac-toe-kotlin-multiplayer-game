package com.example.tic_tac_toe_kotlin_multiplayer_game.tools


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.example.tic_tac_toe_kotlin_multiplayer_game.R


object CustomTools {

    private fun isConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (error: Exception) {
            false
        }
    }



    @SuppressLint("SetTextI18n")
    fun networkErrorDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.error_dialog_layout)

        val params: ViewGroup.LayoutParams = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = params as WindowManager.LayoutParams
        dialog.findViewById<TextView>(R.id.dialogTitleTextViewID).text = "No Internet Connection"
        dialog.findViewById<TextView>(R.id.dialogDescriptionTextViewID).text =
            "Please Connect The Internet"
        dialog.findViewById<Button>(R.id.dialogOkButton).setOnClickListener {
            dialog.dismiss()
            if (!isConnected()) {
                Handler().postDelayed({
                    dialog.show()
                }, 2000)

            }
        }
        dialog.show()
    }
}