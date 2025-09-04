package com.sm.mylibrary.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sm.mylibrary.model.login.LoginResponse

class SheardViewModel : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>   = _loginResponse


    fun setLoginResponse(response: LoginResponse) {
        _loginResponse.value = response
    }



}