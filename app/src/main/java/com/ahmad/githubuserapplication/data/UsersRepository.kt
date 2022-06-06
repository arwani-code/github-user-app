package com.ahmad.githubuserapplication.data

import android.util.Log
import androidx.lifecycle.*
import com.ahmad.githubuserapplication.data.local.entity.UserEntity
import com.ahmad.githubuserapplication.data.local.room.UserDao
import com.ahmad.githubuserapplication.data.remote.response.ItemsItem
import com.ahmad.githubuserapplication.data.remote.response.SearchUserResponse
import com.ahmad.githubuserapplication.data.remote.retrofit.ApiService

class UsersRepository private constructor(
    private val userDao: UserDao,
    private val apiService: ApiService
) {

    fun getUsers(): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val userItem = apiService.getAllUsers()
            val newUsers = userItem.map { user ->
                val isFavorite = userDao.isFavoriteUser(user.login)
                UserEntity(
                    id = user.id,
                    login = user.login,
                    avatar_url = user.avatarUrl,
                    isFavorite = isFavorite
                )
            }
            userDao.deleteAllUsers()
            userDao.insertUser(newUsers)
        } catch (ex: Exception) {
            emit(Result.Error(ex.message.toString()))
        }
        val localData: LiveData<Result<List<UserEntity>>> =
            userDao.getAllUsers().map { Result.Success(it) }
        emitSource(localData)
    }

    fun insertSearchUser(username: String): LiveData<Result<List<UserEntity>>> = liveData {
        Log.i("TESTRESPONSE", "SJNJSNDsk")
        emit(Result.Loading)
        try {
            val listItems = apiService.getSearchUser(username)
            val listUsers = listItems.items
            val newUser = listUsers?.map { user ->
                val isFavorite = userDao.isFavoriteUser(user?.login)
                UserEntity(
                    id = user?.id,
                    login = user?.login,
                    avatar_url = user?.avatarUrl,
                    isFavorite = isFavorite
                )
            }
            userDao.insertUser(newUser)
        }catch (ex: Exception){
            emit(Result.Error(ex.message.toString()))
        }
    }

    fun getSearchUser(login: String?): LiveData<Result<List<UserEntity>>> = liveData {
        val getUser: LiveData<Result<List<UserEntity>>> =
            userDao.getSearchUser(login).map { Result.Success(it) }
        emitSource(getUser)
    }

    fun getDetailUser(username: String): LiveData<List<UserEntity>> = liveData {
        val user =
            apiService.getDetailUser(username, "token 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")
        val isFavorite = userDao.isFavoriteUser(user.login)
        val userItem = UserEntity(
            user.id,
            user.login,
            user.name,
            user.avatarUrl,
            user.email,
            user.location,
            isFavorite
        )
        userDao.updateUser(userItem)
    }

    companion object {
        @Volatile
        private var instance: UsersRepository? = null
        fun getInstance(
            userDao: UserDao,
            apiService: ApiService
        ): UsersRepository =
            instance ?: synchronized(this) {
                instance ?: UsersRepository(userDao, apiService)
            }.also { instance = it }
    }
}