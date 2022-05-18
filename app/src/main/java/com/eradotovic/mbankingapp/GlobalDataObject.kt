package com.eradotovic.mbankingapp

import android.content.Context
import androidx.room.Room
import com.eradotovic.mbankingapp.data.repository.LocalUserRepository
import com.eradotovic.mbankingapp.data.room.MBankingAppDB

/**
 * global data like db, and context provider
 * */
object GlobalDataObject {
    lateinit var database: MBankingAppDB
    lateinit var appContext: Context

    val localUserRepository by lazy {
        LocalUserRepository(
            localUserDao = database.localUserDao()
        )
    }

    /**
     * db, and context for entire app if needed
     * */
    fun provide(context: Context){
        appContext = context
        database = Room.databaseBuilder(context, MBankingAppDB::class.java, "mBankingAppDB.db")
            .fallbackToDestructiveMigration() //just for test purposes
            .build()
    }
}