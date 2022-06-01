package kz.reself.resod.api.service

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus
import kz.reself.resod.api.data.*
import kz.reself.resod.api.model.AdData
import kz.reself.resod.api.model.BuildingDTO
import kz.reself.resod.api.model.CompanyDTO
import kz.reself.resod.api.model.SpecialistDTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AdDataInterface {

    @GET("/resod-management/api/v1/public/ad-data/sortedApproved")
    fun getSortedData():Call<Map<String, List<AdData>>>

    @GET("/resod-management/api/v1/public/ad-data/paginationApproved")
    fun getAllBuilding(@QueryMap options: Map<String, String>):Call<BuildingDTO>

    @GET("/resod-management/api/v1/public/ad-data/orgId/{id}")
    fun getAllByOrgId(@Path(value = "id")id:Long):Call<AdData>

    @GET("/resod-management/api/v1/public/ad-data/{id}")
    fun getBuildingById(@Path(value = "id") id: Long): Call<Building>

    @GET("/resod-management/api/v1/public/organization/all")
    fun getAllCompany():Call<CompanyDTO>

    @GET("/resod-management/api/v1/public/org-image/orgId/{id}")
    fun getCompanyImg(@Path(value = "id")id:Long):Call<List<CompanyImg>>

    @GET("/resod-management/api/v1/public/employee/pagination")
    fun getAllSpecialists():Call<SpecialistDTO>

    @GET("/resod-management/api/v1/public/employee/{id}")
    fun getSpecialistById(@Path(value = "id")id:String):Call<Specialist>

    @GET("/resod-management/api/v1/public/employee/email/{email}")
    fun getSpecialistByEmail(@Path(value = "email") email: String): Call<Specialist>

    @POST("/api/public/user/v1/signup")
    fun registrationUser(@Body registrationForm: RegistrationForm):Call<User>

    @POST("/api/public/user/v1/login")
    fun login(@Body loginForm: LoginForm): Call<LoginResponse>

    @GET("/api/public/user/v1/users/email/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<User>

    @POST("/api/public/user/v1/login/api/logout")
    fun logoutUser(@Header("x-auth-token") token: String?): Call<String>

    @GET("/chat-service/api/v1/private/conversation/author/{email}")
    fun getAllConversationByAuthor(
        @Header("x-auth-token") token: String?,
        @Path("email") email: String?
    ): Call<List<ChatUser>>

    @GET("/resod-management/api/v1/private/ad-client/pagination")
    fun getUserFavorites(@Header("x-auth-token") token: String?, @QueryMap options: Map<String, String>): Call<FavoritesPagination>

    @DELETE("/resod-management/api/v1/private/ad-client/delete/{buildingId}/{clientId}")
    fun deleteFavorite(
        @Header("x-auth-token") token: String?,
        @Path("buildingId") buildingId: Long,
        @Path("clientId") clientId: Long?): Call<ResponseBody>

    @POST("/resod-management/api/v1/private/ad-client")
    fun addFavorite(@Header("x-auth-token") token: String?, @Body body: FavoritesPaginationContentDTO): Call<FavoritesPaginationContent>
}