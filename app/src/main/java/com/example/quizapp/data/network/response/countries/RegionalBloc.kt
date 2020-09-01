package com.example.quizapp.data.network.response.countries


import com.google.gson.annotations.SerializedName

data class RegionalBloc(
    val acronym: String,
    val name: String,
    val otherAcronyms: List<Any>,
    val otherNames: List<String>
)