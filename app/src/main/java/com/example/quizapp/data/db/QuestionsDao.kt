package com.example.quizapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quizapp.data.db.entity.Question
import com.example.quizapp.data.db.entity.QuestionsResponse

@Dao
interface QuestionsDao {

    // Insert & Update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(questionsResponse: Question)

    @Query("SELECT * FROM questions")
    fun getQuestions(amount: Int,
                     category: Int,
                     difficulty: String,
                     type: String): LiveData<QuestionEntryImpl>
}