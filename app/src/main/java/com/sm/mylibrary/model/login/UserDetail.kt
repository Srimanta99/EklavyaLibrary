package com.sm.mylibrary.model.login

import com.google.gson.annotations.SerializedName


data class UserDetail (

  @SerializedName("id"            ) var id            : Int?    = null,
  @SerializedName("username"      ) var username      : String? = null,
  @SerializedName("password"      ) var password      : String? = null,
  @SerializedName("email"         ) var email         : String? = null,
  @SerializedName("admin_type"    ) var adminType     : String? = null,
  @SerializedName("status"        ) var status        : String? = null,
  @SerializedName("ecode"         ) var ecode         : String? = null,
  @SerializedName("name"          ) var name          : String? = null,
  @SerializedName("timing"        ) var timing        : String? = null,
  @SerializedName("salary"        ) var salary        : String? = null,
  @SerializedName("date"          ) var date          : String? = null,
  @SerializedName("shiftout"      ) var shiftout      : String? = null,
  @SerializedName("shiftin"       ) var shiftin       : String? = null,
  @SerializedName("photo"         ) var photo         : String? = null,
  @SerializedName("fname"         ) var fname         : String? = null,
  @SerializedName("mname"         ) var mname         : String? = null,
  @SerializedName("phone"         ) var phone         : Long?    = null,
  @SerializedName("ephone"        ) var ephone        : Long?    = null,
  @SerializedName("dob"           ) var dob           : String? = null,
  @SerializedName("qualification" ) var qualification : String? = null,
  @SerializedName("aadhar"        ) var aadhar        : String? = null,
  @SerializedName("photo1"        ) var photo1        : String? = null,
  @SerializedName("photo2"        ) var photo2        : String? = null,
  @SerializedName("reg_date"      ) var regDate       : String? = null,
  @SerializedName("approve_date"  ) var approveDate   : String? = null,
  @SerializedName("last_date"     ) var lastDate      : String? = null,
  @SerializedName("permission"    ) var permission    : String? = null,
  @SerializedName("vihno"         ) var vihno         : String? = null,
  @SerializedName("profile_path"  ) var profile_path  : String? = null,
  @SerializedName("aadhar_path"   ) var aadhar_path   : String? = null,
  @SerializedName("slid"          ) var slid          : String? = null,
  @SerializedName("userid"        ) var userid        : String? = null,
   @SerializedName("seat_type"    ) var seat_type      : String? = null,
   @SerializedName("types"        ) var types          : String? = null,
  @SerializedName("seat_no"        ) var seat_no       : String? = null,
  @SerializedName("seat_timing"    ) var seat_timing   : String? = null,
  @SerializedName("vari_seat"      ) var vari_seat     : String? = null

)