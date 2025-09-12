package com.sm.mylibrary.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sm.mylibrary.R


object ShowFullScreenImageDialog {

     fun showFullScreenDialog(context: Context, imageUrl: String) {
        val dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_showfullimage)

        val imageView = dialog.findViewById<ImageView>(R.id.fullScreenImage)
         val imagecross = dialog.findViewById<ImageView>(R.id.img_cross)

        // load image (Glide/Picasso/Coil)
        Glide.with(context)
            .load(imageUrl)
            .into(imageView)

        // Close on tap
        imagecross.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}