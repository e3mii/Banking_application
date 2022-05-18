package com.eradotovic.mbankingapp.data.entity

data class User(
    val user_id: String,
    val acounts: List<Account>
)