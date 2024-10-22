package com.example.galleryapp

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class ImageAdapter(private val imageList: List<String>, private val context: Context) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagePath = imageList[position]
        loadImageIntoImageView(imagePath, holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    private fun loadImageIntoImageView(imagePath: String, imageView: ImageView) {
        try {
            val file = File(imagePath)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(imagePath)
                imageView.setImageBitmap(bitmap)
            } else {
                // Set a placeholder image if the file doesn't exist
                imageView.setImageResource(R.drawable.placeholder_image)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Set a placeholder image if there's an error loading the image
            imageView.setImageResource(R.drawable.placeholder_image)
        }
    }
}