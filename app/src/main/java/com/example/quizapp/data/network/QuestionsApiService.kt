package com.example.quizapp.data.network

import com.example.quizapp.data.db.entity.QuestionsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface QuestionsApiService {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("api.php")
    fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ) : Call<QuestionsResponse>

//    @GET("api.php")
//    suspend fun getQuestions(
//        @Query("amount") amount: Int,
//        @Query("category") category: Int,
//        @Query("difficulty") difficulty: String,
//        @Query("type") type: String
//    ): Response<QuestionsResponse>

//    companion object {
//        operator fun invoke(
//            connectivityInterceptor: ConnectivityInterceptor
//        ): QuestionsApiService {
//
//            val requestInterceptor = Interceptor {
//                chain ->
//                val url = chain.request()
//                    .url()
//                    .newBuilder()
//                    .build()
//                val request = chain.request()
//                    .newBuilder()
//                    .build()
//
//                return@Interceptor chain.proceed(request)
//            }
//
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(requestInterceptor)
//                .addInterceptor(connectivityInterceptor)
//                .build()
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl("https://opentdb.com/")
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(QuestionsApiService::class.java
//                )
//        }
//
//    }

}