package com.example.debttracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DebtListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DebtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_debt_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView =
            findViewById(R.id.debtsRecyclerView)

        adapter = DebtAdapter(
            debts = AppData.debts.findAll(),

            onEdit = { debt ->
                editDebt(debt)
            },

            onDelete = { debt ->
                deleteDebt(debt)
            }
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        val returnButton =
            findViewById<Button>(R.id.returnButton)

        returnButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {
            adapter.updateDebts(
                AppData.debts.findAll()
            )
        }
    }

    private fun editDebt(debt: Debt){
        val intent =
            Intent(this, AddEditDebtActivity::class.java)

        intent.putExtra("id", debt.id)

        startActivity(intent)
    }

    private fun deleteDebt(debt: Debt){
        AppData.debts.delete(debt.id)

        adapter.updateDebts(
            AppData.debts.findAll()
        )
    }
}