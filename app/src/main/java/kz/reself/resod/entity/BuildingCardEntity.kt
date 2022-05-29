package kz.reself.resod.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.reself.resod.api.data.Building
import kz.reself.resod.api.data.Organization
import kz.reself.resod.api.model.AdImage
import java.util.*

@Entity(
    tableName = "building_cards"
)
data class BuildingCardEntity(
    @PrimaryKey(autoGenerate = true)
    val idKey: Long,
    val id: Long,
    val name: String,
    val price: Int,
    val area: Double,
    val floorCount: Int,
    val description: String,
    val imageUrl: String,
    val organizationName: String,
    val createdDate: Long
) {

    fun toBuilding(): Building = Building(
        id = id,
        name = name,
        price = price,
        area = area,
        floorCount = floorCount,
        description = description,
        images = listOf(
            AdImage(
                id = 0,
                name = "",
                storageUrl = imageUrl,
                size = 0,
                adId = 0,
                fileExtension = "",
                bucket = ""
            )
        ),
        organization = Organization(
            name = organizationName
        )
    )

    companion object {
        fun fromBuildingCard(building: Building): BuildingCardEntity = BuildingCardEntity(
            idKey = 0,
            id = building.id,
            name = building.name,
            price = building.price,
            area = building.area,
            floorCount = building.floorCount,
            description = building.description,
            imageUrl = building.images[0].storageUrl,
            organizationName = building.organization.name,
            createdDate = System.currentTimeMillis()
        )
    }
}