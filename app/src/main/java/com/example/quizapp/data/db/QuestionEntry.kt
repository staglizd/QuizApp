package com.example.quizapp.data.db

import com.google.gson.annotations.SerializedName

interface QuestionEntry {
    val category: String
    val correctAnswer: String
    val difficulty: String
    val incorrectAnswers: List<String>
    val question: String
    val type: String
}