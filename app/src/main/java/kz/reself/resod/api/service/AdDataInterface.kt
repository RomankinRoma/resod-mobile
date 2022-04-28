package kz.reself.resod.api.service

import kz.reself.resod.api.data.CompanyImg
import kz.reself.resod.api.data.Specialist
import kz.reself.resod.api.model.AdData
import kz.reself.resod.api.model.CompanyDTO
import kz.reself.resod.api.model.SpecialistDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AdDataInterface {

    @GET("/resod-management/api/v1/public/ad-data/sortedApproved")
    fun getSortedData():Call<Map<String,List<AdData>>>

    @GET("/resod-management/api/v1/public/ad-data")
    fun getAll():Call<List<AdData>>

    @GET("/resod-management/api/v1/public/ad-data/orgId/{id}")
    fun getAllByOrgId(@Path(value = "id")id:Long):Call<AdData>

    @GET("/resod-management/api/v1/public/organization/all")
    fun getAllCompany():Call<CompanyDTO>

    @GET("/resod-management/api/v1/public/org-image/orgId/{id}")
    fun getCompanyImg(@Path(value = "id")id:Long):Call<List<CompanyImg>>

    @GET("/resod-management/api/v1/public/employee/pagination")
    fun getAllSpecialists():Call<SpecialistDTO>

    @GET("/resod-management/api/v1/public/employee/{id}")
    fun getSpecialistById(@Path(value = "id")id:String):Call<Specialist>

}