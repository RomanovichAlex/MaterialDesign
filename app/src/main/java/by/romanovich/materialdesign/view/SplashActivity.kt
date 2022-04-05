package by.romanovich.materialdesign.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import by.romanovich.materialdesign.R
import by.romanovich.materialdesign.view.main.MainFragment


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.MensTheme)

        setContentView(R.layout.activity_splash)

        findViewById<ImageView>(R.id.image_view).animate().rotationBy(720f).setInterpolator(LinearInterpolator()).duration= 10000L

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        },2000L)
    }
}

