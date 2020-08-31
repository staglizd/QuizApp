package com.example.quizapp.model

class Category {
    private var id: Int = 0
    private var name: String = ""
    private var name_en: String = ""
    private var description: String = ""
    private var active: Int = 0

    constructor()

    constructor(
        id: Int,
        name: String,
        name_en: String,
        description: String,
        active: Int
    ) {
        this.id = id
        this.name = name
        this.name_en = name_en
        this.description = description
        this.active = active
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

    fun getActive():Int? {
        return active
    }

    fun setActive(active: Int) {
        this.active = active
    }



}