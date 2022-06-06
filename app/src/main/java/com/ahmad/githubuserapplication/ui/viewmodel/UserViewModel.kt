package com.ahmad.githubuserapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ahmad.githubuserapplication.data.UsersRepository

class UserViewModel(private val usersRepository: UsersRepository): ViewModel() {

    fun getUsers() = usersRepository.getUsers()

    fun insertSearchUser(login: String) = usersRepository.insertSearchUser(login)

    fun getSearchUser(login: String?) = usersRepository.getSearchUser(login)
}