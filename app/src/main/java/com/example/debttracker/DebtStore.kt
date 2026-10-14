package com.example.debttracker

interface DebtStore {
    fun findAll(): List<Debt>
    fun findOne(id: Long): Debt?
    fun create(debt: Debt)
    fun update(debt: Debt): Boolean
    fun delete(id: Long): Boolean
}