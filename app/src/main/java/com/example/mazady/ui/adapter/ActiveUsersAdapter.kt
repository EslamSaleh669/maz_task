package com.example.mazady.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mazady.R



class ActiveUsersAdapter(
    private val ActiveUsers: ArrayList<Int>,
    val activity: Activity,



) : RecyclerView.Adapter<ActiveUsersAdapter.AllNewsVHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNewsVHolder =
        AllNewsVHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.active_user_viewshape,parent,false)
        )


    override fun onBindViewHolder(holder: AllNewsVHolder, position: Int) {


        holder.activeUserImg.setImageResource(ActiveUsers[position]);


    }

    override fun getItemCount() = ActiveUsers.size

    class AllNewsVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val activeUserImg : ImageView = itemView.findViewById(R.id.activeuserimg)


    }



}