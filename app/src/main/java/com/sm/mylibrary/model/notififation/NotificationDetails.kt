package com.sm.mylibrary.model.notififation

import java.io.Serializable


data class NotificationDetails(val id :Int, val msg_type: String, val msg: String,
                               val user_id: String, val added_on: String, val added_by: String,
                               val status: String) : Serializable
