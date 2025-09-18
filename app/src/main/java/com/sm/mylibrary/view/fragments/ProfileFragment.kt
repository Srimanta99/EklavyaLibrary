package com.sm.mylibrary.view.fragments


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.Gson
import com.sm.mylibrary.R
import com.sm.mylibrary.databinding.FragmentProfileBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.utils.ShowFullScreenImageDialog
import com.sm.mylibrary.viewmodel.FragmentProfileViewModel

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
        if (sheardPreferenceViewModel.loadData(Constants.PROFILE_IMAGE_PATH)!="") {

            val profile = sheardPreferenceViewModel.loadData(Constants.PROFILE_IMAGE_PATH)
            Log.d("profile",profile)
            Glide.with(this)
                .load(profile)
                // .load(loginResponse?.userDetail?.profile_path + loginResponse?.userDetail?.photo)
                .placeholder(R.drawable.user_profile) // optional
                .error(R.drawable.user_profile)       // optional
                .into(fragmentProfileBinding?.profileImage!!)

            fragmentProfileBinding?.profileImage?.setOnClickListener({
                ShowFullScreenImageDialog.showFullScreenDialog(requireContext(), profile)

            })
        }



        if (sheardPreferenceViewModel.loadData(Constants.AADHAR_FRONT_IMAGE_PATH)!="") {
            Log.d("addharimagepath",sheardPreferenceViewModel.loadData(Constants.AADHAR_FRONT_IMAGE_PATH))

            val imagePathFront = sheardPreferenceViewModel.loadData(Constants.AADHAR_FRONT_IMAGE_PATH)

            fragmentProfileBinding?.tvKyc?.visibility = View.VISIBLE
            fragmentProfileBinding?.cardFront?.visibility = View.VISIBLE
            Glide.with(this)
                .load(imagePathFront)
                //.load(loginResponse?.userDetail?.aadhar_path + loginResponse?.userDetail?.photo1)
              //  .placeholder(R.drawable.placeholder) // optional
                .error(R.drawable.placeholder)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        fragmentProfileBinding?.progressBarAadhar?.visibility = View.GONE // hide loader
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        fragmentProfileBinding?.progressBarAadhar?.visibility = View.GONE // hide loader
                        return false
                    }
                })
                    .into(fragmentProfileBinding?.imageAdadharFrontside!!)

            fragmentProfileBinding?.imageAdadharFrontside?.setOnClickListener {
                ShowFullScreenImageDialog.showFullScreenDialog(requireContext(), imagePathFront)
            }


        }

        if (sheardPreferenceViewModel.loadData(Constants.AADHAR_BACK_IMAGE_PATH)!="") {
          //  Log.d("addharimagepath back",sheardPreferenceViewModel.loadData(Constants.AADHAR_BACK_IMAGE_PATH))
            val imagePathBack = sheardPreferenceViewModel.loadData(Constants.AADHAR_BACK_IMAGE_PATH)

            fragmentProfileBinding?.tvKyc?.visibility = View.VISIBLE
            fragmentProfileBinding?.cardBack?.visibility = View.VISIBLE
            Glide.with(this)
                .load(imagePathBack)
                // .load(loginResponse?.userDetail?.aadhar_path + loginResponse?.userDetail?.photo2)
               // .placeholder(R.drawable.placeholder) // optional
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        fragmentProfileBinding?.progressBarAadharBack?.visibility = View.GONE // hide loader
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        fragmentProfileBinding?.progressBarAadharBack?.visibility = View.GONE // hide loader
                        return false
                    }
                })
                .error(R.drawable.placeholder)
                // optional
                .into(fragmentProfileBinding?.imageAdadharBackside!!)
            fragmentProfileBinding?.imageAdadharBackside?.setOnClickListener {
                ShowFullScreenImageDialog.showFullScreenDialog(requireContext(), imagePathBack)
            }
        }
    }

    private fun setvalues() {
        fragmentProfileBinding?.tvUserName?.text = loginResponse?.userDetail?.name
        if (loginResponse?.userDetail?.types!=null && loginResponse?.userDetail?.seat_no!=null) {
            fragmentProfileBinding?.tvSeatNo?.text = "Seat no -" + loginResponse?.userDetail?.types + " " + loginResponse?.userDetail?.seat_no
        }
        fragmentProfileBinding?.tvEmail?.text = loginResponse?.userDetail?.email
        fragmentProfileBinding?.tvPhone?.text = loginResponse?.userDetail?.phone.toString()
        fragmentProfileBinding?.tvEcode?.text = loginResponse?.userDetail?.ecode




    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return fragmentProfileBinding?.root
    }


}