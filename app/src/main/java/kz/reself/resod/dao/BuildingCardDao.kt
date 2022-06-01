package kz.reself.resod.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.reself.resod.entity.BuildingCardEntity

@Dao
interface BuildingCardDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBuildingCard(buildingCardEntity: BuildingCardEntity)

    // SELECT
    @Query("SELECT * FROM building_cards ORDER BY createdDate DESC")
    fun readAllData(): LiveData<List<BuildingCardEntity>?>

    @Query("SELECT * FROM building_cards WHERE id = :id")
    fun getById(id: Long): LiveData<BuildingCardEntity?>

    // DELETE
    @Query("DELETE FROM building_cards")
    suspend fun deleteAllBuildingCards()

    @Query("DELETE FROM building_cards WHERE id = :id")
    suspend fun deleteById(id: Long)
}