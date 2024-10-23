package com.example.galleryapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Build

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = ArrayList<String>()

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // 3 columns

        // Check for permissions
        checkPermissionAndLoadImages()
    }

    private fun checkPermissionAndLoadImages() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 and above
            if (checkPermission(Manifest.permission.READ_MEDIA_IMAGES)) {
                loadImages()
            } else {
                requestPermission(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            // For Android 9 and below
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                loadImages()
            } else {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImages()
                } else {
                    Toast.makeText(
                        this,
                        "Permission denied. Cannot load images.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun loadImages() {
        imageList.clear()

        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_ADDED
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (cursor.moveToNext()) {
                val imagePath = cursor.getString(dataColumn)
                imageList.add(imagePath)
            }
        }

        // Initialize adapter with click listener
        imageAdapter = ImageAdapter(imageList, this) { position ->
            try {
                val intent = Intent(this, FullScreenImageActivity::class.java).apply {
                    putStringArrayListExtra("image_list", ArrayList(imageList))
                    putExtra("position", position)
                }
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Error opening image", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        recyclerView.adapter = imageAdapter
    }
}