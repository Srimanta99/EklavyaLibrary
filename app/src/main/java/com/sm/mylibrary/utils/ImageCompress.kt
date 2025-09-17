package com.sm.mylibrary.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageCompress {

    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 2. Compress bitmap until ≤1MB
    fun compressBitmapToUnder1MB(bitmap: Bitmap): ByteArray {
        val maxFileSize = 1_000_000 // 1 MB
        var compressQuality = 100
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, stream)

        // reduce quality step-by-step until size < 1MB
        while (stream.toByteArray().size > maxFileSize && compressQuality > 10) {
            stream.reset()
            compressQuality -= 5
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, stream)
        }

        return stream.toByteArray()
    }

    // 3. Combine both and return Base64 directly
    fun getCompressedBase64FromUri(context: Context, uri: Uri): String? {
        val bitmap = getBitmapFromUri(context, uri) ?: return null
        val compressedBytes = compressBitmapToUnder1MB(bitmap)
        return Base64.encodeToString(compressedBytes, Base64.NO_WRAP)
    }
}