package com.example.videomessengerproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UsersAccountViewModelFactory(private val dao: UsersAccountsDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersAccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersAccountViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
