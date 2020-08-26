package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapp.helpers.Constants
import com.example.quizapp.model.Question
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import java.util.*
import kotlin.collections.ArrayList


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null
    private var mCategory: Int? = 0
    private var mCanAnswer: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mCategory = intent.getIntExtra(Constants.CATEGORY_ID, 0)

        getQuestionsByCategory(mCategory!!, 10)

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
            btn_submit.text = "KRAJ"
        } else {
            btn_submit.text = "Pošalji"
        }

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition/${progressBar.max}"

        tv_question.text = question!!.getQuestion()

        // Add image with Picasso
        Picasso.get().load(question!!.getImageUrl()).into(iv_image)

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
                        answerView(question!!.getCorrectAnswer()!!.toInt(), R.drawable.correct_option_border_bg)

                        if (mCurrentPosition == mQuestionList!!.size) {
                            btn_submit.text = "KRAJ"
                        } else {
                            btn_submit.text = "Sljedeće pitanje"
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

        val refQuestions = FirebaseDatabase.getInstance().reference.child("Questions")

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
}