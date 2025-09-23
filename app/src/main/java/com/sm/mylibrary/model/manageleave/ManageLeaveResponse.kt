package com.sm.mylibrary.model.manageleave

data class ManageLeaveResponse( val responsecode : String , val status: String,
                                val message: String, val leave: ArrayList<LeaveData>)
