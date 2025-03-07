package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val Forgot = findViewById<TextView>(R.id.forgotPassword)
        val signUp = findViewById<TextView>(R.id.textView2)
        Forgot.setOnClickListener {
            val intent = Intent(this, Forgot_password::class.java)
            startActivity(intent)
        }
        signUp.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }
    }
}