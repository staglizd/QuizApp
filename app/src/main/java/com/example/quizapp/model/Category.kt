package com.example.quizapp.model

class Category {
    private var id: Int = 0
    private var name: String = ""
    private var name_en: String = ""
    private var description: String = ""

    constructor()

    constructor(
        id: Int,
        name: String,
        name_en: String,
        description: String
    ) {
        this.id = id
        this.name = name
        this.name_en = name_en
        this.description = description
    }

    fun getId():Int? {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getNameEn(): String? {
        return name_en
    }

    fun setNameEn(name_en: String) {
        this.name_en = name_en
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }




}