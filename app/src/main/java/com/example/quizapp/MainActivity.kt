package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.helpers.Constants
import com.example.quizapp.helpers.Inserts
import com.example.quizapp.model.Category
import com.example.quizapp.model.Users
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

    var arrCategories = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Inserts.addCategories()
        Inserts.addQuestions()

        // Full screen aplikacija
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

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
                    tv_points.text = "Trenutno stanje bodova: ${user!!.getPoints()}"

                    // Pozdravna poruka
                    tv_welcome.text = "Dobrodošli ${user!!.getUsername()}!"
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        mCategories = ArrayList()
        arrCategories = ArrayList<String>()

        refCategories!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                (mCategories as ArrayList).clear()
                arrCategories.clear()

                for (snapshot in p0.children) {
                    val category = snapshot.getValue(Category::class.java)
                    arrCategories.add(category!!.getName().toString())
                    (mCategories as ArrayList).add(category!!)
                }

                // Izbor kategorije
                val adapter:ArrayAdapter<String> = ArrayAdapter(this@MainActivity, R.layout.dropdown_menu_popup_item, arrCategories)
                dropdown_category.setAdapter(adapter)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        btn_start.setOnClickListener {
            if (categorySelected != null) {
                Toast.makeText(this, "Odabrali ste kategoriju: ${categorySelected!!.getName()}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.CATEGORY_ID, categorySelected!!.getId())
                intent.putExtra(Constants.USER_NAME, user!!.getUsername())
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Molimo odaberite kategoriju", Toast.LENGTH_LONG).show()
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