package com.sm.mylibrary.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class CommonPermession {
// companion object{
//                    private fun checkStoragePermission( activity: Activity?): Boolean {
//                        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                            // Android 13+
//                            activity?.let {
//                                ContextCompat.checkSelfPermission(it, android.Manifest.permission.READ_MEDIA_IMAGES)
//                            } == PackageManager.PERMISSION_GRANTED
//
//                        } else {
//                            // Android 12 and below
//                            activity?.let {
//                                ContextCompat.checkSelfPermission(it, android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                            } == PackageMnager.PERMISSION_GRANTED
//            }
//        }
//
//    }
//
//
//    private fun requestStoragePermission( activity: Activity?) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            activity?.let {
//                ActivityCompat.requestPermissions(
//                    it,
//                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
//                    STORAGE_PERMISSION_CODE
//                )
//            }
//        } else {
//            activity?.let {
//                ActivityCompat.requestPermissions(
//                    it,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    STORAGE_PERMISSION_CODE
//                )
//            }
//        }
   // }
}