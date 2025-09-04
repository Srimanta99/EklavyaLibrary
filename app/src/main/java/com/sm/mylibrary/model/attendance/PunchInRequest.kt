package com.sm.mylibrary.model.attendance

data class PunchInRequest( val eid : String, val ecode : String, val date : String,
                           val time : String, val action : String, val seat_type : String, val seat_no : String )
