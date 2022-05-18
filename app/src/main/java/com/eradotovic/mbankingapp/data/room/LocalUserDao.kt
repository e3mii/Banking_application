package com.eradotovic.mbankingapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eradotovic.mbankingapp.data.entity.LocalUser
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LocalUserDao {

    @Query(value = "SELECT * FROM localUsers")
    abstract fun getLocalUsers(): Flow<List<LocalUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: LocalUser)
}