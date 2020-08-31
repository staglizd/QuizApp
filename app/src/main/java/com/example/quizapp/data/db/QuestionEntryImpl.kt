package com.example.quizapp.data.db

import com.google.gson.annotations.SerializedName

data class QuestionEntryImpl (
    override val category: String,
    override val correctAnswer: String,
    override val difficulty: String,
    override val incorrectAnswers: List<String>,
    override val question: String,
    override val type: String
): QuestionEntry