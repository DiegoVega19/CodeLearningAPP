package com.happysmile.codelearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sig_in.*

class LoginActivity : AppCompatActivity() {
    private val GOOGLE_SIGN = 100;
    private val callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build()
        adView.loadAd(adRequest)

        registrarText.setOnClickListener {
            var i = Intent(this, SigInActivity::class.java)
            startActivity(i)
        }

        setup()
    }

    private fun setup() {
        btnentrar.setOnClickListener {
            if (textCorreo.text.isNotEmpty() && textPass.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    textCorreo.text.toString(),
                    textPass.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                       showHome()
                    } else {
                        showerror()
                    }
                }
            }
        }

        icGoogle.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, gso)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN)

        }

        icFb.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>
            {
                override fun onSuccess(result: LoginResult?) {

                        result?.let {
                            val token = it.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)
                            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    showHome()

                                } else {
                                    // If sign in fails, display a message to the user.
                                    showerror()
                                }
                            }
                        }
                    }

                    override fun onCancel() {
                      //Se cancela la sesion
                    }

                    override fun onError(error: FacebookException?) {
                        showerror()
                    }
                })

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      callbackManager.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            // Google Sign In was successful, authenticate with Firebase
            try {
                val account = task.getResult(ApiException::class.java)!!
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                showHome()

                            } else {
                                // If sign in fails, display a message to the user.
                                showerror()
                            }
                        }
                }
            } catch (e: ApiException) {
                showerror()
            }

        }
    }

    fun showerror() {
        Toast.makeText(this, "Se ha producido un error", Toast.LENGTH_SHORT).show()
    }

    fun showHome()
    {
        var i = Intent(this, MainMenuActivity::class.java)
        startActivity(i)
    }
}
