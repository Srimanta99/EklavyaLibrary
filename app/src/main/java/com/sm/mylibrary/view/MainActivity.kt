package com.sm.mylibrary.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.sm.mylibrary.R
import com.sm.mylibrary.databinding.ActivityMainBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.model.notififation.NotificationDetails
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.utils.Utils
import com.sm.mylibrary.view.fragments.HomeFragment
import com.sm.mylibrary.view.fragments.ManageLeaveFragment
import com.sm.mylibrary.view.fragments.ProfileFragment
import com.sm.mylibrary.view.fragments.RefundFragment
import com.sm.mylibrary.viewmodel.ActivityMainViewModel


class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding  : ActivityMainBinding
    lateinit var  activityMainViewModel: ActivityMainViewModel

    val sheardPreferenceViewModel = SheardPreferenceViewModel()
    var loginResponse : LoginResponse? = null

     var notificationCount = ArrayList<NotificationDetails>()

   // private lateinit var progressDialog: ProgressDialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainViewModel = ViewModelProvider(this).get(ActivityMainViewModel::class.java)

        activityMainBinding.viewModel = activityMainViewModel
        activityMainBinding.lifecycleOwner = this

        val toggle = ActionBarDrawerToggle(this, activityMainBinding.drawerLayout, activityMainBinding.toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        activityMainBinding.drawerLayout.addDrawerListener(toggle)
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)
        toggle.syncState()




