package com.example.videomessengerproject

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsersAccountsDAO {

    @Insert
    suspend fun insert(usersAccounts: UsersAccounts)

    @Update
    suspend fun update(usersAccounts: UsersAccounts)

    @Delete
    suspend fun delete(usersAccounts: UsersAccounts)

    @Query("SELECT * FROM information_about_user WHERE userId = :userId")
    fun get(userId: Long): LiveData<UsersAccounts>

    @Query("SELECT * FROM information_about_user ORDER BY userId DESC")
    fun getAll(): LiveData<List<UsersAccounts>>
}
