package com.sm.mylibrary.repository

import com.sm.mylibrary.model.slot.Slot
import com.sm.mylibrary.model.slot.SlotDetails
import com.sm.mylibrary.retrofit.RetrofitClient
import retrofit2.Response

class SlotRepository {
    suspend fun getSlots(): Response<Slot> {
        return RetrofitClient.apiService.getSlots()
    }
}