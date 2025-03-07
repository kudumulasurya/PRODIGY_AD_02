package com.example.to_do_list

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_list.network.ApiService
import com.example.to_do_list.network.LoginRequest
import com.example.to_do_list.network.LoginResponse
import com.example.to_do_list.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailInput = findViewById<EditText>(R.id.editText)
        val passwordInput = findViewById<EditText>(R.id.editText2)
        val loginButton = findViewById<Button>(R.id.button)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val signUp = findViewById<TextView>(R.id.textView2)

        // Navigate to Forgot Password Activity
        forgotPassword.setOnClickListener {
            val intent = Intent(this, Forgot_password::class.java)
            startActivity(intent)
        }

        // Navigate to Sign Up Activity
        signUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        // Login Button Click
        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Enter email and password!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)

        RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Toast.makeText(applicationContext, "Success: ${loginResponse?.message}", Toast.LENGTH_LONG).show()

                    // Navigate to Home Screen (Replace with actual HomeActivity)
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
