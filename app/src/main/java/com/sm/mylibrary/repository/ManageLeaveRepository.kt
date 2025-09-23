package com.sm.mylibrary.repository

import com.sm.mylibrary.model.manageleave.ManageLeaveResponse
import com.sm.mylibrary.retrofit.RetrofitClient
import retrofit2.Response

class ManageLeaveRepository {
    suspend fun getLeaveData( request: HashMap<String, String>): Response<ManageLeaveResponse> {
        return RetrofitClient.apiService.getLeaveData(request)
    }
}