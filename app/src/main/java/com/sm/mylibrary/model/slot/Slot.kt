package com.sm.mylibrary.model.slot

data class Slot(val responsecode: String,
                val status: String,
                val message: String,
                val slot_detail: List<SlotDetails>) {

}