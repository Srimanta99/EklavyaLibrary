package com.sm.mylibrary.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SheardPreferenceViewModel : ViewModel() {

    fun saveData(param: String, data: String) {
        viewModelScope.launch {
            SharedPreferencesManager.saveString(param, data)
        }
    }

    fun loadData( param: String): String {
        return SharedPreferencesManager.getString(param, "")
    }

    fun clearPreferenceData(){
       SharedPreferencesManager.clearPreference()
    }
}