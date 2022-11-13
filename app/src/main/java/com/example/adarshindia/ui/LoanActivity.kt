package com.example.adarshindia.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityLoanBinding
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.viewmodel.LoanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanActivity : BaseActivity<LoanViewModel, ActivityLoanBinding>() , View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intilize()
        observer()

    }

    private fun observer() {
        hideProgressBar()
        mViewModel!!.loanLivedata.observe(this, {
            showSuccessToast(it.data!!.message)
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun intilize() {
        viewDataBinding.imgBack.setOnClickListener(this)
        viewDataBinding.btApply.setOnClickListener(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_loan
    }

    override fun getViewModelClass(): Class<LoanViewModel> {
        return LoanViewModel::class.java
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.img_back ->
            {
                finish()
            }
            R.id.bt_apply ->
            {
                validation()
            }
        }
    }

    private fun validation() {
        if(viewDataBinding.etLoanAmount.text!!.isNotEmpty())
        {
            if(viewDataBinding.etComment.text!!.isNotEmpty())
            {
                showProgressBar()
                mViewModel!!.getLoanApplyApi(viewDataBinding.etLoanAmount.text.toString() ,viewDataBinding.etComment.text.toString() ,viewDataBinding.rdYes.isChecked)
            }
            else{
                showErrorSnackBar(getString(R.string.why_loan))
            }
        }
        else{
            showErrorSnackBar(getString(R.string.enter_loan_amount))
        }
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }
}