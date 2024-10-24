package com.example.mazady.data.repo

import android.content.SharedPreferences
import com.example.mazady.data.network.ApiClient
import com.example.mazady.data.network.response.MainCategoriesResponse
import com.example.mazady.data.network.response.OptionChildResponse
import com.example.mazady.data.network.response.PropertiesResponse
import com.example.mazady.util.Constants
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import javax.inject.Inject

class AdminRepo @Inject constructor(

    private val apiClient: ApiClient,

) {

    fun getMainCategories(): Observable<MainCategoriesResponse> {
        return apiClient.getMainCategories(
            Constants.CURRENT_LANG,
            Constants.PRIVATE_KEY

        ).subscribeOn(Schedulers.io())
    }

    fun getProperties(catId: Int): Observable<PropertiesResponse> {
        return apiClient.getProperties(
            Constants.CURRENT_LANG,
            Constants.PRIVATE_KEY,
            catId

        ).subscribeOn(Schedulers.io())
    }


    fun getOptionChild(optionId: Int): Observable<OptionChildResponse> {
        return apiClient.getOptionChild(

            "${Constants.BASE_URL}api/v1/get-options-child/$optionId",
            Constants.CURRENT_LANG,
            Constants.PRIVATE_KEY


        ).subscribeOn(Schedulers.io())
    }



}