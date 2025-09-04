package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.banner.Banner
import com.sm.mylibrary.model.banner.BannerResponse
import com.sm.mylibrary.model.slot.SlotDetails
import com.sm.mylibrary.repository.HomeBannerRepository
import kotlinx.coroutines.launch

class ActivityMainViewModel : ViewModel() {

    private val _menuAction = MutableLiveData<Int>()
    val menuAction: LiveData<Int> = _menuAction

    fun onMenuClicked(menuId: Int) {
        _menuAction.value = menuId
    }




}