package com.sm.mylibrary.model.refund

data class FeesDetails(val id: Int,
                       val userid: Int,
                       val fees_type: String,
                       val from_date: String,
                       val amount: String,
                       val to_date: String,
                       val mode: String,
                       val status: String,
                       val doc: String,
                       val detail: String,
                       val added_on: String,
                       val cash: Int,
                       val online: Int,
                       val approval: String,
                       val adminid: Int,
                       val addedon: String,
                       val approve_date : String,
                        val month_abbr : String) {
}