package com.ahmad.githubuserapplication.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ahmad.githubuserapplication.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM github_users ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM github_users WHERE favorite = 1")
    fun getFavoriteUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM github_users WHERE login LIKE :login")
    fun getSearchUser(login: String?): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: List<UserEntity>?)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM github_users WHERE favorite = 0")
    suspend fun deleteAllUsers()

    @Query("SELECT EXISTS(SELECT * FROM github_users WHERE name = :name AND favorite = 1)")
    suspend fun isFavoriteUser(name: String?): Boolean
}