package com.example.galleryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class FullScreenImageActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FullScreenImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        viewPager = findViewById(R.id.viewPager)

        val imageList = intent.getStringArrayListExtra("image_list") ?: ArrayList ()
        val position = intent.getIntExtra("position", 0)

        adapter = FullScreenImageAdapter(imageList)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(position, false)
    }
}