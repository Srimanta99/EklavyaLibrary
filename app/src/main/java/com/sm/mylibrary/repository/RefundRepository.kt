package com.sm.mylibrary.repository

import com.sm.mylibrary.model.imageupload.ImageUploadResponse
import com.sm.mylibrary.retrofit.RetrofitClient
import retrofit2.Response

class RefundRepository {
    suspend fun getreFunds( request: HashMap<String, String>): Response<ImageUploadResponse> {
        return RetrofitClient.apiService.profileImageUpload(request)
    }
}