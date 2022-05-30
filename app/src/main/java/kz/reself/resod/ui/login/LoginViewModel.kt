package kz.reself.resod.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.reself.resod.api.data.LoginResponse
import kz.reself.resod.db.AppDatabase
import kz.reself.resod.entity.UserEntity
import kz.reself.resod.repository.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<UserEntity>?>
//    var loginStatus: LiveData<String>
    private val userRepository: UserRepository

    init {
        val userDao = AppDatabase.getAppDatabase(application).getUserDao()
        userRepository = UserRepository(userDao)
        readAllData = userRepository.readAllData
//        loginStatus = userRepository.userIsLogin()
    }

    fun add(loginResponse: LoginResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.add(loginResponse.toUserEntity())
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteAll()
        }
    }
}