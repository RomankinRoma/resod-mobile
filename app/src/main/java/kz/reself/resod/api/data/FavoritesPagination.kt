package kz.reself.resod.api.data

data class FavoritesPagination(
    val content: List<FavoritesPaginationContent>,
    val page: Int,
    val size: Int,
    val total: Int
)