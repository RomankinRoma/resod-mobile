package kz.reself.resod.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.reself.resod.dao.BuildingCardDao
import kz.reself.resod.entity.BuildingCardEntity

class BuildingCardRepository(private val buildingCardDao: BuildingCardDao) {
    val readAllData: LiveData<List<BuildingCardEntity>?> = buildingCardDao.readAllData()

    suspend fun add(buildingCardEntity: BuildingCardEntity) {
        buildingCardDao.addBuildingCard(buildingCardEntity)
    }

    suspend fun deleteAll() {
        buildingCardDao.deleteAllBuildingCards()
    }

    suspend fun deleteById(id: Long) {
        buildingCardDao.deleteById(id)
    }

    fun getById(id: Long) = buildingCardDao.getById(id)
}