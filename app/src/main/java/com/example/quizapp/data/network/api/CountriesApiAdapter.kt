package com.example.quizapp.data.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountriesApiAdapter {

    val countriesApiService: CountriesApiService = Retrofit.Builder()
        .baseUrl("http://restcountries.eu/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CountriesApiService::class.java)
}