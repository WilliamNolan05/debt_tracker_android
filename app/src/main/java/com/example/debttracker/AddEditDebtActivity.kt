package com.example.debttracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.app.DatePickerDialog
import java.util.Calendar

class AddEditDebtActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var reasonInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var revtagInput: EditText
    private lateinit var dueDateButton: Button

    private var dueDate: String = ""
    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_edit_debt)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nameInput =
            findViewById(R.id.nameInput)
        reasonInput =
            findViewById(R.id.reasonInput)
        amountInput =
            findViewById(R.id.amountInput)
        revtagInput =
            findViewById(R.id.revtagInput)
        dueDateButton =
            findViewById(R.id.dueDateButton)

        val saveButton =
            findViewById<Button>(R.id.saveButton)
        val cancelButton =
            findViewById<Button>(R.id.cancelButton)

        editingId =
            intent.getLongExtra("id", -1L)
                .takeIf { it != -1L }

        if (editingId != null) {
            findViewById<TextView>(R.id.formTitle)
                .text = "Edit Debt"

            loadExistingDebt(editingId!!)
        }

        saveButton.setOnClickListener {
            saveDebt()
        }

        cancelButton.setOnClickListener {
            finish()
        }

        dueDateButton.setOnClickListener {
            pickDueDate()
        }
    }

    private fun loadExistingDebt(id: Long){
        val debt =
            AppData.debts.findOne(id)

            if (debt == null) {

                Toast.makeText(
                    this,
                    "Debt not found",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
                return
            }

            nameInput.setText(debt.name)
            reasonInput.setText(debt.reason)
            amountInput.setText(debt.amount.toString())
            revtagInput.setText(debt.revtag)
            dueDate = debt.dueDate
        if (dueDate.isNotEmpty()) {
            dueDateButton.text = "Due: $dueDate"
        }
        }

    private fun saveDebt() {
        val name =
            nameInput.text.toString().trim()

        val reason =
            reasonInput.text.toString().trim()

        if (name.isEmpty()) {
            nameInput.error = "Name is required"
            return
        }

        val amount =
            amountInput.text.toString().toDoubleOrNull()

        if (amount == null || amount <= 0) {
            amountInput.error = "Enter a valid number"
            return
        }


        var revtag =
            revtagInput.text.toString().trim()

        if (revtag.isNotEmpty() && !revtag.startsWith("@")) revtag = "@$revtag"

        if (editingId == null){

            val debt = Debt(
                name = name,
                reason = reason,
                amount = amount,
                revtag = revtag,
                dueDate = dueDate
            )

            AppData.debts.create(debt)

            Toast.makeText(
                this,
                "Debt Created",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val debt = Debt(
                id = editingId!!,
                name = name,
                reason = reason,
                amount = amount,
                revtag = revtag,
                dueDate = dueDate
            )

            AppData.debts.update(debt)

            Toast.makeText(
                this,
                "Debt Updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }

    private fun pickDueDate() {
        val calendar = Calendar.getInstance()

        DatePickerDialog(
            this,
            {_, year, month, day ->
                dueDate = "%02d/%02d/%d".format(day, month + 1, year)
                dueDateButton.text = "Due: $dueDate"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}