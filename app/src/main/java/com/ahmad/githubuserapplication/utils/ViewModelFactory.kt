package com.ahmad.githubuserapplication.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmad.githubuserapplication.data.UsersRepository
import com.ahmad.githubuserapplication.di.Injection
import com.ahmad.githubuserapplication.ui.viewmodel.UserViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(
    private val usersRepository: UsersRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(usersRepository) as T
        }
        throw IllegalArgumentException("Unknow Viewmodel class ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}