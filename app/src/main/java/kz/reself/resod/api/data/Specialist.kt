package kz.reself.resod.api.data

data class Specialist (
    val id: Long,
    val firstName: String,
    val lastName: String,
    val status: String,
    val address: String,
    val imgUrl: Int,
    val email: String
 )