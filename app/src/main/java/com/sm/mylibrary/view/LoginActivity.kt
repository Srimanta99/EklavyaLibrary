package com.sm.mylibrary.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.sm.mylibrary.databinding.ActivityLoginBinding
import com.sm.mylibrary.model.login.User
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.viewmodel.ActivityLoginViewModel


class LoginActivity : AppCompatActivity() {

    lateinit var loginViewModel : ActivityLoginViewModel
    lateinit var loginViewBinding : ActivityLoginBinding

    private lateinit var progressDialog: ProgressDialog
    val sheardPreferenceViewModel = SheardPreferenceViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        loginViewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginViewBinding.root)
        loginViewModel = ViewModelProvider(this).get(ActivityLoginViewModel::class.java)

        progressDialog = ProgressDialog(this).apply {
            setMessage("Loading...")
            setCancelable(false)
        }

        loginViewModel.loading.observe(this) { isLoading ->
            if (isLoading) progressDialog.show() else progressDialog.dismiss()
        }

        loginViewModel.loginResult.observe(this){
            if(it.responsecode == "200"){
                sheardPreferenceViewModel.saveData(Constants.IS_LOGIN,"login")
//                it.userDetail?.name?.let { it1 -> sheardPreferenceViewModel.saveData(Constants.USERNAME, it1) }
//                it.userDetail?.email?.let { it1 -> sheardPreferenceViewModel.saveData(Constants.EMAIL, it1) }
//                it.userDetail?.status?.let { it1 -> sheardPreferenceViewModel.saveData(Constants.STATUS, it1) }
//                it.userDetail?.phone?.let { it2 -> sheardPreferenceViewModel.saveData(Constants.PHONE, it2.toString()) }
               //  val sharedViewModel = ViewModelProvider(this)[SheardViewModel::class.java]
               //  sharedViewModel.setLoginResponse(it)
                 val gson = Gson()
                 val json: String = gson.toJson(it)
                 sheardPreferenceViewModel.saveData(Constants.LOGIN_RESPONSE, json)
                 sheardPreferenceViewModel.saveData(Constants.PROFILE_IMAGE_PATH,it?.userDetail?.profile_path + it?.userDetail?.photo)
                sheardPreferenceViewModel.saveData(Constants.AADHAR_FRONT_IMAGE_PATH,it?.userDetail?.aadhar_path + it?.userDetail?.photo1)
                sheardPreferenceViewModel.saveData(Constants.AADHAR_BACK_IMAGE_PATH,it?.userDetail?.aadhar_path + it?.userDetail?.photo2)

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this,it.message, Toast.LENGTH_LONG).show()
            }
        }


        loginViewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        loginViewBinding.txtSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        loginViewBinding.txtForgotpassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivitty::class.java))
           // Toast.makeText(this, "Under Development", Toast.LENGTH_LONG).show()

        }

        loginViewBinding.btnLogin.setOnClickListener {
            loginViewModel.username.value = loginViewBinding.txtUsername.text.toString()
            loginViewModel.password.value = loginViewBinding.txtPassword.text.toString()
            if(loginViewModel.onLoginClicked()) {
               // startActivity(Intent(this, MainActivity::class.java))

                val user = User(loginViewModel.username.value, loginViewModel.password.value)
                loginViewModel.loginUser(user)
            }



        }


    }
}