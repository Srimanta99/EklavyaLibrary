package com.sm.mylibrary.repository

import com.sm.mylibrary.retrofit.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Response

class SignUpRepository {
    suspend fun registerUser(request: HashMap<String, String>): Response<ResponseBody> {
        return RetrofitClient.apiService.registerUser(request)
    }
}