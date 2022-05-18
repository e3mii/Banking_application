package com.eradotovic.mbankingapp.data.entity

data class Account(
    val id: String,
    val IBAN: String,
    val amount: String,
    val currency: String,
    val transactions: List<Transaction>
)
