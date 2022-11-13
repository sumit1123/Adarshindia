package com.example.adarshindia.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityDonateBinding
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.viewmodel.DonateViewModel
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonateActivity : BaseActivity<DonateViewModel, ActivityDonateBinding>(), View.OnClickListener ,
    PaymentResultListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         intilize()
         obersver()
    }

    private fun obersver() {
        mViewModel!!.donateLivedata.observe(this, {
            val intent = Intent(this, LoanActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun intilize() {
        Checkout.preload(applicationContext)
        viewDataBinding.btContinue.setOnClickListener(this)

    }

    override fun getLayout(): Int {
        return R.layout.activity_donate
    }

    override fun getViewModelClass(): Class<DonateViewModel> {
        return DonateViewModel::class.java
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.bt_continue->
            {
                validation()
            }
        }
    }

    private fun validation() {
        if(viewDataBinding.etAmount.text!!.isNotEmpty())
        {
            if(viewDataBinding.etMobile.text!!.isNotEmpty())
            {
                mViewModel!!.getOrderIDFromRajorPay(this , viewDataBinding.etAmount.text.toString() , viewDataBinding.etMobile.text.toString())
            }
            else{
                showErrorSnackBar(getString(R.string.enter_mobile_number))
            }
        }
        else{
            showErrorSnackBar(getString(R.string.enter_amount))
        }
    }

    override fun onPaymentSuccess(paymentID: String?) {
        mViewModel!!.getDonateApi(viewDataBinding.etAmount.text.toString() , paymentID!!)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        showSuccessToast(p1!!)
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }
}