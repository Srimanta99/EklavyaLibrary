package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.applyleave.ApplyLeaveResponse
import com.sm.mylibrary.repository.ApplyLeaveRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ActivityApplyLeaveViewModel : ViewModel() {

    var leaveStartDate = MutableLiveData("")
    val leaveEndDate = MutableLiveData("")
    val leaveReason = MutableLiveData("")
    val validationMessage = MutableLiveData<String>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _applyLeaveResult = MutableLiveData<ApplyLeaveResponse>()
    val applyleaverResult: LiveData<ApplyLeaveResponse> get() = _applyLeaveResult

    val repository = ApplyLeaveRepository()

    fun checKValidation(): Boolean {
           return when {
               leaveStartDate.value.isEmpty() -> {
                   validationMessage.value = "Select Leave Start Date"
                   false
               }

               leaveEndDate.value.isEmpty() -> {
                   validationMessage.value = "Select Leave End Date"
                   false
               }
               leaveReason.value.isEmpty() -> {
                   validationMessage.value = "Enter Leave Reason"
                   false
               }

               else -> {
                   validationMessage.value = "Success"
                   true
               }
           }
    }


    public fun applyLeave(applyLeaveRequest: HashMap<String, String>) {

        _loading.value = true

        viewModelScope.launch {
            try {
                val response = repository.applyLeave(applyLeaveRequest)
                if (response.isSuccessful && response.body() != null) {
                    _loading.value =  false
                    _applyLeaveResult.postValue(response.body())
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