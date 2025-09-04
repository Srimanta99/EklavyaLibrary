package com.sm.mylibrary.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayOutputStream

object Utils {

    fun imageViewToBase64(imageView: ImageView): String? {
        // get drawable from ImageView
        val drawable = imageView.drawable ?: return null

        // convert drawable to Bitmap
        val bitmap = (drawable as BitmapDrawable).bitmap

        // compress bitmap to byte array (JPEG 100% quality)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()

        // encode to Base64
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }


    fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var name: String? = null
        // Only query if it's a content:// Uri
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) {
                        name = it.getString(nameIndex)
                    }
                }
            }
        }

        // For file:// Uris fallback to path
        if (name == null) {
            name = uri.path
            val cut = name?.lastIndexOf('/')
            if (cut != null && cut != -1) {
                name = name?.substring(cut + 1)
            }
        }
        return name
    }
}