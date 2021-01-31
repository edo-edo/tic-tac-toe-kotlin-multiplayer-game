package com.example.tic_tac_toe_kotlin_multiplayer_game.ui.splash_screen

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tic_tac_toe_kotlin_multiplayer_game.R
import com.example.tic_tac_toe_kotlin_multiplayer_game.ui.StartGameActivity


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        rotateAnimate(findViewById(R.id.SplashScreenImageView))

        rotateAnimateInfinity(findViewById(R.id.imageView_01))
        rotateAnimateInfinity(findViewById(R.id.imageView_02))
        rotateAnimateInfinity(findViewById(R.id.imageView_03))
        rotateAnimateInfinity(findViewById(R.id.imageView_04))


        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    val intent = Intent(baseContext, StartGameActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
        background.start()


    }


    private fun rotateAnimate(imageView: ImageView) {
        val rotate = RotateAnimation(
            0F, 360F,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 1500
        imageView.animation = rotate
    }

    private fun rotateAnimateInfinity(imageView: ImageView) {
        val rotate = loadAnimation(this, R.anim.rotate_animation)
        rotate.fillAfter = true
        imageView.animation = rotate
    }
}