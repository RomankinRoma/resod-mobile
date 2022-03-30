package kz.reself.resod.api.data

data class Building(
    val companyName: String,
    val price: Int,
    val area: Double,
    val numberOfRooms: Int,
    val address: String,
    val description: String,
    val imgUrl: Int
)
