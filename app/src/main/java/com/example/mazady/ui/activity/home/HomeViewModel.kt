package com.example.mazady.ui.activity.home

import androidx.lifecycle.ViewModel
import com.example.mazady.data.network.response.MainCategoriesResponse
import com.example.mazady.data.network.response.OptionChildResponse
import com.example.mazady.data.network.response.PropertiesResponse
import com.example.mazady.data.repo.AdminRepo


import io.reactivex.Observable

class HomeViewModel(private val adminRepo: AdminRepo) : ViewModel() {


    fun getMainCategories(): Observable<MainCategoriesResponse> = adminRepo.getMainCategories()
    fun getProperties(catId:Int): Observable<PropertiesResponse> = adminRepo.getProperties(catId)
    fun getOptionChild(optionId:Int): Observable<OptionChildResponse> = adminRepo.getOptionChild(optionId)


}