package com.example.mazady

import com.example.mazady.data.network.ApiClient
import com.example.mazady.util.Constants
import com.google.gson.GsonBuilder
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CategoriesTest {
    private lateinit var apiClient: ApiClient

    private lateinit var mockWebServer: MockWebServer



    @Before
    fun setUp(){

        mockWebServer =  MockWebServer()
        apiClient = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiClient::class.java)
    }



    @Test
    fun testCategories() = runTest {

        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = apiClient.testedGetMainCategories("en","3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?\$@+16")
        mockWebServer.takeRequest()
        Assert.assertEquals(true,response)


    }


    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}