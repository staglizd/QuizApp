package com.example.quizapp.model

class Users {
    private var uid: String = ""
    private var username: String = ""
    private var points: Int = 0

    constructor()

    constructor(
        uid: String,
        username: String,
        points: Int
    ) {
        this.uid = uid
        this.username = username
        this.points = points
    }

    fun getUid():String? {
        return uid
    }

    fun setUid(uid: String) {
        this.uid = uid
    }

    fun getUsername():String? {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getPoints():Int? {
        return points
    }

    fun setPoints(points: Int) {
        this.points = points
    }




}