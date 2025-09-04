package com.sm.mylibrary.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.mylibrary.model.slot.SlotDetails
import com.sm.mylibrary.repository.SignUpRepository
import com.sm.mylibrary.repository.SlotRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ActivitySignUpViewModel : ViewModel() {

    var slotRepository  = SlotRepository()

    private val _slots = MutableLiveData<List<SlotDetails>>()
    val slots: LiveData<List<SlotDetails>> get() = _slots

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    private val repository = SignUpRepository()

    private val _registerResult = MutableLiveData<ResponseBody>()
    val registerResult: LiveData<ResponseBody> get() = _registerResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    val name = MutableLiveData("")
    val mothername = MutableLiveData("")
    val fathername = MutableLiveData("")
    val dob = MutableLiveData("")
    val email = MutableLiveData("")
    val phone = MutableLiveData("")
    val emergencyNumber = MutableLiveData("")
    val prepairFor = MutableLiveData("")
    val qualification = MutableLiveData("")
    val aadhar = MutableLiveData("")
    val slot = MutableLiveData("")
    val vehicleNumber = MutableLiveData("")
    val education = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")
    val validationMessage = MutableLiveData<String>()

    public fun fetchSlots(){
       viewModelScope.launch {
           try {
               val response = slotRepository.getSlots()
               if (response.isSuccessful) {
                   _slots.postValue(response.body()?.slot_detail)
               } else {
                   _error.postValue("Error Code: ${response.code()}")
               }
           }catch (e: Exception){
               _error.postValue(e.message ?: "Unknown error")
           }
       }
    }


    public fun registeruser(registraionRequest: HashMap<String, String>){

        _loading.value = true

        viewModelScope.launch {
            try {
                val response = repository.registerUser(registraionRequest)
                if (response.isSuccessful && response.body() != null) {
                    _loading.value =  false
                    _registerResult.postValue(response.body())
                } else {
                    _loading.value =  false
                    if (response.code() == 409) {
                        _error.postValue("Registration failed: Phone Number already exists")
                    }

                }
            }
            catch (e: Exception) {
                _loading.value =  false
                _error.postValue("Error: ${e.message}")
            }
        }
    }

    fun validateFields(): Boolean {
        return when {
            name.value.isEmpty() -> {
                validationMessage.value = "Name is required."
                false
            }
            mothername.value.isEmpty() -> {
                validationMessage.value = "Mother Name is required."
                false
            }
            fathername.value.isEmpty() -> {
                validationMessage.value = "Father Name is required."
                false
            }

            email.value.isEmpty() -> {
                validationMessage.value = "Email is required."
                false
            }
            education.value.isEmpty() -> {
                validationMessage.value = "Education Qualification is required."
                false
            }
            phone.value.isEmpty() -> {
                validationMessage.value = " Contact number is required."
                false
            }

            dob.value.isEmpty() -> {
                validationMessage.value = "Date of Birth is required."
                false
            }

            aadhar.value.isEmpty() -> {
                validationMessage.value = "Aadhar number is required"
                false
            }

            slot.value.isEmpty() -> {
                validationMessage.value = "Select  Slot"
                false
            }

            vehicleNumber.value.isEmpty() -> {
                validationMessage.value = "Vehicle number is required"
                false
            }

            password.value.isEmpty() -> {
                validationMessage.value = "Enter Password"
                false
            }


            confirmPassword.value.isEmpty() -> {
                validationMessage.value = "Enter Confirm Password"
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()-> {
                validationMessage.value = "Invalid email"
                false
            }
            !phone.value.toString().matches(Regex("^\\d{10}$")) -> {
                validationMessage.value = "Invalid phone number"
                false
            }
            password.value.length < 6 -> {
                validationMessage.value = "Password too short"
                false
            }
            password.value != confirmPassword.value -> {
                validationMessage.value = "Passwords do not match"
                false
            }

            else -> {
                validationMessage.value = "Success"
                true
            }
        }

    }
}