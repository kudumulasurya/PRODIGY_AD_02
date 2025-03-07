package com.example.to_do_list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_list.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val username = findViewById<EditText>(R.id.newuser)
        val email = findViewById<EditText>(R.id.newmail)
        val phone = findViewById<EditText>(R.id.newnumber)
        val password = findViewById<EditText>(R.id.newname)
        val confirmPassword = findViewById<EditText>(R.id.newpassword)
        val signUpButton = findViewById<Button>(R.id.button2)
        val loginTextView = findViewById<TextView>(R.id.login1)
        val backButton = findViewById<ImageButton>(R.id.backButton1)
        progressBar = findViewById(R.id.progressBar)

        // Sign Up button listener
        signUpButton.setOnClickListener {
            val usernameText = username.text.toString().trim()
            val emailText = email.text.toString().trim()
            val phoneText = phone.text.toString().trim()
            val passwordText = password.text.toString().trim()
            val confirmPasswordText = confirmPassword.text.toString().trim()

            if (validateInputs(usernameText, emailText, phoneText, passwordText, confirmPasswordText)) {
                val phoneNumber: Long = phoneText.toLongOrNull() ?: 0L // Convert phone to Long, default to 0 if invalid
                registerUser(usernameText, emailText, phoneNumber, passwordText)
            }
        }

        // Navigate to Login screen
        loginTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close SignUp activity
        }

        // Back button listener
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateInputs(username: String, email: String, phone: String, password: String, confirmPassword: String): Boolean {
        if (username.isEmpty()) {
            showToast("Username is required")
            return false
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Enter a valid email")
            return false
        }
        if (phone.length != 10 || phone.toLongOrNull() == null) {
            showToast("Enter a valid 10-digit phone number")
            return false
        }
        if (password.isEmpty()) {
            showToast("Password is required")
            return false
        }
        if (password != confirmPassword) {
            showToast("Passwords do not match")
            return false
        }
        return true
    }

    private fun registerUser(username: String, email: String, phone: Long, password: String) {
        progressBar.visibility = View.VISIBLE

        val request = RegisterRequest(username, email, phone, password)
        RetrofitClient.instance.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body()?.success == true) {
                    showToast("Registration successful! Please log in.")
                    finish() // Close SignUp activity
                } else {
                    showToast(response.body()?.message ?: "Registration failed. Try again!")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                showToast("Error: ${t.localizedMessage}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
