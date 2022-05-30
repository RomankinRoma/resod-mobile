package kz.reself.resod.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.reself.resod.entity.BuildingCardEntity

@Dao
interface BuildingCardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBuildingCard(buildingCardEntity: BuildingCardEntity)

    @Query("SELECT * FROM building_cards ORDER BY createdDate DESC")
    fun readAllData(): LiveData<List<BuildingCardEntity>?>

    @Query("DELETE FROM building_cards")
    suspend fun deleteAllBuildingCards()
}