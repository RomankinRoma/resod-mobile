package kz.reself.resod.ui.favorites

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.reself.resod.db.AppDatabase
import kz.reself.resod.entity.BuildingCardEntity
import kz.reself.resod.repository.BuildingCardRepository
import kz.reself.resod.repository.UserRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<BuildingCardEntity>?>
    private val repository: BuildingCardRepository

    init {
        val dao = AppDatabase.getAppDatabase(application).getBuildingCardDao()
        repository = BuildingCardRepository(dao)
        readAllData = repository.readAllData
    }

    fun add(buildingCardEntity: BuildingCardEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(buildingCardEntity)
        }
    }
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is sale Fragment"
//    }
//    val text: LiveData<String> = _text


}