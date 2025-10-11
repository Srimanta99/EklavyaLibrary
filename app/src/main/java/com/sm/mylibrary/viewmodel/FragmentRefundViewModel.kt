package com.sm.mylibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.refund.RefundData
import com.sm.mylibrary.repository.RefundRepository
import kotlinx.coroutines.launch

class FragmentRefundViewModel : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    val refundRepository : RefundRepository = RefundRepository()


    private val _refundResult = MutableLiveData<RefundData>()
    val refundResult: LiveData<RefundData> get() = _refundResult



    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    public fun reFunds(request: HashMap<String, String>){
        _loading.value = true

        viewModelScope.launch {
            try {
                val response =  refundRepository.getreFunds(request)
                if (response.isSuccessful && response.body() != null) {
                    _loading.value = false
                    _refundResult.postValue(response.body())
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