package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.manageleave.ManageLeaveResponse
import com.sm.mylibrary.repository.ManageLeaveRepository
import kotlinx.coroutines.launch

class FragmentMangeLeaveViewModel : ViewModel() {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    val manageLeaveRepository : ManageLeaveRepository = ManageLeaveRepository()


    private val _leaveData = MutableLiveData<ManageLeaveResponse>()
    val leaveData: LiveData<ManageLeaveResponse> get() = _leaveData



    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    public fun getLeaveData(request: HashMap<String, String>){
        _loading.value = true

        viewModelScope.launch {
            try {
                val response =  manageLeaveRepository.getLeaveData(request)

                if (response.isSuccessful && response.body() != null) {
                      _loading.value = false
                      _leaveData.postValue(response.body())
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