//        progressDialog = ProgressDialog(this).apply {
//            setMessage("Loading...")
//            setCancelable(false)
//        }




       // val sharedViewModel = ViewModelProvider(this)[SheardViewModel::class.java]
       // sharedViewModel.loginResponse.observe(this){
           // loginResponse = it
       // }
       // loginResponse = sharedViewModel.loginResponse.value

         val loginData = sheardPreferenceViewModel.loadData(Constants.LOGIN_RESPONSE)
         loginResponse = Gson().fromJson(loginData, LoginResponse::class.java)

        showuserDetails()
        loadFragment(HomeFragment())
        val requestData = HashMap<String, String>()
        requestData["uid"] = loginResponse?.userId.toString()
        // requestData["name"] = "131"

            activityMainViewModel.getnotification(requestData)

        activityMainViewModel.notificationResult.observe(this){
            if(it.responsecode == "200"){
              //  Log.d("notification RESPONSE", it.notification.toString())
                for ( i in 0 until it.notification.size){
                    notificationCount.add(it.notification[i])
                }
                activityMainBinding.tvNotificationCount.text = notificationCount.size.toString()
            }
        }


        activityMainBinding.imgNotification.setOnClickListener {
            if(notificationCount.size>0) {
                val intent = Intent(this, ActivityNotification::class.java)
                intent.putExtra("notification", notificationCount!!)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "No notification", Toast.LENGTH_SHORT).show()
            }
        }

        activityMainBinding.tvDayPending.setOnClickListener {
            val validtill = loginResponse?.userDetail?.lastDate
           // val days = validtill?.let { it1 -> Utils.daysBetweenTodayAnd(it1) }
            val days = Utils.daysBetweenTodayAnd("2025-10-07")
            //Toast.makeText(this, "Days Left $days", Toast.LENGTH_SHORT).show()
            showPopup(days.toString())

        }

        // Observe menu clicks
        activityMainViewModel.menuAction.observe(this) {
            when (it) {
                0 -> {
                    val fm = supportFragmentManager // or parentFragmentManager if inside a Fragment
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    loadFragment(HomeFragment())
                }
                1 -> loadFragment(ProfileFragment())
                2 ->{
                   // Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AttendenceActivity::class.java)
                    startActivity(intent)

                }
                3 ->{
                    val intent = Intent(this, AttendenceActivity::class.java)
                    startActivity(intent)
                   // Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
                }
                4 ->{
                   // Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ApplyLeaveActivity::class.java)
                    startActivity(intent)
                }
                5 ->{
                   // Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
                    loadFragment(ManageLeaveFragment())
                }
                6 ->{
                   // Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
                    loadFragment(RefundFragment())
                }
                7 -> {
                    Toast.makeText(this, "Successfully Logged out", Toast.LENGTH_SHORT).show()

                    val rememberStatus = sheardPreferenceViewModel.loadData(Constants.REMEMBERME_STATUS)
                    val USERNAME = sheardPreferenceViewModel.loadData(Constants.REMEMBEME_USERNAME)
                    val PASSWORD = sheardPreferenceViewModel.loadData(Constants.REMEMBERME_PASSWORD)



                    sheardPreferenceViewModel.clearPreferenceData()
                    sheardPreferenceViewModel.saveData(Constants.IS_LOGIN,"")

                    if (rememberStatus.equals("true")){
                        sheardPreferenceViewModel.saveData(Constants.REMEMBERME_STATUS, rememberStatus)
                        sheardPreferenceViewModel.saveData(Constants.REMEMBEME_USERNAME, USERNAME)
                        sheardPreferenceViewModel.saveData(Constants.REMEMBERME_PASSWORD, PASSWORD)
                    }else
                        sheardPreferenceViewModel.saveData(Constants.REMEMBERME_STATUS, "false")

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
            activityMainBinding.drawerLayout.closeDrawers()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showuserDetails() {
        activityMainBinding.tvUsername.setText("Welcome "+ loginResponse?.userDetail?.name)
        activityMainBinding.userEmail.setText(loginResponse?.userDetail?.email)
        activityMainBinding.userName.setText(loginResponse?.userDetail?.name)

        val validtill = loginResponse?.userDetail?.lastDate
        // val days = validtill?.let { it1 -> Utils.daysBetweenTodayAnd(it1) }
        val days = Utils.daysBetweenTodayAnd("2025-10-07")
        if (days>0){
            activityMainBinding.tvDayPending.text = days.toString()
        }else{
            showPopupofDeActivatedAccount(days.toString())
        }


        if (sheardPreferenceViewModel.loadData(Constants.PROFILE_IMAGE_PATH)!="") {

            val profile = sheardPreferenceViewModel.loadData(Constants.PROFILE_IMAGE_PATH)

            Glide.with(this)
                .load(profile)
                // .load(loginResponse?.userDetail?.profile_path + loginResponse?.userDetail?.photo)
                .placeholder(R.drawable.user_profile) // optional
                .error(R.drawable.user_profile)       // optional
                .into(activityMainBinding.userImageCircular)
        }

      /*  if (loginResponse?.userDetail?.photo!=null) {
            Picasso.get()
                .load(loginResponse?.userDetail?.profile_path + loginResponse?.userDetail?.photo)
                .placeholder(R.drawable.user_profile) // optional
                .error(R.drawable.user_profile)       // optional
                .into(activityMainBinding.userImageCircular)
        }*/

      //  activityMainBinding.tvUsername.setText("Welcome "+ sheardPreferenceViewModel.loadData(Constants.USERNAME))
    }

     fun updateProfileImage(url :String){
         Glide.with(this)
             .load(url)
             // .load(loginResponse?.userDetail?.profile_path + loginResponse?.userDetail?.photo)
             .placeholder(R.drawable.user_profile) // optional
             .error(R.drawable.user_profile)       // optional
             .into(activityMainBinding.userImageCircular)
     }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


    private  fun showPopup( days : String){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.pop_up_days_left, null)
        val tvMessage = dialogView.findViewById<TextView>(R.id.popupText)
        val imgcross = dialogView.findViewById<ImageView>(R.id.img_cross)
        tvMessage.text = " Your  account will be suspended/deactivated, You have $days days left, Please Contact admin."
        // Build dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            /*.setPositiveButton("OK") { d, _ ->
                d.dismiss()
            }*/
          //  .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
            .create()

        imgcross.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }


    private  fun showPopupofDeActivatedAccount( days : String){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.pop_up_days_left, null)
        val tvMessage = dialogView.findViewById<TextView>(R.id.popupText)
        val imgcross = dialogView.findViewById<ImageView>(R.id.img_cross)
        tvMessage.text = " Your account Suspended, Please Contact admin."


        // Build dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            /*.setPositiveButton("OK") { d, _ ->
                d.dismiss()
            }*/
            //  .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
            .create()

        imgcross.setOnClickListener {

            dialog.dismiss()

            val rememberStatus = sheardPreferenceViewModel.loadData(Constants.REMEMBERME_STATUS)
            val USERNAME = sheardPreferenceViewModel.loadData(Constants.REMEMBEME_USERNAME)
            val PASSWORD = sheardPreferenceViewModel.loadData(Constants.REMEMBERME_PASSWORD)



            sheardPreferenceViewModel.clearPreferenceData()
            sheardPreferenceViewModel.saveData(Constants.IS_LOGIN,"")

            if (rememberStatus.equals("true")){
                sheardPreferenceViewModel.saveData(Constants.REMEMBERME_STATUS, rememberStatus)
                sheardPreferenceViewModel.saveData(Constants.REMEMBEME_USERNAME, USERNAME)
                sheardPreferenceViewModel.saveData(Constants.REMEMBERME_PASSWORD, PASSWORD)
            }else
                sheardPreferenceViewModel.saveData(Constants.REMEMBERME_STATUS, "false")

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }
}