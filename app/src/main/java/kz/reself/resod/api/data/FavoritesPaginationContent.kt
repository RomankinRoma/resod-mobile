package kz.reself.resod.api.data

data class FavoritesPaginationContent(
    val id: Long,
    val adId: Long,
    val clientId: Long,
    var building: Building
)
