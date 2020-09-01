package com.example.quizapp.data.network.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuestionsApiAdapter {

    val questionsApiService: QuestionsApiService = Retrofit.Builder()
        .baseUrl("http://opentdb.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuestionsApiService::class.java)
}