package com.ahmad.githubuserapplication.di

import android.content.Context
import com.ahmad.githubuserapplication.data.UsersRepository
import com.ahmad.githubuserapplication.data.local.room.UserDatabase
import com.ahmad.githubuserapplication.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UsersRepository{
        val database = UserDatabase.getInstance(context)
        val apiService = ApiConfig.getApiService()
        val userDao = database.userDao()
        return UsersRepository.getInstance(userDao, apiService)
    }
}