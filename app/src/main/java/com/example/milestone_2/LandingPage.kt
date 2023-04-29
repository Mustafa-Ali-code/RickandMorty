package com.example.milestone_2

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)

        val username = findViewById<EditText>(R.id.userName)
        val startButton = findViewById<Button>(R.id.startButton)

        startButton.setOnClickListener {
            if(username.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter Your Name!", Toast.LENGTH_SHORT).show()
            }else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}