package kz.reself.resod.api.model

import kz.reself.resod.api.data.Building

data class BuildingDTO(
    val content: List<Building>,
    val page: Int,
    val size: Int,
    val total: Int
)
