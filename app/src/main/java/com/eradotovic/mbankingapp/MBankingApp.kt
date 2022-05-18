package com.eradotovic.mbankingapp

import android.app.Application

/**
 * providing db, and context if needed, for entire app
 * */
class MBankingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalDataObject.provide(this)
    }
}