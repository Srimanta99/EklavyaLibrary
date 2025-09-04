package com.sm.mylibrary.repository

import com.sm.mylibrary.model.attendance.PunchInRequest
import com.sm.mylibrary.model.attendance.PunchInResponse
import com.sm.mylibrary.retrofit.RetrofitClient
import retrofit2.Response

class ApplyAttendenceRepository  {

    suspend fun ApplyPunchIn(punchInRequest: HashMap<String, String>): Response<PunchInResponse> {
        return RetrofitClient.apiService.applyPunchIn(punchInRequest)
    }

    suspend fun ApplyPunchOut(punchInRequest: HashMap<String, String>): Response<PunchInResponse> {
        return RetrofitClient.apiService.applyPunchOut(punchInRequest)
    }
}