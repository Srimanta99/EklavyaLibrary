package com.sm.mylibrary.model.notififation

data class NotificationModel(val responsecode: String, val status: String, val message: String,
                             val notification :ArrayList<NotificationDetails>)
