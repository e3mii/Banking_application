package com.eradotovic.mbankingapp.data.repository

import com.eradotovic.mbankingapp.data.entity.LocalUser
import com.eradotovic.mbankingapp.data.room.LocalUserDao
import kotlinx.coroutines.flow.Flow

class LocalUserRepository(
    private val localUserDao: LocalUserDao
) {

    fun getLocalUsers(): Flow<List<LocalUser>>{
        return localUserDao.getLocalUsers()
    }

    suspend fun insertLocalUser(localUser: LocalUser) = localUserDao.insert(localUser)
}