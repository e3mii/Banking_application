package com.eradotovic.mbankingapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eradotovic.mbankingapp.data.entity.LocalUser

@Database(
    entities = [LocalUser::class],
    version = 1,
    exportSchema = false
)
abstract class MBankingAppDB : RoomDatabase() {
    abstract fun localUserDao(): LocalUserDao
}