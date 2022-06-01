package kz.reself.resod.ui.favorites

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.reself.resod.APP_PREFERENCES
import kz.reself.resod.USER_ID_KEY
import kz.reself.resod.USER_LOGIN_STATUS_KEY
import kz.reself.resod.USER_TOKEN_KEY
import kz.reself.resod.api.data.Building
import kz.reself.resod.api.data.FavoritesPagination
import kz.reself.resod.api.data.FavoritesPaginationContent
import kz.reself.resod.api.data.FavoritesPaginationContentDTO
import kz.reself.resod.api.service.AdDataInterface
import kz.reself.resod.api.service.NetworkHandler
import kz.reself.resod.db.AppDatabase
import kz.reself.resod.entity.BuildingCardEntity
import kz.reself.resod.repository.BuildingCardRepository
import kz.reself.resod.repository.UserRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val retrofit = NetworkHandler.retrofit.create(AdDataInterface::class.java)
    private val appSharedPreferences = application.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    private val page: Int
    private val size: Int
    private val total: Int

    val readAllData: LiveData<List<BuildingCardEntity>?>
    private val repository: BuildingCardRepository

    val listFavorites: MutableLiveData<FavoritesPagination> = MutableLiveData()

    init {
        page = 0
        size = 0
        total = 0

        val dao = AppDatabase.getAppDatabase(application).getBuildingCardDao()
        repository = BuildingCardRepository(dao)

        val status = appSharedPreferences.getString(USER_LOGIN_STATUS_KEY, "")

        if (!status.equals("") && !status.equals("no")) {
            listFavorites.value = FavoritesPagination(listOf(), 0, 0, 0)

            readAllData = MutableLiveData()

            getFavoritesByRestApi()
            Log.w("FAVORITE_LIST","BY TOKEN")
        } else {
            readAllData = repository.readAllData
            Log.w("FAVORITE_LIST","BY DB")
        }
    }

    fun add(buildingCardEntity: BuildingCardEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(buildingCardEntity)
        }
    }

    fun getUserByIdInDb(id: Long) = repository.getById(id)

    fun add(buildingId: Long) {
        val token = appSharedPreferences.getString(USER_TOKEN_KEY, "")
        val userId = appSharedPreferences.getString(USER_ID_KEY, "")?.toLong()

        val body = FavoritesPaginationContentDTO(buildingId, userId)

        val responseAddF = retrofit.addFavorite(token, body)

        responseAddF.enqueue(object : Callback<FavoritesPaginationContent?> {
            override fun onResponse(call: Call<FavoritesPaginationContent?>, response: Response<FavoritesPaginationContent?>) {
                Log.w("ADD_FAVORITE","ADDED")
            }

            override fun onFailure(call: Call<FavoritesPaginationContent?>, t: Throwable) {
                Log.e("ADD_FAVORITE","ERROR:" + t.message)
            }
        })
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun deleteByIdKey(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteById(id)
        }
    }

    fun getFavoritesByRestApi() {
        val token = appSharedPreferences.getString(USER_LOGIN_STATUS_KEY, "")
        val userId = appSharedPreferences.getString(USER_ID_KEY, "")

        if (!token.equals("") && !userId.equals("")) {
            val options: HashMap<String, String> = HashMap()

            if (userId != null) {
                options["clientId"] = userId
            }

            val responseFavorites = retrofit.getUserFavorites(token, options)

            responseFavorites.enqueue(object : Callback<FavoritesPagination?> {
                override fun onResponse(call: Call<FavoritesPagination?>, response: Response<FavoritesPagination?>) {

                    if (response.isSuccessful) {
                        val list = response.body()!!
                        listFavorites.value = list

                        Log.w("FAVORITE_LIST","SET VAL REST")
                        Log.println(Log.INFO, "FAVORITE_LIST_VAL","LIST: " + Gson().toJson(response.body()))

                        for(index in listFavorites.value!!.content.indices) {
                            val favoriteElem = listFavorites.value!!.content.get(index)

                            getBuilding(index, favoriteElem.adId)
                        }
                    } else {
                        listFavorites.value = FavoritesPagination(listOf(), 0, 0, 0)
                    }
                }

                override fun onFailure(call: Call<FavoritesPagination?>, t: Throwable) {
                    Log.e("GET_COMPANY_IMG","ERROR:" + t.message)
                }
            })
        }
    }

    private fun getBuilding(index: Int, id: Long) {
        val responseBuilding = retrofit.getBuildingById(id)

        responseBuilding.enqueue(object : Callback<Building?> {
            override fun onResponse(call: Call<Building?>, response: Response<Building?>) {

                if (response.isSuccessful) {
                    var curBuilding = response.body()!!
                    curBuilding.isAddFavorites = true

                    listFavorites.value!!.content.get(index).building = curBuilding
                    Log.w("FAVORITE_LIST","SET ITEM id = " + index)
                    Log.println(Log.INFO, "FAVORITE_LIST", "ITEM = " + Gson().toJson(response.body()))
                    listFavorites.value = listFavorites.value
                }
            }

            override fun onFailure(call: Call<Building?>, t: Throwable) {
                Log.e("FAVORITE_LIST_ITEM","1ERROR:" + t.message)
            }
        })
    }

    fun deleteFavorite(buildingId: Long, updateList: Boolean) {
        val statusLogin = appSharedPreferences.getString(USER_LOGIN_STATUS_KEY, "")
        val token = appSharedPreferences.getString(USER_TOKEN_KEY, "")
        val clientId = appSharedPreferences.getString(USER_ID_KEY, "")?.toLong()

        if (!statusLogin.equals("") && !statusLogin.equals("no")) {
            val response = retrofit.deleteFavorite(token, buildingId, clientId)

            response.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    Log.w("FAVORITE_ITEM_DELETE","resoponse: " + Gson().toJson(response.code()))
                    if (response.isSuccessful) {
                        if (updateList) {
                            Log.w("FAVORITE_ITEM_DELETE","UPDATE")
                            getFavoritesByRestApi()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Log.e("FAVORITE_ITEM_DELETE","1ERROR:" + t.message)
                }
            })
        }
    }
}