package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.notififation.NotificationModel
import com.sm.mylibrary.repository.HomeRepository
import kotlinx.coroutines.launch

class ActivityMainViewModel : ViewModel() {

    private val _notificationResult = MutableLiveData<NotificationModel>()
    val notificationResult: LiveData<NotificationModel> get() = _notificationResult

    private val _menuAction = MutableLiveData<Int>()
    val menuAction: LiveData<Int> = _menuAction

    val homeRepository = HomeRepository()

    fun onMenuClicked(menuId: Int) {
        _menuAction.value = menuId
    }



    fun getnotification (  request : Map<String, String>){
       viewModelScope.launch {
           try {
               val response =  homeRepository.getnotification(request)
               if (response.isSuccessful && response.body() != null) {
                   _notificationResult.postValue(response.body())
               }
           }catch (e: Exception){
               e.printStackTrace()
           }
           }

    }


}