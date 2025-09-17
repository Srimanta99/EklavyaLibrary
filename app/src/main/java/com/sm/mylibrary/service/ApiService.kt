package com.sm.mylibrary.service

import com.sm.mylibrary.model.applyleave.ApplyLeaveResponse
import com.sm.mylibrary.model.attendance.PunchInResponse
import com.sm.mylibrary.model.banner.BannerResponse
import com.sm.mylibrary.model.imageupload.ImageUploadResponse
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.model.login.User
import com.sm.mylibrary.model.notififation.NotificationModel
import com.sm.mylibrary.model.refund.RefundData
import com.sm.mylibrary.model.slot.Slot
import com.sm.mylibrary.repository.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("dropdownoption.php?action=slot")
    suspend fun getSlots(): Response<Slot>

//    @FormUrlEncoded
//    @POST("register.php")
//    suspend fun registerUser(@FieldMap fields: RegistraionRequest): Response<ResponseBody>

    @FormUrlEncoded
    @POST("register.php")
    suspend fun registerUser(@FieldMap fields: Map<String, String>): Response<ResponseBody>

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<ResponseBody>

    @POST("postaction.php")
    suspend fun sendUser(@Body user: User): Response<LoginResponse>

    @FormUrlEncoded
    @POST("applyleave.php")
    suspend fun applyLeave(@FieldMap fields: Map<String, String>): Response<ApplyLeaveResponse>


    @FormUrlEncoded
    @POST("attandence.php?action=punchin")
    suspend fun applyPunchIn(@FieldMap fields: Map<String, String>): Response<PunchInResponse>

    @FormUrlEncoded
    @POST("attandence.php?action=punchin")
    suspend fun applyPunchOut(@FieldMap fields: Map<String, String>): Response<PunchInResponse>

    @FormUrlEncoded
    @POST("attandence.php?action=refund")
    suspend fun reFunds(@FieldMap fields: Map<String, String>): Response<RefundData>

    @GET("attandence.php?action=banner")
    suspend fun getBanner(): Response<BannerResponse>

    @FormUrlEncoded
    @POST("postphoto.php")
    suspend fun profileImageUpload(@FieldMap fields: Map<String, String>): Response<ImageUploadResponse>


    @FormUrlEncoded
    @POST("postphoto.php")
    suspend fun aadharImageUpload(@FieldMap fields: Map<String, String>): Response<ImageUploadResponse>

    @FormUrlEncoded
    @POST("attandence.php?action=notification")
    suspend fun getNotification(@FieldMap fields: Map<String, String>): Response<NotificationModel>

}