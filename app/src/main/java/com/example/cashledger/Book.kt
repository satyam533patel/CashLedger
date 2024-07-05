package com.example.cashledger

data class Book(
    var id: String = "",  // Ensure this matches your Firebase database structure
    var name: String = "",
    var balance: Double = 0.0
)
