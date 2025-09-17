package com.sm.mylibrary.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sm.mylibrary.databinding.ActivitySignUpBinding
import com.sm.mylibrary.utils.DatePicker
import com.sm.mylibrary.viewmodel.ActivitySignUpViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class SignUpActivity : AppCompatActivity() {

     lateinit var  activitySignUpViewModel: ActivitySignUpViewModel
     lateinit var  signUpBinding: ActivitySignUpBinding

     var list = ArrayList<String>()

    val requestData = HashMap<String, String>()

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)
        activitySignUpViewModel = ViewModelProvider(this).get(ActivitySignUpViewModel::class.java)

        signUpBinding.txtDob.setOnClickListener {
            DatePicker.showDatePicker(this) { selectedDate ->
                val inputFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())

// formatter for your desired output format:
                val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                val date = inputFormat.parse(selectedDate)   // convert string → Date
                val formattedDate = outputFormat.format(date!!)
                signUpBinding.txtDob.setText(formattedDate)
            }
        }

        progressDialog = ProgressDialog(this).apply {
            setMessage("Loading...")
            setCancelable(false)
        }

        signUpBinding.btnSignup.setOnClickListener {
           // Toast.makeText( this, "Under Development.", Toast.LENGTH_SHORT).show()
            activitySignUpViewModel.name.value = signUpBinding.txtName.text.toString()
            activitySignUpViewModel.email.value = signUpBinding.txtEmail.text.toString()
            activitySignUpViewModel.phone.value = signUpBinding.txtPhone.text.toString()
            activitySignUpViewModel.password.value = signUpBinding.txtPassword.text.toString()
            activitySignUpViewModel.confirmPassword.value = signUpBinding.txtConpassword.text.toString()
            activitySignUpViewModel.dob.value = signUpBinding.txtDob.text.toString()
            activitySignUpViewModel.slot.value = signUpBinding.txtSlot.text.toString()
            activitySignUpViewModel.mothername.value = signUpBinding.txtMothername.text.toString()
            activitySignUpViewModel.fathername.value = signUpBinding.txtFathername.text.toString()
            activitySignUpViewModel.dob.value = signUpBinding.txtDob.text.toString()
            activitySignUpViewModel.aadhar.value = signUpBinding.txtAadhar.text.toString()
            activitySignUpViewModel.vehicleNumber.value = signUpBinding.txtVehicle.text.toString()
            activitySignUpViewModel.education.value = signUpBinding.txtEduquali.text.toString()
            activitySignUpViewModel.qualification.value = signUpBinding.txtEduquali.text.toString()
            activitySignUpViewModel.prepairFor.value =  signUpBinding.txtPrepare.text.toString()

            if (activitySignUpViewModel.validateFields()) {
              //  Toast.makeText(this, "Registration Successful.", Toast.LENGTH_SHORT).show()

                if(signUpBinding.chkTermCondition.isChecked) {
                    requestData["name"] = activitySignUpViewModel.name.value.toString()
                    requestData["email"] = activitySignUpViewModel.email.value.toString()
                    requestData["phone"] = activitySignUpViewModel.phone.value.toString()
                    requestData["fname"] = activitySignUpViewModel.fathername.value.toString()
                    requestData["mname"] = activitySignUpViewModel.mothername.value.toString()
                    requestData["ephone"] = activitySignUpViewModel.phone.value.toString()
                    requestData["dob"] = activitySignUpViewModel.dob.value.toString()
                    requestData["qualification"] = activitySignUpViewModel.qualification.value.toString()
                    requestData["aadhar"] = activitySignUpViewModel.aadhar.value.toString()
                    requestData["salary"] = activitySignUpViewModel.prepairFor.value.toString()
                    requestData["password"] = activitySignUpViewModel.password.value.toString()
                    requestData["admin_type"] = "student"
                    requestData["timing"] = "I"
                    requestData["shiftin"] = activitySignUpViewModel.slot.value.toString()
                    requestData["vihno"] = activitySignUpViewModel.vehicleNumber.value.toString()
//

                    activitySignUpViewModel.registeruser(requestData)

//                    activitySignUpViewModel.registeruser(RegistraionRequest(activitySignUpViewModel.name.value.toString(),activitySignUpViewModel.email.value.toString(),
//                        activitySignUpViewModel.phone.value.toString(),activitySignUpViewModel.fathername.value.toString(),
//                        activitySignUpViewModel.mothername.value.toString(),
//                        activitySignUpViewModel.phone.value.toString(),activitySignUpViewModel.dob.value.toString(),
//                        activitySignUpViewModel.qualification.value.toString(), activitySignUpViewModel.aadhar.value.toString(),
//                        activitySignUpViewModel.prepairFor.value.toString(), activitySignUpViewModel.password.value.toString(),
//                        "student", "I", activitySignUpViewModel.slot.value.toString(), activitySignUpViewModel.vehicleNumber.value.toString()
//                        ))
                }else{
                    Toast.makeText(this, "Please accept terms and conditions.", Toast.LENGTH_SHORT).show()
                }

            }

        }
        // call slot drop down Api
        activitySignUpViewModel.fetchSlots()

        signUpBinding.txtSlot.setOnClickListener {
            if( list.size > 0)
                signUpBinding.txtSlot.showDropDown()
            else
               activitySignUpViewModel.fetchSlots()
        }

        signUpBinding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        activitySignUpViewModel.loading.observe(this) { isLoading ->
            if (isLoading) progressDialog.show() else progressDialog.dismiss()
        }

        activitySignUpViewModel.slots.observe(this){

            list = ArrayList<String>()
            for ( i in 0 until it.size){
                list.add(it[i].slottime)
            }
            val adapter = ArrayAdapter(this, android.R.layout.test_list_item, list)
            signUpBinding.txtSlot.setAdapter(adapter)
            //signUpBinding.txtSlot.showDropDown()
        }

        activitySignUpViewModel.registerResult.observe(this) {
            Toast.makeText(this, "Registration Successfully Completed.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))

        }

        activitySignUpViewModel.validationMessage.observe(this) {
            if (it != "Success") {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        activitySignUpViewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }


    }



}