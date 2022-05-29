package kz.reself.resod.api.data

import kz.reself.resod.api.model.AdImage

data class Building(
    val id: Long,
    val name: String,
    val price: Int,
    val area: Double,
    val floorCount: Int,
    val description: String,
    val images: List<AdImage>,
    val organization: Organization
)
