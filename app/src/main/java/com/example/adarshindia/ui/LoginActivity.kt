package com.example.adarshindia.ui

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityLoginBinding
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.data.repository.Status
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.utils.Constant
import com.example.adarshindia.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() , View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intilize()
        observer()

    }

    private fun intilize() {
        viewDataBinding.donthave.setOnClickListener(this)
        viewDataBinding.btLogin.setOnClickListener(this)
    }

    private fun observer() {
        mViewModel!!.loginLivedata.observe(this, {
            hideProgressBar()
            handleLoginResponse(it)
        })

    }

    private fun handleLoginResponse(it: Resource<CommonResponse>?) {
        when(it!!.status)
        {
            Status.SUCCESS ->
            {
                val intent = Intent(this , VerifyOTPActivity::class.java)
                intent.putExtra(Constant.MOBILE_NUMBER , viewDataBinding.etMobile.text.toString())
                intent.putExtra(Constant.FROM , Constant.LOGIN)
                startActivity(intent)
                showErrorSnackBar(it.data!!.message)
            }
            Status.ERROR->
            {
                showErrorSnackBar(it.data!!.message)
            }
            else ->{

            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun getViewModelClass(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.bt_login ->
            {
                validation()
            }
            R.id.donthave->
            {
               val intent = Intent(this, RegisterActivity::class.java)
               startActivity(intent)
            }
        }
    }

    private fun validation() {
        if(viewDataBinding.etMobile.text!!.isNotEmpty())
        {
            showProgressBar()
            mViewModel!!.getLoginApi(viewDataBinding.etMobile.text.toString())
        }
        else
        {
            showErrorSnackBar(getString(R.string.enter_mobile_number))
        }
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility =View.GONE
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}