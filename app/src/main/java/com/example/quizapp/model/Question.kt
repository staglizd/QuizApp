package com.example.quizapp.model

class Question {
    private var id: Int = 0
    private var category: String = ""
    private var question: String = ""
    private var imageUrl: String = ""
    private var optionOne: String = ""
    private var optionTwo: String = ""
    private var optionThree: String = ""
    private var optionFour: String = ""
    private var correctAnswer: Int = 0

    constructor()

    constructor(
        id: Int,
        category: String,
        question: String,
        imageUrl: String,
        optionOne: String,
        optionTwo: String,
        optionThree: String,
        optionFour: String,
        correctAnswer: Int
    ) {
        this.id = id
        this.category = category
        this.question = question
        this.imageUrl = imageUrl
        this.optionOne = optionOne
        this.optionTwo = optionTwo
        this.optionThree = optionThree
        this.optionFour = optionFour
        this.correctAnswer = correctAnswer
    }

    fun getId():Int? {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getCategory():String? {
        return category
    }

    fun setCategory(category: String) {
        this.category = category
    }

    fun getQuestion():String? {
        return question
    }

    fun setQuestion(question: String) {
        this.question = question
    }

    fun getImageUrl():String? {
        return imageUrl
    }

    fun setImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
    }

    fun getOptionOne():String? {
        return optionOne
    }

    fun setOptionOne(optionOne: String) {
        this.optionOne = optionOne
    }

    fun getOptionTwo():String? {
        return optionTwo
    }

    fun setOptionTwo(optionTwo: String) {
        this.optionTwo = optionTwo
    }

    fun getOptionThree():String? {
        return optionThree
    }

    fun setOptionThree(optionThree: String) {
        this.optionThree = optionThree
    }

    fun getOptionFour():String? {
        return optionFour
    }

    fun setOptionFour(optionFour: String) {
        this.optionFour = optionFour
    }

    fun getCorrectAnswer():Int? {
        return correctAnswer
    }

    fun setCorrectAnswer(correctAnswer: Int) {
        this.correctAnswer = correctAnswer
    }


}