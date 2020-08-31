package com.example.quizapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quizapp.R
import com.example.quizapp.helpers.Constants
import com.example.quizapp.model.Users
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity(), RewardedVideoAdListener {

    var firebaseUser: FirebaseUser? = null
    var refUsers: DatabaseReference? = null
    var overallPoints: Int = 0
    var overallPlayed: Int = 0

    private lateinit var mRewardedVideoAd: RewardedVideoAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val username = intent.getStringExtra(Constants.USER_NAME)
        tv_name.text = username

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        var points = correctAnswers*2 - (totalQuestions-correctAnswers)
        if (points < 0) {
            points = 0
        }

        var newText = "${getString(R.string.result_your_score)} $correctAnswers ${getString(R.string.result_out_of)} $totalQuestions ${getString(R.string.result_gets_you)} $points "
        if (points == 1) {
            newText += getString(R.string.result_point_small)
        } else if (points > 1 && points < 5) {
            newText += getString(R.string.result_points_small)
        } else {
            newText += getString(R.string.result_points_small)
        }

        newText +="\n${getString(R.string.result_watch_ad)}!"

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

            if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            }

        }
    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build())
    }

    override fun onRewardedVideoAdClosed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRewardedVideoAdLeftApplication() {

    }

    override fun onRewardedVideoAdLoaded() {

    }

    override fun onRewardedVideoAdOpened() {

    }

    override fun onRewardedVideoCompleted() {

    }

    override fun onRewarded(p0: RewardItem?) {
        Toast.makeText(this@ResultActivity, "Nagrada", Toast.LENGTH_SHORT).show()

        val mapUser = HashMap<String, Any>()
        mapUser["points"] = overallPoints + 10

        refUsers!!.updateChildren(mapUser).addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                //
            }
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRewardedVideoStarted() {

    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        Toast.makeText(this@ResultActivity, R.string.result_error_ad, Toast.LENGTH_SHORT).show()
    }
}