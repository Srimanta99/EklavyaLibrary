package com.sm.mylibrary.model.refund

data class RefundDetail(val id: Int,
                        val exp_type: String,
                        val amount: String,
                        val added_on: String,
                        val added_by: String,
                        val status: String,
                        val detail: String,
                        val paid_by: String,
                        val admin_type: String,
                        val student_detail: String,
                        val sid: Int) {
}