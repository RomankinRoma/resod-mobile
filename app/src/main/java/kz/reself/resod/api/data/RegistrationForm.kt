package kz.reself.resod.api.data

data class RegistrationForm (
    val name: String,
    val surname: String,
    val password: String,
    val email: String,
    val role: String
)
