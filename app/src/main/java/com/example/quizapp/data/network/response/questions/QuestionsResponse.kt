package com.example.quizapp.data.network.response.questions


import com.google.gson.annotations.SerializedName

data class QuestionsResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    val results: List<Question>
)