package com.example.cashledger

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cashledger.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()
        binding.textViewAlreadyRegistered.setOnClickListener{
            val intent=Intent(this,Signin_activity::class.java)
            startActivity(intent)
        }
        binding.buttonSignup.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val passwd = binding.editTextPassword.text.toString()
            if (email.isNotEmpty() && name.isNotEmpty() && passwd.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent=Intent(this,Signin_activity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@SignupActivity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(this@SignupActivity, "Empty Fields are NOT allowed!!", Toast.LENGTH_SHORT).show()
            }
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar_signup_activity)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
        }
        toolbar.setNavigationOnClickListener{onBackPressed()}
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}