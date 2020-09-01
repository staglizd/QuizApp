package com.example.quizapp.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapp.R
import com.example.quizapp.data.network.api.CountriesApiService
import com.example.quizapp.data.network.api.QuestionsApiService
import com.example.quizapp.data.network.response.countries.CountriesResponse
import com.example.quizapp.data.network.response.countries.CountriesResponseItem
import com.example.quizapp.helpers.Constants
import com.example.quizapp.model.Question
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

const val BASE_URL_QUESTIONS = "https://opentdb.com/"
const val BASE_URL_COUNTRIES = "https://restcountries.eu/"

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener, CoroutineScope by MainScope(){

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mCountriesList: ArrayList<CountriesResponseItem>? = null
    private var mQuestionLoaded: List<com.example.quizapp.data.db.entity.Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null
    private var mCategory: Int? = 0
    private var mCanAnswer: Boolean = true
    private var mTotalQuestions: Int? = 0
    private var mDifficulty: String? = ""

    var apiService: QuestionsApiService? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mCategory = intent.getIntExtra(Constants.CATEGORY_ID, 0)
        mTotalQuestions = intent.getIntExtra(Constants.NUMBER_OF_QUESTIONS, 0)
        mDifficulty = intent.getStringExtra(Constants.DIFFICULTY)

        Log.e("QuizActivity-START:", "User: ${mUserName}, Category: ${mCategory}, Questions: ${mTotalQuestions}, Difficulty: ${mDifficulty}")

        // Flags (1), others new API
        if (mCategory == 1) {
            // Flags
            getCountries()
            //getQuestionsByCategory(mCategory!!, 10)
        } else {
            // New API service call trivia
            getQuestions(mTotalQuestions!!, mCategory!!, mDifficulty!!.toLowerCase(), "multiple")
        }

        //mQuestionList = Constants.getQuestions()

        tv_option_one.setOnClickListener (this)
        tv_option_two.setOnClickListener (this)
        tv_option_three.setOnClickListener (this)
        tv_option_four.setOnClickListener (this)

    }

    private fun setQuestion() {

        val question = mQuestionList!!.get(mCurrentPosition - 1)

        defaultOptionsView()

        if (mCurrentPosition == mQuestionList!!.size) {
            btn_submit.text = getString(R.string.quiz_end)
        } else {
            btn_submit.text = getString(R.string.quiz_answer_submit)
        }

        progressBar.progress = mCurrentPosition
        progressBar.max = mTotalQuestions!!
        tv_progress.text = "$mCurrentPosition/${progressBar.max}"

        tv_question.text = question!!.getQuestion()

        // Add image with Picasso
        Log.e("QuizActivity-Image:", question.getImageUrl())
        if (question!!.getImageUrl() != "") {
            if (question!!.getImageUrl()!!.substringAfterLast(".").equals("svg")) {
                // Vector load
                GlideToVectorYou.justLoadImage(this, Uri.parse(question!!.getImageUrl()), iv_image)
            } else {
                Picasso.get().load(question!!.getImageUrl()).into(iv_image)
            }

        }

        tv_option_one.text = question!!.getOptionOne()
        tv_option_two.text = question!!.getOptionTwo()
        tv_option_three.text = question!!.getOptionThree()
        tv_option_four.text = question!!.getOptionFour()

        btn_submit.setOnClickListener(this)

        // Omogućiti odgovaranje
        mCanAnswer = true
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(tv_option_one, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(tv_option_two, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(tv_option_three, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(tv_option_four, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        } else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.DIFFICULTY, mDifficulty)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    if (mCanAnswer) {
                        val question = mQuestionList?.get(mCurrentPosition - 1)
                        if (question!!.getCorrectAnswer() != mSelectedOptionPosition) {
                            // Krivi odgovor
                            answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                        } else {
                            // Točan odgovor
                            mCorrectAnswers++
                        }
                        answerView(question!!.getCorrectAnswer()!!.toInt(),
                            R.drawable.correct_option_border_bg
                        )

                        if (mCurrentPosition == mQuestionList!!.size) {
                            btn_submit.text = "${getString(R.string.quiz_end)}"
                        } else {
                            btn_submit.text = "${getString(R.string.quiz_next_question)}"
                        }
                        mSelectedOptionPosition = 0
                        mCanAnswer = false
                    }

                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum:Int) {
        if (mCanAnswer) {

            defaultOptionsView()
            mSelectedOptionPosition = selectedOptionNum

            tv.setTextColor(Color.parseColor("#363A43"))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)

        }
    }

    private fun getQuestionsByCategory(categoryId: Int, maxQuestions: Int){

        mQuestionList = ArrayList<Question>()

        val refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").limitToFirst(maxQuestions)

        refQuestions!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                mQuestionList!!.clear()

                for (snapshot in p0.children) {
                    val question = snapshot.getValue(Question::class.java)
                    if(question!!.getCategory()!!.toInt() == categoryId) {
                        mQuestionList!!.add(question!!)
                    }
                }

                Collections.shuffle(mQuestionList)

                setQuestion()

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }

    // New API service for countries
    private fun getCountries() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL_COUNTRIES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApiService::class.java)

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = api.getCountries().awaitResponse()
                if(response.isSuccessful) {

                    mCountriesList = ArrayList<CountriesResponseItem>()
                    mQuestionList = ArrayList<Question>()

                    // Save objects
                    val data = response.body()!!
                    mCountriesList = data

                    Collections.shuffle(mCountriesList)

                    var autoId = 0
                    for (country in mCountriesList as CountriesResponse) {
                        autoId++

                        val ctrQuestion: Question
                        // Set random correct answer
                        val rnds = (1..4).random()

                        when (rnds) {
                            1 -> ctrQuestion = Question(autoId, "1",
                                "What country does this flag belong to?", country.flag,
                                country.name, (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name, 1)

                            2 -> ctrQuestion = Question(autoId, "1",
                                "What country does this flag belong to?", country.flag,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name, country.name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name, 2)

                            3 -> ctrQuestion = Question(autoId, "1",
                                "What country does this flag belong to?", country.flag,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name, country.name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name, 3)

                            4 -> ctrQuestion = Question(autoId, "1",
                                "What country does this flag belong to?", country.flag,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name, country.name, 4)

                            else -> ctrQuestion = Question(autoId, "1",
                                "What country does this flag belong to?", country.flag,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name,
                                (mCountriesList as CountriesResponse)[(1..(mCountriesList as CountriesResponse).size-1).shuffled().first()].name, country.name, 4)
                        }

                        mQuestionList!!.add(ctrQuestion)

                        if (autoId == mTotalQuestions) {
                            break
                        }
                    }

                    Log.e("QuizActivity-C:", data.toString())

                    withContext(Dispatchers.Main) {

                        setQuestion()

                    }
                } else {
                    Toast.makeText(this@QuizQuestionsActivity, "Error", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    //Toast.makeText(applicationContext, "Please try again later, there is no internet connection!", Toast.LENGTH_LONG).show()
                    Toast.makeText(this@QuizQuestionsActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

        }
    }

    // New API service call trivia
    private fun getQuestions(amount: Int, categoryId: Int, difficulty: String, type: String) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL_QUESTIONS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionsApiService::class.java)

        GlobalScope.launch (Dispatchers.IO) {
            try {
                val response = api.getQuestions(amount, categoryId, difficulty, type).awaitResponse()
                if(response.isSuccessful) {

                    if (response.body()!!.responseCode != 0) {
                        Toast.makeText(this@QuizQuestionsActivity, "Please try again, there is no enough questions in selected category!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        val data = response.body()!!

                        // TODO Convert questions
                        mQuestionList = ArrayList<Question>()

                        var autoId = 0;
                        for (question in data.results) {
                            autoId++
                            mQuestionList!!.add(setRandomQuestion(autoId, question))
                        }

                        withContext(Dispatchers.Main) {

                            setQuestion()

                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    //Toast.makeText(applicationContext, "Please try again later, there is no internet connection!", Toast.LENGTH_LONG).show()
                    Toast.makeText(this@QuizQuestionsActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

        }
    }

    private fun setRandomQuestion(autoId: Int, question: com.example.quizapp.data.db.entity.Question): Question {

        val newQuestion: Question

        // Set random correct answer
        val rnds = (1..4).random()

        when (rnds) {
            1 -> newQuestion = Question(autoId, question.category,
                question.question.replace("&#039;", "'").replace("&quot;", "\"")
                    .replace("&rsquo;", "'"), "",
                question.correctAnswer, question.incorrectAnswers[0], question.incorrectAnswers[1], question.incorrectAnswers[2], 1)

            2 -> newQuestion = Question(autoId, question.category,
                question.question.replace("&#039;", "'").replace("&quot;", "\"")
                    .replace("&rsquo;", "'"), "",
                question.incorrectAnswers[0], question.correctAnswer, question.incorrectAnswers[1], question.incorrectAnswers[2], 2)

            3 -> newQuestion = Question(autoId, question.category,
                question.question.replace("&#039;", "'").replace("&quot;", "\"")
                    .replace("&rsquo;", "'"), "",
                question.incorrectAnswers[0], question.incorrectAnswers[1], question.correctAnswer, question.incorrectAnswers[2], 3)

            4 -> newQuestion = Question(autoId, question.category,
                question.question.replace("&#039;", "'").replace("&quot;", "\"")
                    .replace("&rsquo;", "'"), "",
                question.incorrectAnswers[0], question.incorrectAnswers[1], question.incorrectAnswers[2], question.correctAnswer, 4)

            else -> newQuestion = Question(autoId, question.category,
                question.question.replace("&#039;", "'").replace("&quot;", "\"")
                    .replace("&rsquo;", "'"), "",
                question.incorrectAnswers[0], question.incorrectAnswers[1], question.incorrectAnswers[2], question.correctAnswer, 4)
        }

        return newQuestion

    }
}