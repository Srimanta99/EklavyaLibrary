package com.sm.mylibrary.repository

import com.sm.mylibrary.model.notififation.NotificationModel
import com.sm.mylibrary.retrofit.RetrofitClient
import retrofit2.Response

class HomeRepository {
    suspend fun getnotification( request : Map<String, String>): Response<NotificationModel> {
        return RetrofitClient.apiService.getNotification(request)
    }


}