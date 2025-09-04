package com.sm.mylibrary.view

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.sm.mylibrary.databinding.ActivityAttendenceBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.viewmodel.ActivityAttendanceViewModel
import com.sm.mylibrary.viewmodel.ActivityMainViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


class AttendenceActivity : AppCompatActivity() {

     lateinit var activityAttendenceBindingBinding: ActivityAttendenceBinding
    private lateinit var progressDialog: ProgressDialog
     lateinit var  activityAttendanceViewModel: ActivityAttendanceViewModel
    var loginResponse : LoginResponse? = null
    val requestData = HashMap<String, String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityAttendenceBindingBinding = ActivityAttendenceBinding.inflate(layoutInflater)
        setContentView(activityAttendenceBindingBinding.root)
        activityAttendanceViewModel = ViewModelProvider(this).get(ActivityAttendanceViewModel::class.java)

        val sheardPreferenceViewModel = SheardPreferenceViewModel()
        val loginData = sheardPreferenceViewModel.loadData(Constants.LOGIN_RESPONSE)
        loginResponse = Gson().fromJson(loginData, LoginResponse::class.java)

        progressDialog = ProgressDialog(this).apply {
            setMessage("Loading...")
            setCancelable(false)
        }

        activityAttendenceBindingBinding.rlApplyPunchin.setOnClickListener {
            var formattedDate  : String = ""
            val date = LocalDate.now()
             formattedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            } else ({
                 val currentTime: Date = Calendar.getInstance().getTime()
                 val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                 formattedDate = sdf.format(currentTime)
             }).toString()

            val time = LocalTime.now()
            val formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))


            progressDialog.show()
            requestData["eid"] = loginResponse?.userDetail?.id.toString()
            requestData["ecode"] = loginResponse?.userDetail?.ecode.toString()
            requestData["date"] =formattedDate
            requestData["time"] = formattedTime
            requestData["action"] = "punchin"
            requestData["seat_type"] = "Fixed"
            requestData["seat_no"] = "B-26"
            activityAttendanceViewModel.applyPunchIn(requestData)
        }


        activityAttendenceBindingBinding.rlApplyPunchout.setOnClickListener {
            var formattedDate  : String = ""
            val date = LocalDate.now()
            formattedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            } else ({
                val currentTime: Date = Calendar.getInstance().getTime()
                val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                formattedDate = sdf.format(currentTime)
            }).toString()

            val time = LocalTime.now()
            val formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))


            progressDialog.show()
            requestData["eid"] = loginResponse?.userDetail?.id.toString()
            requestData["ecode"] = loginResponse?.userDetail?.ecode.toString()
            requestData["date"] =formattedDate
            requestData["time"] = formattedTime
            requestData["action"] = "punchout"
            requestData["seat_type"] = "Fixed"
            requestData["seat_no"] = "B-26"
            activityAttendanceViewModel.applyPunchIn(requestData)
        }


        activityAttendanceViewModel.validationMessage.observe(this) {
            if (it != "Success") {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        activityAttendanceViewModel.applyPunchinResult.observe(this) {
            if (it != null) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }

//            if (it.responsecode.equals("200")) {
//
//            }
        }

        activityAttendanceViewModel.loading.observe(this) { isLoading ->

            if (isLoading) progressDialog.show() else progressDialog.dismiss()
        }

        activityAttendenceBindingBinding.imgBack.setOnClickListener {
            finish()
        }
    }
}