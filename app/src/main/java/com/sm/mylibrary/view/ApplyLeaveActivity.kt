package com.sm.mylibrary.view

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.sm.mylibrary.databinding.ActivityApplyLeaveBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.utils.Utils
import com.sm.mylibrary.viewmodel.ActivityApplyLeaveViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ApplyLeaveActivity : AppCompatActivity() {

    val requestData = HashMap<String, String>()
    val activityApplyLeaveViewModel = ActivityApplyLeaveViewModel()

    lateinit var activityApplyLeaveBinding : ActivityApplyLeaveBinding

    private lateinit var progressDialog: ProgressDialog

    var loginResponse : LoginResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityApplyLeaveBinding = ActivityApplyLeaveBinding.inflate(layoutInflater)
        setContentView(activityApplyLeaveBinding.root)

        val sheardPreferenceViewModel = SheardPreferenceViewModel()
        val loginData = sheardPreferenceViewModel.loadData(Constants.LOGIN_RESPONSE)
        loginResponse = Gson().fromJson(loginData, LoginResponse::class.java)

        progressDialog = ProgressDialog(this).apply {
            setMessage("Please wait...")
            setCancelable(false)
        }

        activityApplyLeaveBinding.tvLeaveStart.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { view: DatePicker?, year1: Int, month1: Int, dayOfMonth: Int ->
                    val dayStr = String.format("%02d", dayOfMonth)
                    val monthStr = String.format("%02d", month1 + 1) // Month is
                    val selectedDate = dayStr + "/" + monthStr+ "/" + year1
                    val formattedDate = Utils.dateFormatConveter(selectedDate)
                    activityApplyLeaveBinding.tvLeaveStart.setText(formattedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        activityApplyLeaveBinding.tvLeaveEnd.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR)
            val month: Int = calendar.get(Calendar.MONTH)
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { view: DatePicker?, year1: Int, month1: Int, dayOfMonth: Int ->

                    val dayStr = String.format("%02d", dayOfMonth)
                    val monthStr = String.format("%02d", month1 + 1) // Month is
                    val selectedDate = dayStr + "/" + monthStr+ "/" + year1

                    val formattedDate = Utils.dateFormatConveter(selectedDate)
                    activityApplyLeaveBinding.tvLeaveEnd.setText(formattedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        fun getDaysDifferenceOld(startDate: String, endDate: String): Long {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val start = sdf.parse(startDate)
            val end = sdf.parse(endDate)

            val diffInMillis = end.time - start.time
            return diffInMillis / (1000 * 60 * 60 * 24)
        }
         activityApplyLeaveBinding.btnApplyLeave.setOnClickListener {
             activityApplyLeaveViewModel.leaveStartDate.value = activityApplyLeaveBinding.tvLeaveStart.text.toString()
             activityApplyLeaveViewModel.leaveEndDate.value = activityApplyLeaveBinding.tvLeaveEnd.text.toString()
             activityApplyLeaveViewModel.leaveReason.value = activityApplyLeaveBinding.etReason.text.toString()

             var daydiff = ""
              if (activityApplyLeaveViewModel.leaveStartDate.value != "" && activityApplyLeaveViewModel.leaveEndDate.value != "" )
              {
                   daydiff = getDaysDifferenceOld(activityApplyLeaveViewModel.leaveStartDate.value,  activityApplyLeaveViewModel.leaveEndDate.value).toString()
              }

                 if (activityApplyLeaveViewModel.checKValidation()) {
                     requestData["fromdate"] = activityApplyLeaveViewModel.leaveStartDate.value.toString()
                     requestData["todate"] = activityApplyLeaveViewModel.leaveEndDate.value
                     requestData["detail"] = activityApplyLeaveViewModel.leaveReason.value
                     requestData["userid"] = loginResponse?.userDetail?.id.toString()
                     requestData["ecode"] = loginResponse?.userDetail?.ecode.toString()
                     requestData["status"] = "Pending"
                     requestData["days"] = daydiff.toString()
                     requestData["halfday"] = "Leave"

                     activityApplyLeaveViewModel.applyLeave(requestData)


             }


         }

        activityApplyLeaveViewModel.validationMessage.observe(this) {
            if (it != "Success") {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        activityApplyLeaveViewModel.applyleaverResult.observe(this) {
            if (it != null) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        activityApplyLeaveViewModel.loading.observe(this) { isLoading ->

            if (isLoading) progressDialog.show() else progressDialog.dismiss()
        }

        activityApplyLeaveBinding.imgBack.setOnClickListener {
            finish()
        }


    }
}