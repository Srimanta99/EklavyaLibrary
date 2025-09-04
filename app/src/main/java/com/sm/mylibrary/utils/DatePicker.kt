package com.sm.mylibrary.utils

import android.app.DatePickerDialog
import android.content.Context
import com.sm.mylibrary.view.SignUpActivity
import java.util.Calendar

object DatePicker {

    fun showDatePicker( context: Context, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(selectedDate) // <-- returning date here
        }, year, month, day)

        datePickerDialog.show()
    }
}