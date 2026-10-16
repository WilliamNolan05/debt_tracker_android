package com.example.debttracker

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewDebtsButton = findViewById<Button>(R.id.viewDebtsButton)
        val addDebtButton = findViewById<Button>(R.id.addDebtButton)

        viewDebtsButton.setOnClickListener {
            startActivity(Intent(this, DebtListActivity::class.java))
        }

        addDebtButton.setOnClickListener {
            startActivity(Intent(this, AddEditDebtActivity::class.java))
        }
    }
}