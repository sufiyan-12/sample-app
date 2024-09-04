package com.prac.sampleappxml.activity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.prac.sampleappxml.R

/**
 * this adapter is used for carousel view
 * to load images from url
 */
class CarouselAdapter(
    private val context: Context,
    private val images: List<String?>?
) : PagerAdapter(){
    override fun getCount() = images?.size?:0
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.carousel_view_item, container, false)
        val carouselIV = view.findViewById<ImageView>(R.id.carousel_img_iv)
        val imageUrl = images?.get(position)
        if(!imageUrl.isNullOrEmpty()){
            // load image from url
            Glide.with(context).load(images?.get(position)).into(carouselIV)
        } else{
            // default view when image url is empty
            carouselIV.setImageResource(R.drawable.ic_launcher_background)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as LinearLayout)
    }
}