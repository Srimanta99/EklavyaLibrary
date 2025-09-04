package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.banner.Banner
import com.sm.mylibrary.repository.HomeBannerRepository
import kotlinx.coroutines.launch

class FragmentHomeViewModel: ViewModel() {

    private val _banner = MutableLiveData<List<Banner>>()
    val banner: LiveData<List<Banner>> get() = _banner


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error




    val homeBannerRepository = HomeBannerRepository()


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
}