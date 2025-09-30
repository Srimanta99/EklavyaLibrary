package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.imageupload.ImageUploadResponse
import com.sm.mylibrary.repository.AadharBackFrontUploadRepository
import com.sm.mylibrary.repository.ProfileImageUploadRepository
import kotlinx.coroutines.launch

class FragmentProfileViewModel : ViewModel() {

    val profileImageUploadRepository = ProfileImageUploadRepository()
    val aadharImageUploadRepository = AadharBackFrontUploadRepository()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error



    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> get() = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading



    private val _imageUploadResponse = MutableLiveData<ImageUploadResponse>()
    val imageUploadResponse: LiveData<ImageUploadResponse> get() = _imageUploadResponse



    fun uploadProfileImage(proImageUploadRequest: HashMap<String, String>) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = profileImageUploadRepository.uploadProfileImage(proImageUploadRequest)
                if (response.isSuccessful) {
                    _loading.value = false

                    _message.postValue(response.body()?.message)
                    _imageUploadResponse.postValue(response.body())
                } else {
                    _loading.value = false
                    _error.postValue("Error Code: ${response.code()}")
                }
            } catch (e: Exception) {
                _loading.value = false
                _error.postValue(e.message ?: "Unknown error")
            }
        }
    }


    fun uploadAadharImage(AadharImageUploadRequest: HashMap<String, String>) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = aadharImageUploadRepository.uploadAadharImage(AadharImageUploadRequest)
                if (response.isSuccessful) {
                    _loading.value = false
                    _message.postValue(response.body()?.message)
                    _imageUploadResponse.postValue(response.body())
                } else {
                    _loading.value = false
                    _error.postValue("Error Code: ${response.code()}")
                }
            } catch (e: Exception) {
                _loading.value = false
                _error.postValue(e.message ?: "Unknown error")
            }
        }
    }


}