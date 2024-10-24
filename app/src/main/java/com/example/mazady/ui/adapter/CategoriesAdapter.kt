package com.example.mazady.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mazady.R


class CategoriesAdapter(
    private val Categories: ArrayList<String>,
    val activity: Activity,


    ) : RecyclerView.Adapter<CategoriesAdapter.AllNewsVHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllNewsVHolder =
        AllNewsVHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.categories_viewshape, parent, false)
        )


    override fun onBindViewHolder(holder: AllNewsVHolder, position: Int) {

        holder.categoryTxt.text = Categories[position]

        if (position == 0) {
            holder.categoryTxt.background =
                activity.getResources().getDrawable(R.drawable.category_main_background)
            holder.categoryTxt.setTextColor(activity.getColor(R.color.white))


        } else {
            holder.categoryTxt.background =
                activity.getResources().getDrawable(R.drawable.category_secondary_background)
            holder.categoryTxt.setTextColor(activity.getColor(R.color.txt_light_gray))

        }


    }

    override fun getItemCount() = Categories.size

    class AllNewsVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTxt: TextView = itemView.findViewById(R.id.cat_txt)


    }


}