package com.sm.mylibrary.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.google.gson.Gson
import com.sm.mylibrary.R
import com.sm.mylibrary.databinding.FragmentProfileBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.utils.ShowFullScreenImageDialog
import com.sm.mylibrary.viewmodel.FragmentHomeViewModel
import com.sm.mylibrary.viewmodel.FragmentProfileViewModel
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {


    val sheardPreferenceViewModel = SheardPreferenceViewModel()
    var loginResponse : LoginResponse? = null
    var fragmentProfileBinding : FragmentProfileBinding? = null
    lateinit var fragmentProfileViewModel : FragmentProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



       // fragmentProfileViewModel = ViewModelProvider(this).get(FragmentProfileViewModel::class.java)


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginData = sheardPreferenceViewModel.loadData(Constants.LOGIN_RESPONSE)
        loginResponse = Gson().fromJson(loginData, LoginResponse::class.java)

        setvalues()
    }

    private fun setvalues() {
        fragmentProfileBinding?.tvUserName?.text = loginResponse?.userDetail?.name
        if (loginResponse?.userDetail?.types!=null && loginResponse?.userDetail?.seat_no!=null) {
            fragmentProfileBinding?.tvSeatNo?.text = "Seat no -" + loginResponse?.userDetail?.types + " " + loginResponse?.userDetail?.seat_no
        }
        fragmentProfileBinding?.tvEmail?.text = loginResponse?.userDetail?.email
        fragmentProfileBinding?.tvPhone?.text = loginResponse?.userDetail?.phone.toString()
        fragmentProfileBinding?.tvEcode?.text = loginResponse?.userDetail?.ecode



        if (loginResponse?.userDetail?.photo!=null) {
            Picasso.get()
                .load(loginResponse?.userDetail?.profile_path + loginResponse?.userDetail?.photo)
                .placeholder(R.drawable.placeholder) // optional
                .error(R.drawable.placeholder)       // optional
                .into(fragmentProfileBinding?.profileImage!!)
        }



        if (loginResponse?.userDetail?.photo1!=null) {
            Picasso.get()
                .load(loginResponse?.userDetail?.aadhar_path + loginResponse?.userDetail?.photo1)
                .placeholder(R.drawable.placeholder) // optional
                .error(R.drawable.placeholder)       // optional
                .into(fragmentProfileBinding?.imageAdadharFrontside!!)

            fragmentProfileBinding?.imageAdadharFrontside?.setOnClickListener {
                ShowFullScreenImageDialog.showFullScreenDialog(requireContext(), loginResponse?.userDetail?.aadhar_path + loginResponse?.userDetail?.photo1)
            }
        }

        if (loginResponse?.userDetail?.photo2!=null) {
            Picasso.get()
                .load(loginResponse?.userDetail?.aadhar_path + loginResponse?.userDetail?.photo2)
                .placeholder(R.drawable.placeholder) // optional
                .error(R.drawable.placeholder)       // optional
                .into(fragmentProfileBinding?.imageAdadharBackside!!)
            fragmentProfileBinding?.imageAdadharBackside?.setOnClickListener {
                ShowFullScreenImageDialog.showFullScreenDialog(requireContext(), loginResponse?.userDetail?.aadhar_path + loginResponse?.userDetail?.photo2)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return fragmentProfileBinding?.root
    }


}