package com.example.debttracker

val store: DebtStore = DebtMemStore()

fun main() {
    println("===Debt Tracking Application===")
    var input: Int
    do {
        input = menu()
        when (input) {
            1 -> addDebt()
            2 -> listDebts()
            3 -> updateDebt()
            4 -> deleteDebt()
            5 -> searchDebt()
            0 -> println("\nExiting Debt Application. Goodbye!")
            else -> println("\nInvalid option. Please try again.")
        }
    } while (input != 0)
}

fun menu(): Int {
    println("\n----------------------------------")
    println(" MAIN MENU")
    println("----------------------------------")
    println(" 1. Add Debt")
    println(" 2. List All Debts")
    println(" 3. Update a Debt")
    println(" 4. Delete a Debt")
    println(" 5. Search Debt by ID")
    println(" 0. Exit")
    print("\nEnter option: ")
    return readlnOrNull()?.toIntOrNull() ?: -1
}
fun addDebt() {
    println("\n--- Add Debt ---")
    print("Enter Name of Debtor: ")
    val name = readlnOrNull()?.trim().orEmpty()
    print("Enter Reason for Debt: ")
    val reason = readlnOrNull()?.trim().orEmpty()
    val amount = readAmount() ?: return
    val revtag = readRevtag() ?: return

    if (name.isNotEmpty()) {
        val debt = Debt(name = name, reason = reason, amount = amount, revtag = revtag)
        store.create(debt)
        println("Debt logged with ID: ${debt.id}")
    } else {
        println("Title cannot be empty. Creation cancelled.")
    }
}
fun listDebts() {
        println("\n--- All Debts---")
        val debts = store.findAll()
        if (debts.isEmpty()) {
            println("No debts recorded yet.")
        } else {
            debts.forEach { println("ID: ${it.id} | Name: ${it.name} | Reason: ${it.reason} | Amount: ${it.amount} | Revtag: ${it.revtag}") }
        }
    }
fun updateDebt() {
    println("\n--- Update Debt ---")
    listDebts()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Debt to update: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null && store.findOne(id) != null) {
        print("Enter New Name: ")
        val name = readlnOrNull()?.trim().orEmpty()
        print("Enter New Reason: ")
        val reason = readlnOrNull()?.trim().orEmpty()
        print("Enter New Amount: ")
        val amount = readAmount() ?: return
        print("Enter a new revtag(optional")
        val revtag = readRevtag() ?: return

        if (name.isNotEmpty()) {
            val updated = store.update(Debt(id = id, name = name, reason = reason, amount = amount, revtag = revtag))
            if (updated) println("Debt updated successfully.")
        } else {
            println("There must be a name. Update cancelled.")
        }
    } else {
        println("Debt with ID $id not found.")
    }
}

fun deleteDebt() {
    println("\n--- Delete Debt ---")
    listDebts()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Debt to delete: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val deleted = store.delete(id)
        if (deleted) {
            println("Debt with ID $id deleted successfully.")
        } else {
            println("Debt with ID $id not found.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun searchDebt() {
    println("\n--- Search Debt ---")
    print("Enter ID: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val debt = store.findOne(id)
        if (debt != null) {
            println("Found: ID: ${debt.id} | Name: ${debt.name} | Reason: ${debt.reason} | Amount: ${debt.amount} | Revtag: ${debt.revtag}")
        } else {
            println("No debt found with ID $id.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun readAmount(): Double? {
    print("Enter Amount (€): ")
    val amount = readlnOrNull()?.trim()?.toDoubleOrNull()
    if (amount == null || amount <= 0) {
        println("Amount must be a number greater than 0.")
        return null
    }
    return amount
}

fun readRevtag(): String? {
    print("Enter Revtag (optional): ")
    val revtag = readlnOrNull()?.trim().orEmpty()
    if (revtag.isNotEmpty() && !revtag.startsWith("@")) {
        return "@$revtag"
    }
    return revtag
}