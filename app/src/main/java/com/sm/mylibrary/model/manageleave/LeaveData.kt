package com.sm.mylibrary.model.manageleave

data class LeaveData( val id: Int,
                      val ecode: String,
                      val date: String,
                      val leavemsg: String,
                      val fromdate: String,
                      val todate: String,
                      val halfday: String,
                      val status: String,
                      val appoveddate: String,
                      val days: Int,
                      val userid: Int)
