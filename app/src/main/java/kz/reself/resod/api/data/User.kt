package kz.reself.resod.api.data

data class User (
    val id: Long,
    val name: String,
    val surname: String,
    val middlename: String,
    val email: String,
    val role: String,
    val password: String,
    val loginType: String,
    val storageUrl: String,
    val errorRef: String,
    val birthDate: String,
    val phoneNumber: String,
    val city: String,
    val country: String,
)