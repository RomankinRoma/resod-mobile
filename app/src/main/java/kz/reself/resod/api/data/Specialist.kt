package kz.reself.resod.api.data

data class Specialist (
    val id: Long,
    val firstName: String,
    val lastName: String,
    val storageUrl: String,
    val description: String,
    val status: String,
    val address: String,
    val email: String,
    val organization: Organization,
    val phoneNumber: String,
    val whatsappPhoneNumber: String,
    val apartmentCount: Int,
    val houseCount: Int,
    val commerceCount: Int
 )