package com.sm.mylibrary.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.google.gson.Gson
import com.sm.mylibrary.R
import com.sm.mylibrary.databinding.ActivityMainBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.view.fragments.HomeFragment
import com.sm.mylibrary.view.fragments.ProfileFragment
import com.sm.mylibrary.viewmodel.ActivityMainViewModel
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding  : ActivityMainBinding
    lateinit var  activityMainViewModel: ActivityMainViewModel

    val sheardPreferenceViewModel = SheardPreferenceViewModel()
    var loginResponse : LoginResponse? = null



   // private lateinit var progressDialog: ProgressDialog

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


        // Observe menu clicks
        activityMainViewModel.menuAction.observe(this) {
            when (it) {
                0 -> loadFragment(HomeFragment())
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
                    Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
                }
//                6 ->{
//                    Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
//                }
                6 -> {
                    Toast.makeText(this, "Successfully Logged out", Toast.LENGTH_SHORT).show()
                    sheardPreferenceViewModel.clearPreferenceData()
                    sheardPreferenceViewModel.saveData(Constants.IS_LOGIN,"")

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
            activityMainBinding.drawerLayout.closeDrawers()
        }
    }

    private fun showuserDetails() {
        activityMainBinding.tvUsername.setText("Welcome "+ loginResponse?.userDetail?.name)
        activityMainBinding.userEmail.setText(loginResponse?.userDetail?.email)
        activityMainBinding.userName.setText(loginResponse?.userDetail?.name)

        if (loginResponse?.userDetail?.photo!=null) {
            Picasso.get()
                .load(loginResponse?.userDetail?.profile_path + loginResponse?.userDetail?.photo)
                .placeholder(R.drawable.user_profile) // optional
                .error(R.drawable.user_profile)       // optional
                .into(activityMainBinding.userImageCircular)
        }

      //  activityMainBinding.tvUsername.setText("Welcome "+ sheardPreferenceViewModel.loadData(Constants.USERNAME))
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}