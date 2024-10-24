package com.example.mazady.ui.activity.secondpage

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.mazady.R
import com.example.mazady.data.model.ItemImageSlider
import com.example.mazady.ui.adapter.ActiveUsersAdapter
import com.example.mazady.ui.adapter.AutoImageSliderAdapter
import com.example.mazady.ui.adapter.CategoriesAdapter
import com.google.android.material.tabs.TabLayout


class SecondPage : AppCompatActivity() {
    private lateinit var activeUsersRecycler: RecyclerView
    private lateinit var categoriesRecycler: RecyclerView
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)

        activeUsersRecycler = findViewById(R.id.active_user_rec)
        categoriesRecycler = findViewById(R.id.cat_rec)
        viewPager = findViewById(R.id.viewPager)

        initActiveUsersList()
        initCategoriesList()
        initImageSlider()

    }

    private fun initImageSlider() {
        val item= mutableListOf<ItemImageSlider>()
        item.add(0, ItemImageSlider("Everything is here to enjoy quiz!",
            "Quiz as a group or individually! Expand your circle!", R.drawable.basebackg))
        item.add(1, ItemImageSlider("Test your knowledge, Quiz Master!",
            "Challenge yourself with a variety of quizzes!", R.drawable.basebackg))
        item.add(2, ItemImageSlider("Elevate your quiz experience!",
            "Unleash your trivia prowess with Hungry Devops!", R.drawable.basebackg))

        val viewPagerAdapter = AutoImageSliderAdapter(this, item)
//        binding.viewPager.adapter = viewPagerAdapter
        viewPager.adapter = viewPagerAdapter
//        viewPagerAdapter.autoslide(binding.viewPager)
        viewPagerAdapter.autoslide(viewPager)

//        binding.tabLayout.setupWithViewPager(binding.viewPager

    }


    private fun initActiveUsersList(){



        val usersList: ArrayList<Int> = ArrayList()
        usersList.add(R.drawable.user1)
        usersList.add(R.drawable.user2)
        usersList.add(R.drawable.user3)
        usersList.add(R.drawable.user1)
        usersList.add(R.drawable.user2)
        usersList.add(R.drawable.user3)
        usersList.add(R.drawable.user1)
        usersList.add(R.drawable.user2)
        usersList.add(R.drawable.user3)
        usersList.add(R.drawable.user1)
        usersList.add(R.drawable.user2)
        usersList.add(R.drawable.user3)
        usersList.add(R.drawable.user1)
        usersList.add(R.drawable.user2)
        usersList.add(R.drawable.user3)



        activeUsersRecycler.adapter =
            ActiveUsersAdapter(usersList,this)
        activeUsersRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }


    private fun initCategoriesList(){



        val catList: ArrayList<String> = ArrayList()
        catList.add("All")
        catList.add("UI/UX")
        catList.add("Illustrator")
        catList.add("3D Animation")
        catList.add("All")
        catList.add("UI/UX")
        catList.add("Illustrator")
        catList.add("3D Animation")
        catList.add("All")
        catList.add("UI/UX")
        catList.add("Illustrator")
        catList.add("3D Animation")
        catList.add("All")
        catList.add("UI/UX")
        catList.add("Illustrator")
        catList.add("3D Animation")



        categoriesRecycler.adapter =
            CategoriesAdapter(catList,this)
        categoriesRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

}