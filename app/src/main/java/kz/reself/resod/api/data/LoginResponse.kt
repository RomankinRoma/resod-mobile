package kz.reself.resod.api.data

import kz.reself.resod.entity.UserEntity

data class LoginResponse(
    val token: String,
    val id: Long,
    val name: String,
    val password: String,
    val email: String,
    val role: String,
    val loginType: String
) {
    fun toUserEntity(): UserEntity = UserEntity(
        idKey = 0,
        token = token ?: "",
        id = id,
        name = name ?: "",
        password = password ?: "",
        email = email ?: "",
        role = role ?: "",
        userLoginType = loginType ?: ""
    )
}
