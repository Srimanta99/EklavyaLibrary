package com.sm.mylibrary.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionHelper {

    // Old permissions
    private const val READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE
    private const val WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE

    // Android 13+ permissions
    private const val READ_MEDIA_IMAGES = android.Manifest.permission.READ_MEDIA_IMAGES
    private const val READ_MEDIA_VIDEO = android.Manifest.permission.READ_MEDIA_VIDEO
    private const val READ_MEDIA_AUDIO = android.Manifest.permission.READ_MEDIA_AUDIO

    /**
     * ✅ Check storage permission for Activity
     */
    fun hasStoragePermission(activity: AppCompatActivity): Boolean {
        return checkPermission(activity)
    }

    /**
     * ✅ Check storage permission for Fragment
     */
    fun hasStoragePermission(fragment: Fragment): Boolean {
        return checkPermission(fragment.requireContext())
    }

    /**
     * ✅ Request permission from Activity
     */
    fun requestStoragePermission(
        activity: AppCompatActivity,
        launcher: ActivityResultLauncher<Array<String>>
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, READ_MEDIA_AUDIO))
        } else {
            launcher.launch(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE))
        }
    }

    /**
     * ✅ Request permission from Fragment
     */
    fun requestStoragePermission(
        fragment: Fragment,
        launcher: ActivityResultLauncher<Array<String>>
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, READ_MEDIA_AUDIO))
        } else {
            launcher.launch(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE))
        }
    }

    // Internal check
    private fun checkPermission(context: android.content.Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        context, READ_MEDIA_VIDEO
                    ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                context, READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

}