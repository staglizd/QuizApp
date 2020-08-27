package com.example.quizapp.model

class Users {
    private var uid: String = ""
    private var username: String = ""
    private var points: Int = 0
    private var profile: String = ""
    private var registered: Long = 0
    private var played: Int = 0
    private var name: String = ""

    constructor()

    constructor(
        uid: String,
        username: String,
        points: Int,
        profile: String,
        registered: Long,
        played: Int,
        name: String
    ) {
        this.uid = uid
        this.username = username
        this.points = points
        this.profile = profile
        this.registered = registered
        this.played = played
        this.name = name
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


    fun getProfile():String? {
        return profile
    }

    fun setProfile(profile: String) {
        this.profile = profile
    }


    fun getRegistered(): Long? {
        return registered
    }

    fun setRegistered(registered: Long) {
        this.registered = registered
    }


    fun getPlayed(): Int? {
        return played
    }

    fun setPlayed(played: Int) {
        this.played = played
    }


    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

}