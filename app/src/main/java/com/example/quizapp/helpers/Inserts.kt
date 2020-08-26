package com.example.quizapp.helpers

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object Inserts {


    fun addCategories() {
        var refCategories: DatabaseReference? = null
        refCategories = FirebaseDatabase.getInstance().reference.child("Categories").child("1")

        val categoryHashMap = HashMap<String, Any>()
        categoryHashMap["id"] = 1
        categoryHashMap["name"] = "Zemljopis"
        categoryHashMap["name_en"] = "Geography"
        categoryHashMap["description"] = "Zemljopis"

        refCategories!!.updateChildren(categoryHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }

        refCategories = FirebaseDatabase.getInstance().reference.child("Categories").child("2")
        categoryHashMap["id"] = 2
        categoryHashMap["name"] = "Sport"
        categoryHashMap["name_en"] = "Sport"
        categoryHashMap["description"] = "Sport"

        refCategories!!.updateChildren(categoryHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }

        refCategories = FirebaseDatabase.getInstance().reference.child("Categories").child("3")
        categoryHashMap["id"] = 3
        categoryHashMap["name"] = "Glazba"
        categoryHashMap["name_en"] = "Music"
        categoryHashMap["description"] = "Glazba"

        refCategories!!.updateChildren(categoryHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }

        refCategories = FirebaseDatabase.getInstance().reference.child("Categories").child("4")
        categoryHashMap["id"] = 4
        categoryHashMap["name"] = "Razno"
        categoryHashMap["name_en"] = "Everything"
        categoryHashMap["description"] = "Razno"

        refCategories!!.updateChildren(categoryHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }
    }

    fun addQuestions() {
        var refQuestions: DatabaseReference? = null
        val questionHashMap = HashMap<String, Any>()

        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("1")
        questionHashMap["id"] = 1
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_argentina.png?alt=media&token=d151785e-1a24-4a71-9915-458c2e3705fc"
        questionHashMap["optionOne"] = "Argentina"
        questionHashMap["optionTwo"] = "Australija"
        questionHashMap["optionThree"] = "Armenija"
        questionHashMap["optionFour"] = "Austrija"
        questionHashMap["correctAnswer"] = 1

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }


        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("2")
        questionHashMap["id"] = 2
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_australia.png?alt=media&token=2a71ba45-9954-4826-98bd-3a540effafc8"
        questionHashMap["optionOne"] = "Angola"
        questionHashMap["optionTwo"] = "Austrija"
        questionHashMap["optionThree"] = "Australija"
        questionHashMap["optionFour"] = "Armenija"
        questionHashMap["correctAnswer"] = 3

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }


        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("3")
        questionHashMap["id"] = 3
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_brazil.png?alt=media&token=bbc1f9a8-0c16-4612-a1a6-980e34343fd3"
        questionHashMap["optionOne"] = "Bjelorusija"
        questionHashMap["optionTwo"] = "Belize"
        questionHashMap["optionThree"] = "Bruneji"
        questionHashMap["optionFour"] = "Brazil"
        questionHashMap["correctAnswer"] = 4

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }


        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("4")
        questionHashMap["id"] = 4
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_belgium.png?alt=media&token=1237e581-cb45-4e78-b61c-a936810eea1a"
        questionHashMap["optionOne"] = "Bahami"
        questionHashMap["optionTwo"] = "Belgija"
        questionHashMap["optionThree"] = "Barbados"
        questionHashMap["optionFour"] = "Belize"
        questionHashMap["correctAnswer"] = 2

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }


        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("5")
        questionHashMap["id"] = 5
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_fiji.png?alt=media&token=928ff356-c274-490d-9cba-c3f3bab33272"
        questionHashMap["optionOne"] = "Gabon"
        questionHashMap["optionTwo"] = "Francuska"
        questionHashMap["optionThree"] = "Fidži"
        questionHashMap["optionFour"] = "Finska"
        questionHashMap["correctAnswer"] = 3

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }


        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("6")
        questionHashMap["id"] = 6
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_germany.png?alt=media&token=972b77ac-8e16-458b-aa39-aff73a176fd3"
        questionHashMap["optionOne"] = "Njemačka"
        questionHashMap["optionTwo"] = "Gruzija"
        questionHashMap["optionThree"] = "Grčka"
        questionHashMap["optionFour"] = "Niti jednoj od navednih"
        questionHashMap["correctAnswer"] = 1

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }


        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("7")
        questionHashMap["id"] = 7
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_denmark.png?alt=media&token=c854d1e0-e392-4c4d-9c8b-a8d7d063abe2"
        questionHashMap["optionOne"] = "Dominikanska Republika"
        questionHashMap["optionTwo"] = "Egipat"
        questionHashMap["optionThree"] = "Danska"
        questionHashMap["optionFour"] = "Etiopija"
        questionHashMap["correctAnswer"] = 3

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }


        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("8")
        questionHashMap["id"] = 8
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_india.png?alt=media&token=02158378-bdb1-40da-be88-a5edb799f902"
        questionHashMap["optionOne"] = "Irska"
        questionHashMap["optionTwo"] = "Iran"
        questionHashMap["optionThree"] = "Mađarska"
        questionHashMap["optionFour"] = "Indija"
        questionHashMap["correctAnswer"] = 4

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }


        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("9")
        questionHashMap["id"] = 9
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_new_zealand.png?alt=media&token=91dc6201-03f7-432c-a601-132cdb6d7eac"
        questionHashMap["optionOne"] = "Australija"
        questionHashMap["optionTwo"] = "Novi Zeland"
        questionHashMap["optionThree"] = "Tuvalu"
        questionHashMap["optionFour"] = "SAD"
        questionHashMap["correctAnswer"] = 2

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }


        refQuestions = FirebaseDatabase.getInstance().reference.child("Questions").child("10")
        questionHashMap["id"] = 10
        questionHashMap["category"] = "1"
        questionHashMap["question"] = "Kojoj državi pripada navedena zastava?"
        questionHashMap["imageUrl"] = "https://firebasestorage.googleapis.com/v0/b/quizapp-af119.appspot.com/o/ic_flag_of_kuwait.png?alt=media&token=ccf0b147-09ee-44c6-a3f3-e2dd1ba8bc61"
        questionHashMap["optionOne"] = "Kuvajt"
        questionHashMap["optionTwo"] = "Jordan"
        questionHashMap["optionThree"] = "Sudan"
        questionHashMap["optionFour"] = "Palestina"
        questionHashMap["correctAnswer"] = 1

        refQuestions!!.updateChildren(questionHashMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ništa
                }
            }

    }
}