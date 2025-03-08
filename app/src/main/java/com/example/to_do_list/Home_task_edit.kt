package com.example.to_do_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home_task_edit : AppCompatActivity() {
    private lateinit var editTaskName: EditText
    private lateinit var editTaskDesc: EditText
    private lateinit var editTaskDate: EditText
    private lateinit var updateTaskButton: Button

    private var taskIndex: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_task_edit)
        editTaskName = findViewById(R.id.editTaskName)
        editTaskDesc = findViewById(R.id.editTaskDesc)
        editTaskDate = findViewById(R.id.editTaskDate)
        updateTaskButton = findViewById(R.id.updateTaskButton)

        taskIndex = intent.getIntExtra("taskIndex", -1)
        editTaskName.setText(intent.getStringExtra("taskName"))
        editTaskDesc.setText(intent.getStringExtra("taskDesc"))
        editTaskDate.setText(intent.getStringExtra("taskDate"))

        updateTaskButton.setOnClickListener {
            val updatedName = editTaskName.text.toString().trim()
            val updatedDesc = editTaskDesc.text.toString().trim()
            val updatedDate = editTaskDate.text.toString().trim()

            if (updatedName.isNotEmpty() && updatedDate.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("updatedName", updatedName)
                resultIntent.putExtra("updatedDesc", updatedDesc)
                resultIntent.putExtra("updatedDate", updatedDate)
                resultIntent.putExtra("taskIndex", taskIndex)

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Task name and date are required!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}