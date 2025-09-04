package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.model.login.User
import com.sm.mylibrary.repository.LoginRepository
import com.sm.mylibrary.repository.SignUpRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ActivityLoginViewModel : ViewModel() {

    var username = MutableLiveData("")
    var password = MutableLiveData("")

    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val repository = LoginRepository()

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> get() = _loginResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading



    fun onLoginClicked() : Boolean {
        val usernameValue = username.value ?: ""
        val passwordValue = password.value ?: ""

        var isValid = true


        if (usernameValue.isBlank()) {
            emailError.value = "Email is required"
            isValid = false
        } else {
            emailError.value = null
        }

        // Password Validation
        if (passwordValue.isBlank()) {
            passwordError.value = "Password is required"
            isValid = false
        }  else {
            passwordError.value = null
        }

        if (isValid) {
            return true
        }
        else
            return false
    }


    public fun loginUser(registraionRequest: User){
        _loading.value = true

        viewModelScope.launch {
            try {
                val response =  repository.loginUser(registraionRequest)
                if (response.isSuccessful && response.body() != null) {
                    _loading.value = false
                    _loginResult.postValue(response.body())
                } else {
                    _loading.value = false
                    _error.postValue("Login failed: ${response.message()}")
                }
            }
            catch (e: Exception) {
                _loading.value = false
                _error.postValue("Error: ${e.message}")
            }
        }
    }

}