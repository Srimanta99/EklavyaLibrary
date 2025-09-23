package com.sm.mylibrary.view.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sm.mylibrary.adapter.ManageLeaveAdapter
import com.sm.mylibrary.databinding.FragmentManageLeaveBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.model.manageleave.LeaveData
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.viewmodel.FragmentMangeLeaveViewModel


class ManageLeaveFragment : Fragment() {

   var  fragmentManageLeaveBinding: FragmentManageLeaveBinding? = null
    var loginResponse : LoginResponse? = null
    private lateinit var progressDialog: ProgressDialog
    val requestData = HashMap<String, String>()
    val sheardPreferenceViewModel = SheardPreferenceViewModel()
    lateinit var frgmentMangeLeaveViewModel: FragmentMangeLeaveViewModel
    var leavedate = ArrayList<LeaveData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentManageLeaveBinding = FragmentManageLeaveBinding.inflate(inflater, container, false)
        return fragmentManageLeaveBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginData = sheardPreferenceViewModel.loadData(Constants.LOGIN_RESPONSE)
        loginResponse = Gson().fromJson(loginData, LoginResponse::class.java)
        frgmentMangeLeaveViewModel = ViewModelProvider(this).get(FragmentMangeLeaveViewModel::class.java)

        requestData["uid"] = loginResponse?.userId.toString()
        frgmentMangeLeaveViewModel.getLeaveData(requestData)


        frgmentMangeLeaveViewModel.leaveData.observe(viewLifecycleOwner) {
            Log.d("leaveData", it.toString())
            if (it != null) {
                if (it.leave.size>0){
                    leavedate =  it.leave
                    fragmentManageLeaveBinding?.rvLeaves?.layoutManager = LinearLayoutManager(requireActivity())
                    fragmentManageLeaveBinding?.rvLeaves?.adapter = ManageLeaveAdapter(leavedate)
                    fragmentManageLeaveBinding?.tvLeave?.visibility = View.VISIBLE
                    fragmentManageLeaveBinding?.rvLeaves?.visibility = View.VISIBLE

                }else {
                   // fragmentManageLeaveBinding?.rvLeaves?.visibility = View.GONE
                    fragmentManageLeaveBinding?.tvNodata?.visibility = View.VISIBLE
                }
            }else{
                fragmentManageLeaveBinding?.tvNodata?.visibility = View.VISIBLE
               // fragmentManageLeaveBinding?.rvLeaves?.visibility = View.GONE

            }
        }

        frgmentMangeLeaveViewModel.loading.observe(viewLifecycleOwner) { isLoading ->

            if (isLoading) progressDialog.show() else progressDialog.dismiss()
        }

    }




}