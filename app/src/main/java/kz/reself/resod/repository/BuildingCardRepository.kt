package kz.reself.resod.repository

import androidx.lifecycle.LiveData
import kz.reself.resod.dao.BuildingCardDao
import kz.reself.resod.entity.BuildingCardEntity

class BuildingCardRepository(private val buildingCardDao: BuildingCardDao) {
    val readAllData: LiveData<List<BuildingCardEntity>?> = buildingCardDao.readAllData()

    suspend fun add(buildingCardEntity: BuildingCardEntity) {
        buildingCardDao.addBuildingCard(buildingCardEntity)
    }
}