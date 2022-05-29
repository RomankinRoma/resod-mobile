package kz.reself.resod.repository

import androidx.lifecycle.LiveData
import kz.reself.resod.dao.UserDao
import kz.reself.resod.entity.UserEntity

class UserRepository (private val userDao: UserDao) {
    val readAllData: LiveData<List<UserEntity>?> = userDao.readAllData()

    suspend fun add(userEntity: UserEntity) {
        userDao.addUser(userEntity)
    }
}