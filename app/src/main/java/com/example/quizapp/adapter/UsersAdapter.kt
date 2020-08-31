package com.example.quizapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UsersAdapter (
    mContext: Context,
    mUsersList: List<Users>
): RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val mContext: Context?
    private val mUsersList: List<Users>?
    var firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

    init {
        this.mUsersList = mUsersList
        this.mContext = mContext
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: Users = mUsersList!![position]

        Picasso.get().load(user?.getProfile()).into(holder.profile_image_ranking)

        holder.name_ranking!!.text = user?.getName()
        holder.tv_points_ranking!!.text = "${R.string.ranking_points}: ${user!!.getPoints()}"
        holder.tv_played_ranking!!.text = "${R.string.ranking_played}: ${user!!.getPlayed()}"

    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        val view: View = LayoutInflater.from(mContext)
            .inflate(R.layout.ranking_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mUsersList!!.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var profile_image_ranking: CircleImageView? = null
        var name_ranking: TextView? = null
        var tv_points_ranking: TextView? = null
        var tv_played_ranking: TextView? = null

        init {
            profile_image_ranking = itemView.findViewById(R.id.profile_image_ranking)
            name_ranking = itemView.findViewById(R.id.name_ranking)
            tv_points_ranking = itemView.findViewById(R.id.tv_points_ranking)
            tv_played_ranking = itemView.findViewById(R.id.tv_played_ranking)
        }
    }

}