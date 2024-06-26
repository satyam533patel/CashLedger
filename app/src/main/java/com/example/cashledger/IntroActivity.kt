package com.example.cashledger

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        val buttonSignup_intro = findViewById<Button>(R.id.button_signup_intro)
        buttonSignup_intro.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }
    }
}