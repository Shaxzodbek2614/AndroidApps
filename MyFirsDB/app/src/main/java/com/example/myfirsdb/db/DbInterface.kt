package com.example.myfirsdb.db

import com.example.myfirsdb.models.User

interface DbInterface {
    fun addUser(user: User)
    fun showUser():ArrayList<User>
    fun editUser(user: User)
    fun deleteUser(user: User)
}