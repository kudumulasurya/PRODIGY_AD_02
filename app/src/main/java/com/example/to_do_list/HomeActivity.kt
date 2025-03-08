package com.example.to_do_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var taskInput: EditText
    private lateinit var taskDescription: EditText
    private lateinit var taskDate: EditText
    private lateinit var addButton: Button
    private lateinit var taskListView: ListView
    private lateinit var adapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    data class Task(var name: String, var description: String, var date: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        taskInput = findViewById(R.id.taskInput)
        taskDescription = findViewById(R.id.taskDescription)
        taskDate = findViewById(R.id.taskDate)
        addButton = findViewById(R.id.addButton)
        taskListView = findViewById(R.id.taskListView)

        adapter = TaskAdapter()
        taskListView.adapter = adapter

        addButton.setOnClickListener {
            val name = taskInput.text.toString().trim()
            val description = taskDescription.text.toString().trim()
            val date = taskDate.text.toString().trim()

            if (name.isNotEmpty() && date.isNotEmpty()) {
                tasks.add(Task(name, description, date))
                adapter.notifyDataSetChanged()
                taskInput.text.clear()
                taskDescription.text.clear()
                taskDate.text.clear()
            } else {
                Toast.makeText(this, "Task name and date are required!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class TaskAdapter : BaseAdapter() {
        override fun getCount(): Int = tasks.size
        override fun getItem(position: Int): Any = tasks[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(parent?.context)
                .inflate(R.layout.home_taskitem, parent, false)

            val taskName: TextView = view.findViewById(R.id.taskName)
            val taskDesc: TextView = view.findViewById(R.id.taskDesc)
            val taskDate: TextView = view.findViewById(R.id.taskDate)
            val updateButton: Button = view.findViewById(R.id.updateButton)
            val deleteButton: Button = view.findViewById(R.id.deleteButton)

            val task = tasks[position]

            taskName.text = task.name
            taskDesc.text = task.description
            taskDate.text = "Due Date: ${task.date}"

            updateButton.setOnClickListener {
                val intent = Intent(this@HomeActivity, Home_task_edit::class.java)
                intent.putExtra("taskIndex", position)
                intent.putExtra("taskName", task.name)
                intent.putExtra("taskDesc", task.description)
                intent.putExtra("taskDate", task.date)
                startActivityForResult(intent, 1)
            }

            deleteButton.setOnClickListener {
                tasks.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(this@HomeActivity, "Task deleted", Toast.LENGTH_SHORT).show()
            }

            return view
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val taskIndex = data.getIntExtra("taskIndex", -1)
            val updatedName = data.getStringExtra("updatedName") ?: ""
            val updatedDesc = data.getStringExtra("updatedDesc") ?: ""
            val updatedDate = data.getStringExtra("updatedDate") ?: ""

            if (taskIndex != -1) {
                tasks[taskIndex] = Task(updatedName, updatedDesc, updatedDate)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
