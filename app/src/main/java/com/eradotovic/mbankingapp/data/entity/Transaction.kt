package com.eradotovic.mbankingapp.data.entity

data class Transaction(
    val id: String,
    val date: String,
    val description: String,
    val amount: String,
    val type: String
)
