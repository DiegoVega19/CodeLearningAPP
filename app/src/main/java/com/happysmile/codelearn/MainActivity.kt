package com.happysmile.codelearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,LoginActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)


        //Evento de analitics
        val analitics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("Message", "iNTEGRACION Exitosa")
        analitics.logEvent("InitScreen",bundle)
    }
}