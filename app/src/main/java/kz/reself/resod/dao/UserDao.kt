package kz.reself.resod.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.reself.resod.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(userEntity: UserEntity)

    @Query("SELECT * FROM users ORDER BY idKey ASC")
    fun readAllData(): LiveData<List<UserEntity>?>
}