package com.eradotovic.mbankingapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "localUsers",
    indices = [
        Index("id", unique = true)
    ]
)
data class LocalUser (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val localUserId : Long = 0,
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "pin") val pin: String
)
