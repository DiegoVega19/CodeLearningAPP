package com.happysmile.codelearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sig_in.*

class SigInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sig_in)
        auth()
    }

    private fun auth() {

        buttonEntar.setOnClickListener {
            if (editCorreo.text.isNotEmpty() && editPass.text.isNotEmpty())
            {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editCorreo.text.toString(),editPass.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        var i = Intent(this,LoginActivity::class.java)
                        startActivity(i)
                    }
                    else
                    {
                        Toast.makeText(this, "Se ha producido un error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}