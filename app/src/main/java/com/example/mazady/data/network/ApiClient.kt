package com.example.mazady.data.network
import com.example.mazady.data.network.response.MainCategoriesResponse
import com.example.mazady.data.network.response.OptionChildResponse
import com.example.mazady.data.network.response.PropertiesResponse


import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.security.PrivateKey


interface ApiClient {

    @GET("api/v1/get_all_cats")
    fun getMainCategories(
        @Header("Accept-Language") lang: String,
        @Header("private-key") privateKey: String,
    ): Observable<MainCategoriesResponse>

    @GET("api/v1/properties")
    fun getProperties(
        @Header("Accept-Language") lang: String,
        @Header("private-key") privateKey: String,
        @Query("cat") cat:Int
    ): Observable<PropertiesResponse>



    @GET()
    fun getOptionChild(
        @Url url: String,
        @Header("Accept-Language") lang: String,
        @Header("private-key") privateKey: String,

    ): Observable<OptionChildResponse>





    @GET("api/v1/get_all_cats")
    suspend fun testedGetMainCategories(
        @Header("Accept-Language") lang: String,
        @Header("private-key") privateKey: String,
    ): Response<MainCategoriesResponse>

}