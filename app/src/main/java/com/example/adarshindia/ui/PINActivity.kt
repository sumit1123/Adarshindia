package com.example.adarshindia.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityPinactivityBinding
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.data.repository.Status
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.utils.Constant
import com.example.adarshindia.utils.GenericTextWatcher
import com.example.adarshindia.utils.PreferenceKey
import com.example.adarshindia.viewmodel.PinViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PINActivity : BaseActivity<PinViewModel, ActivityPinactivityBinding>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intilize()
        observer()
    }

    private fun intilize() {
        viewDataBinding.btNext.setOnClickListener(this)
        viewDataBinding.tvForgotpin.setOnClickListener(this)
        viewDataBinding.etOtp1.addTextChangedListener(
            GenericTextWatcher(
                this,
                viewDataBinding.etOtp1,
                viewDataBinding.etOtp2
            )
        )
        viewDataBinding.etOtp2.addTextChangedListener(
            GenericTextWatcher(
                this,
                viewDataBinding.etOtp2,
                viewDataBinding.etOtp3
            )
        )
        viewDataBinding.etOtp3.addTextChangedListener(
            GenericTextWatcher(
                this,
                viewDataBinding.etOtp3,
                viewDataBinding.etOtp4
            )
        )
        viewDataBinding.etOtp4.addTextChangedListener(
            GenericTextWatcher(
                this,
                viewDataBinding.etOtp4,
                viewDataBinding.etOtp4
            )
        )

    }

    private fun observer() {
        mViewModel!!.pinLivedata.observe(this, {
            hideProgressBar()
            handleOTPResponse(it)
        })
    }

    private fun handleOTPResponse(it: Resource<VerifyOTPReponse>?) {
        when (it!!.status) {
            Status.SUCCESS -> {
                if (it.data!!.success) {
                    val intent = Intent(this, DashBoardActivity::class.java)
                    startActivity(intent)
                } else {
                    showErrorSnackBar(it.data.message)
                }
            }
            Status.ERROR -> {
                showErrorSnackBar(it.data!!.message)
            }
            else -> {

            }
        }
    }


    override fun getLayout(): Int {
        return R.layout.activity_pinactivity
    }

    override fun getViewModelClass(): Class<PinViewModel> {
        return PinViewModel::class.java
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.bt_next -> {
                verifyPin()
            }
            R.id.tv_forgotpin->
            {
               viewDataBinding.etOtp1.text!!.clear()
                viewDataBinding.etOtp2.text!!.clear()
                viewDataBinding.etOtp3.text!!.clear()
                viewDataBinding.etOtp4.text!!.clear()
                CoroutineScope(Dispatchers.IO).launch {
                    mViewModel!!.adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.is_set_mpin.name, "false")
                }
                showSuccessToast(getString(R.string.setpinagain))
            }
        }
    }

    private fun verifyPin() {
        if (viewDataBinding.etOtp1.text!!.isNotEmpty() && viewDataBinding.etOtp2.text!!.isNotEmpty() && viewDataBinding.etOtp3.text!!.isNotEmpty()
            && viewDataBinding.etOtp4.text!!.isNotEmpty()
        ) {
            showProgressBar()
            CoroutineScope(Dispatchers.IO).launch {
                if (mViewModel!!.adarshIndiaRepository.getDataStoreContext()
                        .getString(PreferenceKey.is_set_mpin.name).equals("false")
                ) {
                    mViewModel!!.getPinSetUpApi(
                        viewDataBinding.etOtp1.text.toString() + viewDataBinding.etOtp2.text.toString()
                                + viewDataBinding.etOtp3.text.toString() + viewDataBinding.etOtp4.text.toString()
                    )
                } else {
                    mViewModel!!.getPinVerifyUpApi(
                        viewDataBinding.etOtp1.text.toString() + viewDataBinding.etOtp2.text.toString()
                                + viewDataBinding.etOtp3.text.toString() + viewDataBinding.etOtp4.text.toString()
                    )
                }
            }

        } else {
            showErrorSnackBar(getString(R.string.enter_pin))
        }
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }
}