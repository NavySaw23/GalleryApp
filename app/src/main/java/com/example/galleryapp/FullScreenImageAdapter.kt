package com.example.galleryapp

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class FullScreenImageAdapter(private val imageList: ArrayList<String>) :
    RecyclerView.Adapter<FullScreenImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_full_screen_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            setupImageView()
        }

        fun bind(imagePath: String) {
            try {
                val bitmap = BitmapFactory.decodeFile(imagePath)
                imageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                imageView.setImageResource(R.drawable.placeholder_image)
            }
        }

        private fun setupImageView() {
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER // This will make the image fit the screen
        }
    }
}