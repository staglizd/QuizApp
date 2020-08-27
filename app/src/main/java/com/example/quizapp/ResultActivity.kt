package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quizapp.helpers.Constants
import com.example.quizapp.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    var firebaseUser: FirebaseUser? = null
    var refUsers: DatabaseReference? = null
    var overallPoints: Int = 0
    var overallPlayed: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val username = intent.getStringExtra(Constants.USER_NAME)
        tv_name.text = username

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        var points = correctAnswers*2 - (totalQuestions-correctAnswers)
        if (points < 0) {
            points = 0
        }

        var newText = "Vaš rezultat je $correctAnswers od $totalQuestions što vam donosi $points "
        if (points == 1) {
            newText += "bod"
        } else if (points > 1 && points < 5) {
            newText += "boda"
        } else {
            newText += "bodova"
        }

        tv_score.text = newText

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        refUsers!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    var user = p0.getValue(Users::class.java)

                    overallPoints = user!!.getPoints()!! + points
                    overallPlayed = user!!.getPlayed()!! + 1

                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        btn_finish.setOnClickListener {
            val mapUser = HashMap<String, Any>()
            mapUser["points"] = overallPoints
            mapUser["played"] = overallPlayed
            mapUser["lastPlayed"] = ServerValue.TIMESTAMP

            refUsers!!.updateChildren(mapUser).addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    //
                }
            }

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}