package com.example.mazady.ui.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mazady.R
import com.example.mazady.data.model.ItemImageSlider

class AutoImageSliderAdapter(private val context: Context, private var imageList: List<ItemImageSlider>) : PagerAdapter() {

    private var currentPage = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    override fun getCount() = imageList.size

    fun autoslide(viewPager: ViewPager){
        handler= Handler()
        runnable = object : Runnable {
            override fun run() {
                if (currentPage == viewPager.adapter?.count?.minus(1)) {
                    currentPage = 0
                } else {
                    currentPage++
                }
                viewPager.setCurrentItem(currentPage, true)
                handler.postDelayed(this, 2000)
            }
        }
        Handler().postDelayed(runnable,1000)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =  (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater).inflate(R.layout.slider_item, null)
//        val ivImages = view.findViewById<ConstraintLayout>(R.id.imageview)
//        val heading=view.findViewById<TextView>(R.id.tvHeading)
//        val subHeading=view.findViewById<TextView>(R.id.tvSubHeading)
//        ivImages.background = context.getResources().getDrawable(R.drawable.basebackg)

//        heading.text=imageList[position].heading
//        subHeading.text= imageList[position].subheading


        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}