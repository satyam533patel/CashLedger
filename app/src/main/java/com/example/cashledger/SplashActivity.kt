package com.example.cashledger
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler // Ensure you import android.os.Handler correctly
import com.example.cashledger.R
import com.example.cashledger.IntroActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler().postDelayed({
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish() // Finish SplashActivity so that it doesn't appear on back press
        }, 2000) // Delay in milliseconds (2000 ms = 2 seconds)
    }
}
