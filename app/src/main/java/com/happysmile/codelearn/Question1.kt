package com.happysmile.codelearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_question1.*

class Question1 : AppCompatActivity() {
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question1)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        btnfinalizar.setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
                mInterstitialAd.show()
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
        }

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                goToScore()
            }
        }
        buttonstring.setOnClickListener {
            edittextresult.setText(buttonstring.text)
        }
        }
    fun goToScore()
    {
        var i = Intent(this,Question2::class.java)
        startActivity(i)

    }
    }

