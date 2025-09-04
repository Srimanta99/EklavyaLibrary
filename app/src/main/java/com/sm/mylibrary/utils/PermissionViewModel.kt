package com.sm.mylibrary.utils

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel

class PermissionViewModel : ViewModel() {

    fun checkAndRequestExternalStoragePermission(
        activity: Activity,
        launcher: ActivityResultLauncher<Array<String>>
    ): Boolean {
        return if (PermissionUtils.hasExternalStoragePermission(activity)) {
            true
        } else {
            PermissionUtils.requestExternalStoragePermission(activity, launcher)
            false
        }
    }
}