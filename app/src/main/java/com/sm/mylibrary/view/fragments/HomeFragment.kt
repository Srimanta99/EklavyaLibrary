package com.sm.mylibrary.view.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.sm.mylibrary.R
import com.sm.mylibrary.databinding.FragmentHomeBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.PermissionHelper
import com.sm.mylibrary.utils.PermissionViewModel
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.utils.Utils
import com.sm.mylibrary.view.ApplyLeaveActivity
import com.sm.mylibrary.view.AttendenceActivity
import com.sm.mylibrary.view.MainActivity
import com.sm.mylibrary.viewmodel.FragmentHomeViewModel
import java.io.File


class HomeFragment : Fragment() {

    private lateinit var imageView: ImageView
    private val PICK_IMAGE_REQUEST = 1
    var fragmentHomeBinding: FragmentHomeBinding? = null
    private val STORAGE_PERMISSION_CODE = 100
    private lateinit var permissionViewModel: PermissionViewModel
    lateinit var fragmentHomeViewModel : FragmentHomeViewModel
    var bannerlist = ArrayList<String>()
    val imageList = ArrayList<SlideModel>()
    var isImageprofile  = false
    var aadharFrontSideClick = false
    var aadharBackSideClick = false

    private var imageUri: Uri? = null
    var baSe64ofProfileImage: String? = null
    var baSe64ofAadharFrontImage: String? = null
    var baSe64ofAadharBackImage: String? = null

    var loginResponse : LoginResponse? = null
    val sheardPreferenceViewModel = SheardPreferenceViewModel()

    private lateinit var progressDialog: ProgressDialog

    val requestDataProfileImageUpload = HashMap<String, String>()

    val requestDataAadharfrontImageUpload = HashMap<String, String>()
    val requestDataAadharbackImageUpload = HashMap<String, String>()



