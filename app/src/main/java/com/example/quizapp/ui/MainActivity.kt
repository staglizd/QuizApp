package com.example.quizapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R
import com.example.quizapp.helpers.Constants
import com.example.quizapp.helpers.Inserts
import com.example.quizapp.model.Category
import com.example.quizapp.model.Users
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var firebaseUser: FirebaseUser? = null
    var refUsers: DatabaseReference? = null
    var refCategories: DatabaseReference? = null

    var user: Users? = null
    var mCategories: List<Category>? = null
    var categorySelected: Category? = null
    var difficulty: String? = null
    var numberOfQuestions: Int? = 0

    var arrCategories = ArrayList<String>()
    var arrDifficulty = ArrayList<String>()

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Inserts.addCategories()
        Inserts.addQuestions()

        // Full screen aplikacija
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // Initialize Ads
        MobileAds.initialize(this@MainActivity)

        // AdMob banner
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        // Interstitial ad (full screen ads)
        mInterstitialAd = InterstitialAd(this@MainActivity)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                Toast.makeText(this@MainActivity, R.string.main_ad_closed, Toast.LENGTH_SHORT).show()
            }
        }

        dropdown_category.setOnItemClickListener { parent, view, position, id ->
            categorySelected = mCategories!![position]
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        refCategories = FirebaseDatabase.getInstance().reference.child("Categories")

        refUsers!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    user = p0.getValue(Users::class.java)

                    // Trenutno stanje bodova
                    tv_points.text = "${getString(R.string.main_current_pts)}: ${user!!.getPoints()}"

                    // Pozdravna poruka
                    tv_welcome.text = "${getString(R.string.main_welcome)} ${user!!.getUsername()}!"
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        mCategories = ArrayList()
        arrCategories = ArrayList<String>()
        arrDifficulty = ArrayList<String>()

        refCategories!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                (mCategories as ArrayList).clear()
                arrCategories.clear()

                for (snapshot in p0.children) {
                    val category = snapshot.getValue(Category::class.java)
                    if (category!!.getActive() == 1) {
                        arrCategories.add(category!!.getName().toString())
                        (mCategories as ArrayList).add(category!!)
                    }
                }

                // Izbor kategorije
                val adapter:ArrayAdapter<String> = ArrayAdapter(this@MainActivity,
                    R.layout.dropdown_menu_popup_item, arrCategories)
                dropdown_category.setAdapter(adapter)

                // Izbor težine
                arrDifficulty.add("Easy")
                arrDifficulty.add("Medium")
                arrDifficulty.add("Hard")
                val adapterDifficulty: ArrayAdapter<String> = ArrayAdapter(this@MainActivity,
                    R.layout.dropdown_menu_popup_item, arrDifficulty)
                dropdown_difficulty.setAdapter(adapterDifficulty)

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        btn_start.setOnClickListener {

            // Display interstitial ad
            if (mInterstitialAd.isLoaded) {
                //mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet")
            }

            if (categorySelected != null) {

                if (dropdown_difficulty.text.toString().equals("Easy") || dropdown_difficulty.text.toString().equals("Medium")
                    || dropdown_difficulty.text.toString().equals("Hard")) {
                    Toast.makeText(this, "${getString(R.string.main_you_picked)}: ${categorySelected!!.getName()}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, QuizQuestionsActivity::class.java)
                    intent.putExtra(Constants.CATEGORY_ID, categorySelected!!.getId())
                    intent.putExtra(Constants.USER_NAME, user!!.getUsername())
                    intent.putExtra(Constants.NUMBER_OF_QUESTIONS, sNumberQuestions.getValue().toInt())
                    intent.putExtra(Constants.DIFFICULTY, dropdown_difficulty.text.toString())
                    startActivity(intent)
                    //finish()
                } else {
                    Toast.makeText(this, R.string.main_choose_difficulty_msg, Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(this, R.string.main_pick_category_msg, Toast.LENGTH_LONG).show()
            }
        }

        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        btn_profile.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        btn_ranks.setOnClickListener {
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }

    }

}