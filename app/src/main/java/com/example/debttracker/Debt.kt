package com.example.debttracker

data class Debt(
    var id: Long = 0L,
    var name: String = "",
    var reason: String = "",
    var amount: Double = 0.0,
    var revtag: String = ""
)
