package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.applyleave.ApplyLeaveResponse
import com.sm.mylibrary.model.attendance.PunchInResponse
import com.sm.mylibrary.repository.ApplyAttendenceRepository
import com.sm.mylibrary.repository.ApplyLeaveRepository
import kotlinx.coroutines.launch

class ActivityAttendanceViewModel : ViewModel() {


    val validationMessage = MutableLiveData<String>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _applyPunchResult = MutableLiveData<PunchInResponse>()
    val applyPunchinResult: LiveData<PunchInResponse> get() = _applyPunchResult

    val repository = ApplyAttendenceRepository()


    public fun applyPunchIn(applyLeaveRequest: HashMap<String, String>) {

        _loading.value = true

        viewModelScope.launch {
            try {
                val response = repository.ApplyPunchIn(applyLeaveRequest)
                if (response.isSuccessful && response.body() != null) {
                    _loading.value =  false
                    _applyPunchResult.postValue(response.body())
                   // validationMessage.value = response.body()!!.message
                } else {
                    _loading.value =  false
                    /* if (response.code() == 409) {
                         _error.postValue("Registration failed: Phone Number already exists")
                     }*/

                }
            }
            catch (e: Exception) {
                _loading.value =  false
                _error.postValue("Error: ${e.message}")
            }
        }

    }


    public fun applyPunchOut(applyLeaveRequest: HashMap<String, String>) {

        _loading.value = true

        viewModelScope.launch {
            try {
                val response = repository.ApplyPunchOut(applyLeaveRequest)
                if (response.isSuccessful && response.body() != null) {
                    _loading.value =  false
                    _applyPunchResult.postValue(response.body())
                    // validationMessage.value = response.body()!!.message
                } else {
                    _loading.value =  false
                    /* if (response.code() == 409) {
                         _error.postValue("Registration failed: Phone Number already exists")
                     }*/

                }
            }
            catch (e: Exception) {
                _loading.value =  false
                _error.postValue("Error: ${e.message}")
            }
        }

    }

}