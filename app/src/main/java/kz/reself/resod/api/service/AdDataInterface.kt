package kz.reself.resod.api.service

import kz.reself.resod.api.model.AdData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdDataInterface {

    @GET("/resod-management/api/v1/public/ad-data/sortedApproved")
    fun getSortedData():Call<Map<String,List<AdData>>>

    @GET("/resod-management/api/v1/public/ad-data")
    fun getAll():Call<List<AdData>>

    @GET("/resod-management/api/v1/public/ad-data/orgId/{id}")
    fun getAllByOrgId(@Path(value = "id")id:Long):Call<AdData>

}