package com.example.debttracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.appbar.MaterialToolbar

class DebtListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DebtAdapter
    private lateinit var searchInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_debt_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.title = "Your Debts"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        searchInput =
            findViewById(R.id.searchInput)

        searchInput.doAfterTextChanged {
            showDebts()
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {
            showDebts()
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
        showDebts()
    }

    private fun showDebts() {
        val query =
            searchInput.text.toString().trim()

        val debts =
            AppData.debts.findAll().filter { debt ->
                debt.name.contains(query, ignoreCase = true) ||
                        debt.reason.contains(query, ignoreCase=true) ||
                        debt.revtag.contains(query, ignoreCase=true)
            }
        adapter.updateDebts(debts)

        supportActionBar?.subtitle =
            "Total owed: €%.2f".format(debts.sumOf { it.amount })
    }
}