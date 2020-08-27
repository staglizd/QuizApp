package com.example.quizapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp.helpers.Constants
import com.example.quizapp.model.Users
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.tv_points

class SettingsActivity : AppCompatActivity() {

    var usersReference: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null
    private var imageUri: Uri? = null
    private var storageRef: StorageReference? = null

    private val RequestCode = 438

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        usersReference = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        storageRef = FirebaseStorage.getInstance().reference.child("User Images")

        usersReference!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val user: Users? = p0.getValue(Users::class.java)

                    if (this@SettingsActivity != null) {
                        tv_username.text = user?.getName()
                        tv_points.text = "Bodovi: ${user!!.getPoints()}"
                        tv_played.text = "Odigrano: ${user!!.getPlayed()}"

                        Picasso.get().load(user?.getProfile())
                            .into(profile_image_settings)
                    }

                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })


        btn_save.setOnClickListener {
            Toast.makeText(this@SettingsActivity, "Postavke uspješno spremljene", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        profile_image_settings.setOnClickListener {
            pickImage()
        }
    }


    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, RequestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestCode && resultCode == Activity.RESULT_OK
            && data!!.data != null) {
            imageUri = data.data
            Toast.makeText(this@SettingsActivity, "Učitavanje slike ...", Toast.LENGTH_LONG).show()
            uploadImageToDb()
        }
    }


    private fun uploadImageToDb() {
        val progressBar = ProgressDialog(this@SettingsActivity)
        progressBar.setMessage("Slika se učitava, molimo pričekajte ...")
        progressBar.show()

        if (imageUri != null) {
            val fileRef = storageRef!!.child(System.currentTimeMillis().toString() + ".jpg")

            var uploadTask: StorageTask<*>
            uploadTask = fileRef.putFile(imageUri!!)

            uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot,
                    Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()

                    // Set up as profile image
                    val mapProfileImg = HashMap<String, Any>()
                    mapProfileImg["profile"] = url
                    usersReference!!.updateChildren(mapProfileImg)

                    progressBar.dismiss()
                }
            }
        }
    }

}