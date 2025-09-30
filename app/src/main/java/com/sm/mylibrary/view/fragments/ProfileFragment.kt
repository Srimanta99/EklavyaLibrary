package com.sm.mylibrary.view.fragments


import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.canhub.cropper.CropImageContractOptions
import com.google.gson.Gson
import com.sm.mylibrary.R
import com.sm.mylibrary.databinding.FragmentProfileBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.ImageCompress
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.utils.ShowFullScreenImageDialog
import com.sm.mylibrary.utils.Utils
import com.sm.mylibrary.view.MainActivity
import com.sm.mylibrary.viewmodel.FragmentProfileViewModel
import java.io.File

class ProfileFragment : Fragment() {


    val sheardPreferenceViewModel = SheardPreferenceViewModel()
    var loginResponse : LoginResponse? = null
    var fragmentProfileBinding : FragmentProfileBinding? = null
    lateinit var fragmentProfileViewModel : FragmentProfileViewModel

    var isImageprofile  = false
    var aadharFrontSideClick = false
    var aadharBackSideClick = false

    private var imageUri: Uri? = null
    var baSe64ofProfileImage: String? = null

    val requestDataProfileImageUpload = HashMap<String, String>()

    val requestDataAadharfrontImageUpload = HashMap<String, String>()
    val requestDataAadharbackImageUpload = HashMap<String, String>()
    private lateinit var cropImageLauncher: ActivityResultLauncher<CropImageContractOptions>
    private lateinit var progressDialog: ProgressDialog





    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            if (isImageprofile) {
                fragmentProfileBinding?.profileImage?.setImageURI(it)
//                    baSe64ofProfileImage = fragmentHomeBinding?.imgProfile?.let { it1 ->
//                        Utils.imageViewToBase64(it1)
//                    }
//                    Log.d("BASE64", baSe64ofProfileImage ?: "Conversion failed")
                val base64String = ImageCompress.getCompressedBase64FromUri(requireActivity(), imageUri!!)
                if (base64String != null) {
                    requestDataProfileImageUpload["profile"] = Constants.BASE64CONSTANT + base64String.toString()
                    requestDataProfileImageUpload["filetype"] = "profile"
                    requestDataProfileImageUpload["uid"] = loginResponse?.userId.toString()
                    fragmentProfileViewModel.uploadProfileImage(requestDataProfileImageUpload)
                }else{
                    Toast.makeText(activity, "Image compression failed", Toast.LENGTH_SHORT).show()
                }
                Log.d("Base64withconst", Constants.BASE64CONSTANT+ baSe64ofProfileImage.toString() ?: "Conversion failed")
            }
            if (aadharFrontSideClick) {
                fragmentProfileBinding?.tvFrontImagename?.visibility = View.VISIBLE
                fragmentProfileBinding?.cardFront?.visibility = View.VISIBLE
                fragmentProfileBinding?.imageAdadharFrontside?.setImageURI(it)
//                    baSe64ofAadharFrontImage = fragmentHomeBinding?.imgAadharFront?.let { it1 ->
//                        Utils.imageViewToBase64(it1)
//                    }
                // Log.d("Base64", baSe64ofAadharFrontImage ?: "Conversion failed")
                val base64String = ImageCompress.getCompressedBase64FromUri(requireActivity(), imageUri!!)
                if (base64String != null) {
                    requestDataAadharfrontImageUpload["aadharfront"] = Constants.BASE64CONSTANT + base64String.toString()
                    requestDataAadharfrontImageUpload["filetype"] = "aadharfront"
                    requestDataAadharfrontImageUpload["uid"] = loginResponse?.userId.toString()

                    fragmentProfileViewModel.uploadAadharImage(requestDataAadharfrontImageUpload)
                }

                fragmentProfileBinding?.tvFrontImagename?.text = Utils.getFileNameFromUri(requireContext(),imageUri!!)

            }
            if (aadharBackSideClick) {
                fragmentProfileBinding?.tvBackImagename?.visibility = View.VISIBLE
                fragmentProfileBinding?.cardBack?.visibility = View.VISIBLE
                fragmentProfileBinding?.imageAdadharBackside?.setImageURI(it)
//                    baSe64ofAadharBackImage = fragmentHomeBinding?.imgAadharBack?.let { it1 ->
//                        Utils.imageViewToBase64(it1)
//                    }
                // Log.d("Base64", baSe64ofAadharBackImage ?: "Conversion failed")
                val base64String = ImageCompress.getCompressedBase64FromUri(requireActivity(), imageUri!!)
                if (base64String != null) {
                    requestDataAadharbackImageUpload["aadharback"] = Constants.BASE64CONSTANT + base64String.toString()
                    requestDataAadharbackImageUpload["filetype"] = "aadharback"
                    requestDataAadharbackImageUpload["uid"] = loginResponse?.userId.toString()
                    fragmentProfileViewModel.uploadAadharImage(requestDataAadharbackImageUpload)
                }else{
                    Toast.makeText(activity, "Image compression failed", Toast.LENGTH_SHORT).show()
                }
                fragmentProfileBinding?.tvBackImagename?.text = Utils.getFileNameFromUri(requireContext(),imageUri!!)
            }



        }
    }

    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                imageUri?.let {
                    if (isImageprofile) {
                        fragmentProfileBinding?.profileImage?.setImageURI(it)
//                        baSe64ofProfileImage = fragmentHomeBinding?.imgProfile?.let { it1 ->
//                            Utils.imageViewToBase64(it1)
//                        }

                        //Log.d("uid", loginResponse?.userId.toString() ?: "Conversion failed")
                        val base64String = ImageCompress.getCompressedBase64FromUri(requireActivity(), imageUri!!)
                        if (base64String != null) {
                            requestDataProfileImageUpload["profile"] = Constants.BASE64CONSTANT + base64String.toString()
                            requestDataProfileImageUpload["filetype"] = "profile"
                            requestDataProfileImageUpload["uid"] = loginResponse?.userId.toString()

                            fragmentProfileViewModel.uploadProfileImage(requestDataProfileImageUpload)

                        } else {
                            Toast.makeText(activity, "Image compression failed", Toast.LENGTH_SHORT).show()
                        }

                    }
                    if (aadharFrontSideClick) {
                        fragmentProfileBinding?.tvFrontImagename?.visibility = View.VISIBLE
                        fragmentProfileBinding?.tvFrontImagename?.text = Utils.getFileNameFromUri(requireContext(),imageUri!!)

                        fragmentProfileBinding?.cardFront?.visibility = View.VISIBLE
                        fragmentProfileBinding?.imageAdadharFrontside?.setImageURI(it)
//                        baSe64ofAadharFrontImage = fragmentHomeBinding?.imgAadharFront?.let { it1 ->
//                            Utils.imageViewToBase64(it1)
//                        }

                        val base64String = ImageCompress.getCompressedBase64FromUri(requireActivity(), imageUri!!)
                        if (base64String != null) {
                            requestDataAadharfrontImageUpload["aadharfront"] = Constants.BASE64CONSTANT+ base64String.toString()
                            requestDataAadharfrontImageUpload["filetype"] = "aadharfront"
                            requestDataAadharfrontImageUpload["uid"] = loginResponse?.userId.toString()

                            fragmentProfileViewModel.uploadAadharImage(requestDataAadharfrontImageUpload)
                        } else {
                            Toast.makeText(activity, "Image compression failed", Toast.LENGTH_SHORT).show()
                        }

                        //  Log.d("Base64", baSe64ofAadharFrontImage ?: "Conversion failed")


                        /*activity?.lifecycleScope?.launch {
                            // switch to background thread
                            val base64 = withContext(Dispatchers.IO) {
                                Utils.uriToBase64(requireContext(),imageUri!!)
                            }

                            // back on Main thread here
                            Toast.makeText(activity, base64 ?: "Conversion failed", Toast.LENGTH_SHORT).show()
                           // Log.d("Base64", base64 ?: "Conversion failed")
                        }*/
                    }
                    if (aadharBackSideClick) {
                        fragmentProfileBinding?.tvBackImagename?.visibility = View.VISIBLE
                        fragmentProfileBinding?.cardBack?.visibility = View.VISIBLE
                        fragmentProfileBinding?.imageAdadharBackside?.setImageURI(it)
//                        baSe64ofAadharBackImage = fragmentHomeBinding?.imgAadharBack?.let { it1 ->
//                            Utils.imageViewToBase64(it1)
//                        }
                        // Log.d("Base64", baSe64ofAadharBackImage ?: "Conversion failed")
                        // val compressedBase64 = Utils.compressBase64Image(baSe64ofAadharBackImage!!,60,400,400 )
                        val base64String = ImageCompress.getCompressedBase64FromUri(requireActivity(), imageUri!!)
                        if (base64String != null) {
                            requestDataAadharbackImageUpload["aadharback"] = Constants.BASE64CONSTANT + base64String.toString()
                            requestDataAadharbackImageUpload["filetype"] = "aadharback"
                            requestDataAadharbackImageUpload["uid"] = loginResponse?.userId.toString()

                            fragmentProfileViewModel.uploadAadharImage(requestDataAadharbackImageUpload)
                        }else{
                            Toast.makeText(activity, "Image compression failed", Toast.LENGTH_SHORT).show()

                        }
                        // Log.d("Base64", baSe64ofAadharBackImage ?: "Conversion failed")
                        fragmentProfileBinding?.tvBackImagename?.text = Utils.getFileNameFromUri(requireContext(),imageUri!!)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



       // fragmentProfileViewModel = ViewModelProvider(this).get(FragmentProfileViewModel::class.java)


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginData = sheardPreferenceViewModel.loadData(Constants.LOGIN_RESPONSE)
        loginResponse = Gson().fromJson(loginData, LoginResponse::class.java)
        fragmentProfileViewModel = ViewModelProvider(this).get(FragmentProfileViewModel::class.java)

        progressDialog = ProgressDialog(activity).apply {
            setMessage("Loading...")
            setCancelable(false)
        }

        setvalues()
        fragmentProfileBinding?.profileImage?.setOnClickListener {
            isImageprofile = true
            aadharFrontSideClick = false
            aadharBackSideClick = false
            checkStoragePermissionAndShowDialog()
        }

        fragmentProfileBinding?.btnAadharFront?.setOnClickListener {
            aadharFrontSideClick = true
            isImageprofile = false
            aadharBackSideClick = false
            checkStoragePermissionAndShowDialog()
        }

        fragmentProfileBinding?.btnAadharBack?.setOnClickListener {
            aadharFrontSideClick = false
            isImageprofile = false
            aadharBackSideClick = true
            checkStoragePermissionAndShowDialog()
        }


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

            //fragmentProfileBinding?.tvKyc?.visibility = View.VISIBLE
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

           // fragmentProfileBinding?.tvKyc?.visibility = View.VISIBLE
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


        fragmentProfileViewModel.loading.observe(activity) { isLoading ->
            if (isLoading) progressDialog.show() else progressDialog.dismiss()
        }
        fragmentProfileViewModel.error.observe(activity) { errorMessage ->
            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
        }
        fragmentProfileViewModel.message.observe(activity) { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }

        fragmentProfileViewModel?.imageUploadResponse?.observe(activity) {
            Log.d("imageUploadResponse", it.toString())
            if (isImageprofile) {
                sheardPreferenceViewModel.saveData(Constants.PROFILE_IMAGE_PATH, loginResponse?.userDetail?.profile_path + it?.image_name)
                (requireActivity() as MainActivity).updateProfileImage(loginResponse?.userDetail?.profile_path + it?.image_name)

            }else if (aadharFrontSideClick)
                sheardPreferenceViewModel.saveData(Constants.AADHAR_FRONT_IMAGE_PATH,loginResponse?.userDetail?.aadhar_path + it?.image_name)

            else if (aadharBackSideClick)
                sheardPreferenceViewModel.saveData(Constants.AADHAR_BACK_IMAGE_PATH,loginResponse?.userDetail?.aadhar_path + it?.image_name)


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

    private fun checkStoragePermissionAndShowDialog() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE

        when {
            activity?.let { ContextCompat.checkSelfPermission(it, permission) } == PackageManager.PERMISSION_GRANTED -> {
                // already granted
                showImagePickerDialog()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(activity, "Storage permission required", Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(permission)
            }
            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showImagePickerDialog()
            } else {
                Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun showImagePickerDialog() {
        val options = arrayOf("Choose from Gallery", "Take Photo")

        AlertDialog.Builder(activity)
            .setTitle("Select Image")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> pickFromGallery()
                    1 -> takePhoto()
                }
            }.show()
    }

    private fun pickFromGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun takePhoto() {

        val imageFile = File.createTempFile("photo_", ".jpg", requireContext().cacheDir)
        imageUri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().packageName + ".fileprovider",
            imageFile
        )
        takePhotoLauncher.launch(imageUri)
    }


}