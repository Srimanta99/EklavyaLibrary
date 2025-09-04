package com.sm.mylibrary.repository

import com.sm.mylibrary.model.banner.BannerResponse
import com.sm.mylibrary.retrofit.RetrofitClient
import retrofit2.Response

class HomeBannerRepository {

    suspend fun getBanner(): Response<BannerResponse> {
        return RetrofitClient.apiService.getBanner()
    }
}