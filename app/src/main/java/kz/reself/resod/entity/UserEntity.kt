package kz.reself.resod.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kz.reself.resod.api.data.User

@Entity(
    tableName = "users",
    indices = [
        Index("email", unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val idKey: Long,
    val token: String,
    val id: Long,
    val name: String,
    val password: String,
    val email: String,
    val role: String,
    val userLoginType: String
) {
    fun toUser(): User = User(
        id = id,
        email = email,
        name =  name,
        password = password,
        role = role,
        loginType = userLoginType,
        surname = "",
        middlename = "",
        storageUrl = "",
        errorRef = ""
    )

    companion object {
        fun fromUser(user: User): UserEntity = UserEntity(
            idKey = 0,
            token = "",
            id = user.id,
            name = user.name,
            password = user.password,
            email = user.email,
            role = user.role,
            userLoginType = user.loginType
        )
    }
}
