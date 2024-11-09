package com.example.videomessengerproject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsersAccounts::class], version = 1, exportSchema = false)
abstract class UsersAccountsDatabase : RoomDatabase() {
    abstract val userAccountsDao: UsersAccountsDAO

    companion object {

        private var INSTANCE: UsersAccountsDatabase? = null

        fun getInstance(context: Context): UsersAccountsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersAccountsDatabase::class.java,
                        "information_about_user"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}