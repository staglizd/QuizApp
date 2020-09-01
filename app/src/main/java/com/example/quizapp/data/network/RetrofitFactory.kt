package com.example.quizapp.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    const val BASE_URL = "https://opentdb.com/"

    fun makeRetrofitService(): QuestionsApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            //.addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionsApiService::class.java)
    }

}