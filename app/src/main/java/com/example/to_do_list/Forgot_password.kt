package com.example.to_do_list

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.to_do_list.network.PasswordRequest
import com.example.to_do_list.network.PasswordResponse
import com.example.to_do_list.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Forgot_password : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var otpEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var resetPasswordButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgotPasswordConstraintLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        emailEditText = findViewById(R.id.email)
        otpEditText = findViewById(R.id.otp)
        newPasswordEditText = findViewById(R.id.password)
        resetPasswordButton = findViewById(R.id.change)
        progressBar = findViewById(R.id.progressBar)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener { finish() }

        // Reset Password
        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val otp = otpEditText.text.toString().trim()
            val newPassword = newPasswordEditText.text.toString().trim()

            if (validateInputs(email, otp, newPassword)) {
                resetPassword(email, otp, newPassword)
            }
        }
    }

    private fun resetPassword(email: String, otp: String, newPassword: String) {
        progressBar.visibility = View.VISIBLE

        val request = PasswordRequest(email, otp, newPassword)
        RetrofitClient.instance.changePassword(request).enqueue(object : Callback<PasswordResponse> {
            override fun onResponse(call: Call<PasswordResponse>, response: Response<PasswordResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body()?.success == true) {
                    showToast("Password changed successfully! Please log in.")
                    finish() // Close activity after successful reset
                } else {
                    showToast(response.body()?.message ?: "Password reset failed. Try again!")
                }
            }

            override fun onFailure(call: Call<PasswordResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                showToast("Error: ${t.localizedMessage}")
            }
        })
    }

    private fun validateInputs(email: String, otp: String, newPassword: String): Boolean {
        if (email.isEmpty()) {
            showToast("Email is required")
            return false
        }
        if (otp.isEmpty()) {
            showToast("OTP is required")
            return false
        }
        if (newPassword.isEmpty()) {
            showToast("New password is required")
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
