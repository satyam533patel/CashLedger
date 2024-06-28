package com.example.cashledger

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cashledger.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class Signin_activity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()
        binding.textViewNotRegisteredYet.setOnClickListener{
            val intent= Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSignin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val passwd = binding.editTextPassword.text.toString()

            if (email.isNotEmpty() && passwd.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, passwd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, CheckInPage::class.java)
                            startActivity(intent)
                        } else {
                            val exception = task.exception
                            if (exception is FirebaseAuthInvalidUserException || exception is FirebaseAuthInvalidCredentialsException) {
                                // Invalid user or invalid credentials (wrong password)
                                Toast.makeText(this@Signin_activity, "Credentials don't match, Please check!", Toast.LENGTH_SHORT).show()
                            } else {
                                // Handle other exceptions if needed
                                Toast.makeText(this@Signin_activity, "Sign-in failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            } else {
                Toast.makeText(this@Signin_activity, "Empty Fields are NOT allowed!!", Toast.LENGTH_SHORT).show()
            }
        }

        enableEdgeToEdge()
//        setContentView(R.layout.activity_signin)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_signin_activity)
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