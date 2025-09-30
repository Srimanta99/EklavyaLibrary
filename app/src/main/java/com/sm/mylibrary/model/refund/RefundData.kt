package com.sm.mylibrary.model.refund

data class RefundData(val responsecode : String,
val status: String,
val message: String,
val fees_detail: ArrayList<FeesDetails>
,val refund_detail : ArrayList<RefundDetails>)
