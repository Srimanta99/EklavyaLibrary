package com.sm.mylibrary.repository

import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.model.login.User
import com.sm.mylibrary.retrofit.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Response

class LoginRepository {
    suspend fun loginUser(request: User): Response<LoginResponse> {
        return RetrofitClient.apiService.sendUser(request)
    }


}