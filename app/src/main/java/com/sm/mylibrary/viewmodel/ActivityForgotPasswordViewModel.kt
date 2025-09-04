package com.sm.mylibrary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityForgotPasswordViewModel : ViewModel() {
    val email = MutableLiveData<String>()

}