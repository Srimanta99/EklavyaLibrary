package com.sm.mylibrary.repository

import com.sm.mylibrary.model.refund.RefundData
import com.sm.mylibrary.retrofit.RetrofitClient
import retrofit2.Response

class RefundRepository {
    suspend fun getreFunds( request: HashMap<String, String>): Response<RefundData> {
        return RetrofitClient.apiService.reFunds(request)
    }
}