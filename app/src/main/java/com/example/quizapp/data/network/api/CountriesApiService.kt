package com.example.quizapp.data.network.api

import com.example.quizapp.data.network.response.countries.CountriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CountriesApiService {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("rest/v2/all")
    fun getCountries() : Call<CountriesResponse>

}