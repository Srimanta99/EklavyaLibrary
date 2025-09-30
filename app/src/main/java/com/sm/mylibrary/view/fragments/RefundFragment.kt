package com.sm.mylibrary.view.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.sm.mylibrary.databinding.FragmentRefundBinding
import com.sm.mylibrary.model.login.LoginResponse
import com.sm.mylibrary.model.refund.FeesDetails
import com.sm.mylibrary.model.refund.RefundDetails
import com.sm.mylibrary.utils.Constants
import com.sm.mylibrary.utils.SheardPreferenceViewModel
import com.sm.mylibrary.utils.Utils
import com.sm.mylibrary.viewmodel.FragmentRefundViewModel

class RefundFragment : Fragment() {

    var fragmentRefundBinding : FragmentRefundBinding? = null
    val sheardPreferenceViewModel = SheardPreferenceViewModel()
    var loginResponse : LoginResponse? = null
    lateinit var fragmentRefundViewModel: FragmentRefundViewModel

    private lateinit var progressDialog: ProgressDialog
    val requestData = HashMap<String, String>()


    var fees_detail=  ArrayList<FeesDetails>()
    var refund_detail =  ArrayList<RefundDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)




    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        fragmentRefundBinding = FragmentRefundBinding.inflate(inflater, container, false)
        return fragmentRefundBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginData = sheardPreferenceViewModel.loadData(Constants.LOGIN_RESPONSE)
        loginResponse = Gson().fromJson(loginData, LoginResponse::class.java)
        fragmentRefundViewModel = ViewModelProvider(this).get(FragmentRefundViewModel::class.java)
        requestData["eid"] = loginResponse?.userDetail?.id.toString()
       // setValue(loginResponse)
         fragmentRefundViewModel.reFunds(requestData)

         fragmentRefundViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
             if (isLoading) progressDialog.show() else progressDialog.dismiss()
         }

         fragmentRefundViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
             if (errorMessage.isNotEmpty()) {
                 Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
             }
         }

         fragmentRefundViewModel.refundResult.observe(viewLifecycleOwner) { refundResponse ->
             if (refundResponse != null) {
                 Toast.makeText(requireContext(), refundResponse.message, Toast.LENGTH_SHORT).show()
                 refund_detail =  refundResponse.refund_detail
                 fees_detail = refundResponse.fees_detail
             }

             if (refund_detail.size >0) {
                 fragmentRefundBinding?.llrefund?.visibility = View.VISIBLE
                 fragmentRefundBinding?.tvnoDetails?.visibility = View.GONE
             }

             if (fees_detail.size >0) {
                 fragmentRefundBinding?.tvnoDetails?.visibility = View.GONE
                 fragmentRefundBinding?.llrefund?.visibility = View.VISIBLE
             }


         }

    }

    private fun setValue(loginResponse: LoginResponse?) {
        if (loginResponse?.userDetail?.refid!=null) {
            fragmentRefundBinding?.llrefund?.visibility = View.VISIBLE
            fragmentRefundBinding?.tvnoDetails?.visibility = View.GONE
            //fragmentRefundBinding?.tvSNo?.text = loginResponse?.userDetail?.refid.toString()
            fragmentRefundBinding?.tvRefunds?.text = loginResponse?.userDetail?.exp_type
            fragmentRefundBinding?.tvAmount?.text = loginResponse?.userDetail?.amount
            fragmentRefundBinding?.tvDetails?.text = loginResponse?.userDetail?.detail
           // fragmentRefundBinding?.tvPaidby?.text = loginResponse?.userDetail?.paid_by
           // fragmentRefundBinding?.tvStatus?.text = loginResponse?.userDetail?.refstatus
            fragmentRefundBinding?.tvAddedOn?.text = Utils.changeDateFormatwithtime(loginResponse?.userDetail?.ref_date)
        }else {
            fragmentRefundBinding?.tvnoDetails?.visibility = View.VISIBLE
            fragmentRefundBinding?.llrefund?.visibility = View.GONE
        }



    }


}