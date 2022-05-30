package kz.reself.resod.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kz.reself.resod.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(userEntity: UserEntity)

    @Query("SELECT * FROM users ORDER BY idKey ASC")
    fun readAllData(): LiveData<List<UserEntity>?>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}