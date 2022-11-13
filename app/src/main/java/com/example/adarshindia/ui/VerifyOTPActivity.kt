package com.example.adarshindia.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityVerifyOtpactivityBinding
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.data.repository.Status
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.utils.Constant
import com.example.adarshindia.utils.GenericTextWatcher
import com.example.adarshindia.viewmodel.VerifyOTPViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class VerifyOTPActivity : BaseActivity<VerifyOTPViewModel, ActivityVerifyOtpactivityBinding>(), View.OnClickListener {

    var mobile_number : String = ""
    var from : String = ""
    lateinit var countDownTimer : CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initlize()
        observer()
    }

    private fun initlize() {
        mobile_number = intent.getStringExtra(Constant.MOBILE_NUMBER)!!
        from = intent.getStringExtra(Constant.FROM)!!

        viewDataBinding.etOtp1.addTextChangedListener(GenericTextWatcher(this ,viewDataBinding.etOtp1 , viewDataBinding.etOtp2))
        viewDataBinding.etOtp2.addTextChangedListener(GenericTextWatcher(this ,viewDataBinding.etOtp2 , viewDataBinding.etOtp3))
        viewDataBinding.etOtp3.addTextChangedListener(GenericTextWatcher(this ,viewDataBinding.etOtp3 , viewDataBinding.etOtp4))
        viewDataBinding.etOtp4.addTextChangedListener(GenericTextWatcher(this ,viewDataBinding.etOtp4 , viewDataBinding.etOtp4))

        viewDataBinding.resend.setOnClickListener(this)
        viewDataBinding.btVerify.setOnClickListener(this)
    }

    private fun observer() {
        mViewModel!!.otpLivedata.observe(this, {
            hideProgressBar()
            handleOTPResponse(it)
        })

    }

    private fun handleOTPResponse(it: Resource<VerifyOTPReponse>?) {
        when(it!!.status)
        {
            Status.SUCCESS ->
            {
                if(from.equals(Constant.REGISTER))
                {
                    val intent = Intent(this , PINActivity::class.java)
                    startActivity(intent)
                }
                else if(from.equals(Constant.LOGIN))
                {
                    val intent = Intent(this , DashBoardActivity::class.java)
                    startActivity(intent)
                }
                showErrorSnackBar(it.data!!.message)
            }
            Status.ERROR->
            {
                showErrorSnackBar("Wrong pin")
            }
            else ->{

            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_verify_otpactivity
    }

    override fun getViewModelClass(): Class<VerifyOTPViewModel> {
        return VerifyOTPViewModel::class.java
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.bt_verify ->
            {
                validation()
            }
            R.id.resend ->
            {
                mViewModel!!.getResendOTPApi(mobile_number, from)
                countDown()
            }
        }
    }

    private fun countDown() {
        viewDataBinding.timer.visibility = View.VISIBLE
        countDownTimer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewDataBinding.timer.text = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
            }

            override fun onFinish() {
                viewDataBinding.timer.visibility = View.GONE
            }
        }
        countDownTimer.start()
    }

    private fun validation() {
        if(viewDataBinding.etOtp1.text!!.isNotEmpty() && viewDataBinding.etOtp2.text!!.isNotEmpty() && viewDataBinding.etOtp3.text!!.isNotEmpty()
            && viewDataBinding.etOtp4.text!!.isNotEmpty())
        {
            showProgressBar()
            mViewModel!!.getVerifyOTPApi(mobile_number,viewDataBinding.etOtp1.text.toString() + viewDataBinding.etOtp2.text+
                    viewDataBinding.etOtp3.text+viewDataBinding.etOtp4.text ,from)
        }
        else
        {
            showErrorSnackBar(getString(R.string.enter_otp))
        }
    }

    override fun onBackPressed() {
        countDownTimer.cancel()
        super.onBackPressed()
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }
}