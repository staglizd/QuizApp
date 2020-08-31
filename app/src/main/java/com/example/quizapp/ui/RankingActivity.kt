package com.example.quizapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.adapter.UsersAdapter
import com.example.quizapp.model.Users
import com.google.firebase.database.*

class RankingActivity : AppCompatActivity() {
    var mUserList: List<Users>? = null
    var usersAdapter: UsersAdapter? = null
    lateinit var recycler_view_ranking: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        recycler_view_ranking = findViewById(R.id.recycler_view_ranking)
        recycler_view_ranking.setHasFixedSize(true)

        var linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recycler_view_ranking.layoutManager = linearLayoutManager

        retrieveRanking()
    }


    private fun retrieveRanking() {
        mUserList = ArrayList()
        val reference = FirebaseDatabase.getInstance().reference.child("Users").orderByChild("points")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                (mUserList as ArrayList<Users>).clear()
                for (snapshot in p0.children) {
                    val user = snapshot.getValue(Users::class.java)

                    (mUserList as ArrayList<Users>).add(user!!)

                }

                usersAdapter = UsersAdapter(this@RankingActivity, (mUserList as ArrayList<Users>))
                recycler_view_ranking.adapter = usersAdapter
            }
        })
    }
}