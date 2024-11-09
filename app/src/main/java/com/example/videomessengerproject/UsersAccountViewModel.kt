package com.example.videomessengerproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UsersAccountViewModel (val dao: UsersAccountsDatabase) : ViewModel() {
    var newUsersName = ""

    fun addUser(user: UsersAccounts) {
        viewModelScope.launch {
            dao.userAccountsDao.insert(user)
        }
    }
}