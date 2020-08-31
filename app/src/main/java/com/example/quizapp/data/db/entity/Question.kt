package com.example.quizapp.data.db.entity


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "questions")
data class Question(
    val category: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    val difficulty: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>,
    val question: String,
    val type: String
)