    // Launchers for gallery and camera
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                if (isImageprofile) {
                    fragmentHomeBinding?.imgProfile?.setImageURI(it)
                    baSe64ofProfileImage = fragmentHomeBinding?.imgProfile?.let { it1 ->
                        Utils.imageViewToBase64(it1)
                    }
                    Log.d("BASE64", baSe64ofProfileImage ?: "Conversion failed")
                    requestDataProfileImageUpload["profile"] = Constants.BASE64CONSTANT+ baSe64ofProfileImage.toString()
                    requestDataProfileImageUpload["filetype"] = "profile"
                    requestDataProfileImageUpload["uid"] = loginResponse?.userId.toString()
                    fragmentHomeViewModel.uploadProfileImage(requestDataProfileImageUpload)

                    Log.d("Base64withconst", Constants.BASE64CONSTANT+ baSe64ofProfileImage.toString() ?: "Conversion failed")
                }
                if (aadharFrontSideClick) {
                   fragmentHomeBinding?.tvFrontImagename?.visibility = View.VISIBLE
                    fragmentHomeBinding?.imgAadharFront?.visibility = View.VISIBLE
                    fragmentHomeBinding?.imgAadharFront?.setImageURI(it)
                    baSe64ofAadharFrontImage = fragmentHomeBinding?.imgAadharFront?.let { it1 ->
                        Utils.imageViewToBase64(it1)
                    }
                  // Log.d("Base64", baSe64ofAadharFrontImage ?: "Conversion failed")
                    requestDataAadharfrontImageUpload["aadharfront"] = Constants.BASE64CONSTANT+baSe64ofAadharFrontImage.toString()
                    requestDataAadharfrontImageUpload["filetype"] = "aadhar"
                    requestDataAadharfrontImageUpload["uid"] = loginResponse?.userId.toString()

                    fragmentHomeViewModel.uploadAadharImage(requestDataAadharfrontImageUpload)

                    fragmentHomeBinding?.tvFrontImagename?.text = Utils.getFileNameFromUri(requireContext(),imageUri!!)

                }
                if (aadharBackSideClick) {
                    fragmentHomeBinding?.tvBackImagename?.visibility = View.VISIBLE
                    fragmentHomeBinding?.imgAadharBack?.visibility = View.VISIBLE
                    fragmentHomeBinding?.imgAadharBack?.setImageURI(it)
                    baSe64ofAadharBackImage = fragmentHomeBinding?.imgAadharBack?.let { it1 ->
                        Utils.imageViewToBase64(it1)
                    }
                   // Log.d("Base64", baSe64ofAadharBackImage ?: "Conversion failed")

                    requestDataAadharbackImageUpload["aadharback"] =Constants.BASE64CONSTANT+ baSe64ofAadharBackImage.toString()
                    requestDataAadharbackImageUpload["filetype"] = "aadhar"
                    requestDataAadharbackImageUpload["uid"] = loginResponse?.userId.toString()

                    fragmentHomeViewModel.uploadAadharImage(requestDataAadharbackImageUpload)


                    fragmentHomeBinding?.tvBackImagename?.text = Utils.getFileNameFromUri(requireContext(),imageUri!!)
                }



            }
        }

    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                imageUri?.let {
                    if (isImageprofile) {
                        fragmentHomeBinding?.imgProfile?.setImageURI(it)
                        baSe64ofProfileImage = fragmentHomeBinding?.imgProfile?.let { it1 ->
                            Utils.imageViewToBase64(it1)
                        }
                        requestDataProfileImageUpload["profile"] = Constants.BASE64CONSTANT+baSe64ofProfileImage.toString()
                        requestDataProfileImageUpload["filetype"] = "profile"
                        requestDataProfileImageUpload["uid"] = loginResponse?.userId.toString()

                        //Log.d("uid", loginResponse?.userId.toString() ?: "Conversion failed")


                        fragmentHomeViewModel.uploadProfileImage(requestDataProfileImageUpload)
                    }
                    if (aadharFrontSideClick) {
                        fragmentHomeBinding?.tvFrontImagename?.visibility = View.VISIBLE
                        fragmentHomeBinding?.tvFrontImagename?.text = Utils.getFileNameFromUri(requireContext(),imageUri!!)

                        fragmentHomeBinding?.imgAadharFront?.visibility = View.VISIBLE
                        fragmentHomeBinding?.imgAadharFront?.setImageURI(it)
                        baSe64ofAadharFrontImage = fragmentHomeBinding?.imgAadharFront?.let { it1 ->
                            Utils.imageViewToBase64(it1)
                        }

                        requestDataAadharfrontImageUpload["aadharfront"] = Constants.BASE64CONSTANT+baSe64ofAadharFrontImage.toString()
                        requestDataAadharfrontImageUpload["filetype"] = "aadhar"
                        requestDataAadharfrontImageUpload["uid"] = loginResponse?.userId.toString()

                        fragmentHomeViewModel.uploadAadharImage(requestDataAadharfrontImageUpload)


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
                        fragmentHomeBinding?.tvBackImagename?.visibility = View.VISIBLE
                        fragmentHomeBinding?.imgAadharBack?.visibility = View.VISIBLE
                        fragmentHomeBinding?.imgAadharBack?.setImageURI(it)
                        baSe64ofAadharBackImage = fragmentHomeBinding?.imgAadharBack?.let { it1 ->
                            Utils.imageViewToBase64(it1)
                        }
                       // Log.d("Base64", baSe64ofAadharBackImage ?: "Conversion failed")

                        requestDataAadharbackImageUpload["aadharback"] = Constants.BASE64CONSTANT+baSe64ofAadharBackImage.toString()
                        requestDataAadharbackImageUpload["filetype"] = "aadhar"
                        requestDataAadharbackImageUpload["uid"] = loginResponse?.userId.toString()

                        fragmentHomeViewModel.uploadAadharImage(requestDataAadharbackImageUpload)
                       // Log.d("Base64", baSe64ofAadharBackImage ?: "Conversion failed")


                        fragmentHomeBinding?.tvBackImagename?.text = Utils.getFileNameFromUri(requireContext(),imageUri!!)
                    }
                }
            }
        }

    // Permission launcher
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showImagePickerDialog()
            } else {
                Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionViewModel = ViewModelProvider(this)[PermissionViewModel::class.java]
        fragmentHomeViewModel = ViewModelProvider(this).get(FragmentHomeViewModel::class.java)

        fragmentHomeViewModel.getBannerImage()

        fragmentHomeViewModel.banner.observe(this){

            bannerlist = ArrayList<String>()
            for ( i in 0 until it.size){
                bannerlist.add(it[i].image_path+it[i].photo)
                imageList.add(SlideModel(it[i].image_path+it[i].photo, it[i].url))
            }


           // imageList.add(SlideModel("https://picsum.photos/800/400?random=1", "First Image"))
           // imageList.add(SlideModel("https://picsum.photos/800/400?random=2", "Second Image"))
           // imageList.add(SlideModel("https://picsum.photos/800/400?random=3", "Third Image"))

            // set image list
            fragmentHomeBinding?.imageSlider?.setImageList(imageList, ScaleTypes.CENTER_CROP)
            //signUpBinding.txtSlot.showDropDown()

            fragmentHomeBinding?.imageSlider?.setItemClickListener(object : ItemClickListener {
                override fun doubleClick(position: Int) {

                }

                override fun onItemSelected(position: Int) {

                    val imgUrl = imageList[position].title
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imgUrl))
                    startActivity(intent)

                }
            })

        }



    }

    private fun setValue(loginResponse: LoginResponse?) {
        fragmentHomeBinding?.tvNameUser?.text = loginResponse?.userDetail?.name
        fragmentHomeBinding?.tvDobUser?.text = loginResponse?.userDetail?.dob
        fragmentHomeBinding?.tvSlot?.text = loginResponse?.userDetail?.shiftin
        if (loginResponse?.userDetail?.types!=null && loginResponse?.userDetail?.seat_no!=null) {
            fragmentHomeBinding?.tvSeatNo?.text = loginResponse?.userDetail?.types + " " + loginResponse?.userDetail?.seat_no
        }
        fragmentHomeBinding?.tvStdId?.text = "STD ID:  " + loginResponse?.userDetail?.ecode
        fragmentHomeBinding?.tvValidDate?.text =   loginResponse?.userDetail?.lastDate

        fragmentHomeBinding?.tvLocationStudent?.text = loginResponse?.userDetail?.libraryaddress
        fragmentHomeBinding?.tvEklavyaStudyCenter?.text = loginResponse?.userDetail?.libraryname
        fragmentHomeBinding?.tvStatusValue?.text = loginResponse?.userDetail?.status
        fragmentHomeBinding?.tvWaitingStatus?.text = loginResponse?.message
        fragmentHomeBinding?.tvlocation?.text  = loginResponse?.userDetail?.libraryaddress




    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val granted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true &&
                permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true
        if (granted) {
            // Permission granted
            openGallery()
        } else {
            // Permission denied

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as? MainActivity
        loginResponse = mainActivity?.loginResponse
        setValue(mainActivity?.loginResponse)

        fragmentHomeBinding?.rlApplyLeave?.setOnClickListener {
            val intent = Intent(activity, ApplyLeaveActivity::class.java)
            startActivity(intent)
        }

        fragmentHomeBinding?.rlApplyAttendence?.setOnClickListener {
            val intent = Intent(activity, AttendenceActivity::class.java)
            startActivity(intent)
        }

        fragmentHomeBinding?.imgProfile?.setOnClickListener {
            isImageprofile = true
            aadharFrontSideClick = false
            aadharBackSideClick = false
            checkStoragePermissionAndShowDialog()
        }

        fragmentHomeBinding?.btnAadharFront?.setOnClickListener {
            aadharFrontSideClick = true
            isImageprofile = false
            aadharBackSideClick = false
            checkStoragePermissionAndShowDialog()
        }

        fragmentHomeBinding?.btnAadharBack?.setOnClickListener {
            aadharFrontSideClick = false
            isImageprofile = false
            aadharBackSideClick = true
            checkStoragePermissionAndShowDialog()
        }

        progressDialog = ProgressDialog(activity).apply {
            setMessage("Loading...")
            setCancelable(false)
        }

        fragmentHomeViewModel.loading.observe(activity) { isLoading ->
            if (isLoading) progressDialog.show() else progressDialog.dismiss()
        }
        fragmentHomeViewModel.error.observe(activity) { errorMessage ->
            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
        }
        fragmentHomeViewModel.message.observe(activity) { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }

        fragmentHomeViewModel?.imageUploadResponse?.observe(activity) {
            Log.d("imageUploadResponse", it.toString())
            if (isImageprofile) {
                sheardPreferenceViewModel.saveData(Constants.PROFILE_IMAGE_PATH, loginResponse?.userDetail?.profile_path + it?.image_name)
                (requireActivity() as MainActivity).updateProfileImage(loginResponse?.userDetail?.profile_path + it?.image_name)

            }else if (aadharFrontSideClick)
              sheardPreferenceViewModel.saveData(Constants.AADHAR_FRONT_IMAGE_PATH,loginResponse?.userDetail?.aadhar_path + it?.image_name)

           else if (aadharBackSideClick)
                sheardPreferenceViewModel.saveData(Constants.AADHAR_BACK_IMAGE_PATH,loginResponse?.userDetail?.aadhar_path + it?.image_name)


        }



        if (sheardPreferenceViewModel.loadData(Constants.PROFILE_IMAGE_PATH)!="" ) {
            val profile = sheardPreferenceViewModel.loadData(Constants.PROFILE_IMAGE_PATH)

            Glide.with(this)
                .load(profile)
                .placeholder(R.drawable.placeholder) // optional
                .error(R.drawable.placeholder)       // optional
                .into(fragmentHomeBinding?.imgProfile!!)
        }


        if (sheardPreferenceViewModel.loadData(Constants.AADHAR_FRONT_IMAGE_PATH)!="") {

            val imagePathFront = sheardPreferenceViewModel.loadData(Constants.AADHAR_FRONT_IMAGE_PATH)
            Glide.with(this)
                .load(imagePathFront)
                .placeholder(R.drawable.placeholder) // optional
                .error(R.drawable.placeholder)       // optional
                .into(fragmentHomeBinding?.imgAadharFront!!)
        }

        if (sheardPreferenceViewModel.loadData(Constants.AADHAR_BACK_IMAGE_PATH)!="") {

            val imagePathBack = sheardPreferenceViewModel.loadData(Constants.AADHAR_BACK_IMAGE_PATH)

            Glide.with(this)
                .load(imagePathBack)
                .placeholder(R.drawable.placeholder) // optional
                .error(R.drawable.placeholder)       // optional
                .into(fragmentHomeBinding?.imgAadharBack!!)
        }

        /*fragmentHomeBinding?.btnUploadImage?.setOnClickListener {

            checkPermission()
         //   val hasPermission = activity?.let { permissionViewModel.checkAndRequestExternalStoragePermission(it!!, permissionLauncher) }
          //  if (hasPermission!!) {
            //     openGallery()
           // }
//            if (checkStoragePermission()){
//                openGallery()
//            }else{
//                requestStoragePermission()
//            }
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHomeBinding = null // Avoid memory leaks
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding?.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun checkStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+
            activity?.let {
                ContextCompat.checkSelfPermission(it, android.Manifest.permission.READ_MEDIA_IMAGES)
            } == PackageManager.PERMISSION_GRANTED

        } else {
            // Android 12 and below
            activity?.let {
                ContextCompat.checkSelfPermission(it, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            } == PackageManager.PERMISSION_GRANTED
        }
    }



    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    STORAGE_PERMISSION_CODE
                )
            }
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            }
        }
    }


    private val storagePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.values.all { it }) {
                
            } else {

            }
        }

    private fun checkPermission() {
        if (PermissionHelper.hasStoragePermission(this)) {
            // Already granted
           // Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show()

             openGallery()
        } else {
            PermissionHelper.requestStoragePermission(this, storagePermissionLauncher)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                openGallery()
            }

        }
    }


    @Deprecated("Deprecated in Java") // for old method
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                fragmentHomeBinding?.imgProfile?.setImageURI(selectedImageUri)
            }
        }
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