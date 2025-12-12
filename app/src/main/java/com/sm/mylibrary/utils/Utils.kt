package com.sm.mylibrary.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Base64
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.sm.mylibrary.R
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

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

    fun uriToBase64(context: Context, uri: Uri): String? {
       /* return try {
            // Open input stream from the URI
            val inputStream = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() // read entire file into byte array
            inputStream?.close()

            if (bytes != null) {
                android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }*/

        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // 80% quality
                val bytes = outputStream.toByteArray()
                Base64.encodeToString(bytes, Base64.NO_WRAP)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun getFileNameFromUri(context: Context, uri: Uri): String? {

      /*  val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex =  returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val size = returnCursor.getLong(sizeIndex).toString()
        val file = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream?.available() ?: 0
            //int bufferSize = 1024;
            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream?.read(buffers).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                outputStream.write(buffers, 0, read)
            }
            //Log.e("File Size", "Size " + file.length())
            inputStream?.close()
            outputStream.close()
          //  Log.e("File Path", "Path " + file.path)

        } catch (e: java.lang.Exception) {
            Log.e("Exception", e.message!!)
        }
        return file.path*/

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

       /* if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (cursor.moveToFirst() && nameIndex != -1) {
                    return cursor.getString(nameIndex) // original name
                }
            }
        }
        // For file:// URIs (direct file path)
        if (uri.scheme == ContentResolver.SCHEME_FILE) {
            return File(uri.path ?: "").name
        }

        // Fallback (last path segment)
        return uri.lastPathSegment*/
    }



    fun compressBase64Image(
        base64String: String,
        quality: Int = 60,
        maxWidth: Int = 800,
        maxHeight: Int = 800
    ): String {
        // 1. Decode Base64 to bytes
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)

        // 2. Convert bytes to Bitmap
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        // 3. Optionally scale down the bitmap
        val scaledBitmap = scaleBitmap(bitmap, maxWidth, maxHeight)

        // 4. Compress bitmap again
        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

        // 5. Re-encode to Base64
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }

    private fun scaleBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

        var finalWidth = maxWidth
        var finalHeight = maxHeight

        if (ratioMax > 1) {
            finalWidth = (maxHeight * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth / ratioBitmap).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true)
    }


    fun dateFormatConveter(selectedDate : String) : String{
        val inputFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())

// formatter for your desired output format:
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = inputFormat.parse(selectedDate)   // convert string → Date
        val formattedDate = outputFormat.format(date!!)
        return formattedDate
    }


    fun changeDateFormat(inputDate: String?): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

            val date: Date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return ""
        }
    }

    fun changeDateFormatwithtime(inputDate: String?): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault())

            val date: Date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return ""
        }
    }



    fun getMonthNamefromaDate(date : String) :String{

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = sdf.parse(date)
        val monthName = SimpleDateFormat("MMMM", Locale.ENGLISH).format(date)
        return monthName
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun daysBetweenTodayAnd(targetDateString: String): Long {
        // match your date format
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now()
        val targetDate = LocalDate.parse(targetDateString, formatter)
        // number of days from today to target date
        return ChronoUnit.DAYS.between(today, targetDate)
    }


    fun showAlert(context: Context, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.app_name))
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}