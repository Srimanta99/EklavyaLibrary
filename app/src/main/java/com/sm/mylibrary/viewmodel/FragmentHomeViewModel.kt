package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.banner.Banner
import com.sm.mylibrary.model.imageupload.ImageUploadResponse
import com.sm.mylibrary.repository.AadharBackFrontUploadRepository
import com.sm.mylibrary.repository.HomeBannerRepository
import com.sm.mylibrary.repository.ProfileImageUploadRepository
import kotlinx.coroutines.launch

class FragmentHomeViewModel: ViewModel() {

    private val _banner = MutableLiveData<List<Banner>>()
    val banner: LiveData<List<Banner>> get() = _banner


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



    private val _aadharFrontUploadResponse = MutableLiveData<ImageUploadResponse>()
    val aadharFrontUploadResponse: LiveData<ImageUploadResponse> get() = _aadharFrontUploadResponse


    private val _aadharBackUploadResponse = MutableLiveData<ImageUploadResponse>()
    val aadharBackUploadResponse: LiveData<ImageUploadResponse> get() = _aadharBackUploadResponse


    val homeBannerRepository = HomeBannerRepository()
    val profileImageUploadRepository = ProfileImageUploadRepository()
    val aadharImageUploadRepository = AadharBackFrontUploadRepository()

    fun  getBannerImage(){
        viewModelScope.launch {
            try {
                val response = homeBannerRepository.getBanner()
                if (response.isSuccessful) {
                    _banner.postValue(response.body()?.banner)
                } else {
                    _error.postValue("Error Code: ${response.code()}")
                }
            }catch (e: Exception){
                _error.postValue(e.message ?: "Unknown error")
            }
        }
    }

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