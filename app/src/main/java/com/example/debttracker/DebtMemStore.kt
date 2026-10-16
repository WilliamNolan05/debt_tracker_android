package com.example.debttracker

import java.util.concurrent.atomic.AtomicLong

class DebtMemStore : DebtStore{
    private val debts = ArrayList<Debt>()
    private val lastId = AtomicLong(0L)

    override fun findAll(): List<Debt> {
        return debts
    }

    override fun create(debt: Debt) {
        debt.id = lastId.incrementAndGet()
        debts.add(debt)
    }

    override fun update(debt: Debt): Boolean {
        val foundDebt = findOne(debt.id)
        return if (foundDebt != null){
            foundDebt.name = debt.name
            foundDebt.reason = debt.reason
            foundDebt.amount = debt.amount
            foundDebt.revtag = debt.revtag
            foundDebt.dueDate = debt.dueDate
            true
        } else {
            false
        }
    }
    override fun delete(id: Long): Boolean {
        val foundDebt = findOne(id)
        return if (foundDebt != null){
            debts.remove(foundDebt)
            true
        } else {
            false
        }
    }

    override fun findOne(id: Long): Debt? {
        return debts.find {d -> d.id == id}
    }
}