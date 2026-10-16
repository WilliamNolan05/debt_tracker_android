package com.example.debttracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DebtAdapter(
    private var debts: List<Debt>,
    private val onEdit: (Debt) -> Unit,
    private val onDelete: (Debt) -> Unit
) : RecyclerView.Adapter<DebtAdapter.DebtViewHolder>() {

    class DebtViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val nameText: TextView =
            itemView.findViewById(R.id.nameText)

        val reasonText: TextView =
            itemView.findViewById(R.id.reasonText)

        val amountText: TextView =
            itemView.findViewById(R.id.amountText)

        val dueDateText: TextView =
            itemView.findViewById(R.id.dueDateText)

        val editButton: Button =
            itemView.findViewById(R.id.editButton)

        val deleteButton: Button =
            itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DebtViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_debt,
                parent,
                false
            )

        return DebtViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DebtViewHolder,
        position: Int
    ) {

        val debt = debts[position]

        holder.nameText.text =
            "${debt.id}: ${debt.name}"

        holder.reasonText.text =
            debt.reason

        holder.amountText.text =
            "€${"%.2f".format(debt.amount)}  ${debt.revtag}"

        holder.dueDateText.text =
            if (debt.dueDate.isEmpty()) "No due date" else "Due: ${debt.dueDate}"

        holder.editButton.setOnClickListener {
            onEdit(debt)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(debt)
        }
    }

    override fun getItemCount(): Int {
        return debts.size
    }

    fun updateDebts(newDebts: List<Debt>) {
        debts = newDebts
        notifyDataSetChanged()
    }
}