package com.sm.mylibrary.repository

import com.sm.mylibrary.model.applyleave.ApplyLeaveResponse
import com.sm.mylibrary.retrofit.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Response

class ApplyLeaveRepository {
    suspend fun applyLeave(request: HashMap<String, String>): Response<ApplyLeaveResponse> {
        return RetrofitClient.apiService.applyLeave(request)
    }
}