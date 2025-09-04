package com.sm.mylibrary.model.login

import com.google.gson.annotations.SerializedName
import com.sm.mylibrary.model.login.UserDetail


data class LoginResponse (

  @SerializedName("responsecode" ) var responsecode : String?     = null,
  @SerializedName("status"       ) var status       : String?     = null,
  @SerializedName("message"      ) var message      : String?     = null,
  @SerializedName("user_id"      ) var userId       : Int?        = null,
  @SerializedName("admin_type"   ) var adminType    : String?     = null,
  @SerializedName("user_detail"  ) var userDetail   : UserDetail? = UserDetail()

)