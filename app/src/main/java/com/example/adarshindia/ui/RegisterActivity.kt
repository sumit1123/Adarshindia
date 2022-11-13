package com.example.adarshindia.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityRegisterBinding
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.data.repository.Status
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.model.Records
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.utils.Constant
import com.example.adarshindia.utils.Utils
import com.example.adarshindia.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>(),
    View.OnClickListener , AdapterView.OnItemSelectedListener {

    var stateID : String = ""
    var stateList = ArrayList<Records>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initlize()
        observer()
    }

    private fun observer() {
        mViewModel!!.stateListLivedata.observe(this, {
            stateList = it.data!!.records
            setStateData(it)
        })
        mViewModel!!.registerLivedata.observe(this, {
            hideProgressBar()
            handleRegisterResponse(it)
        })
    }

    private fun handleRegisterResponse(it: Resource<CommonResponse>?) {
        when(it!!.status)
        {
            Status.SUCCESS ->
            {
                if(it.data!!.success)
                {
                    val intent = Intent(this , VerifyOTPActivity::class.java)
                    intent.putExtra(Constant.MOBILE_NUMBER , viewDataBinding.etMobileNumber.text.toString())
                    intent.putExtra(Constant.FROM , Constant.REGISTER)
                    startActivity(intent)
                }
                else{
                    showErrorSnackBar(it.data.message)
                }
            }
            Status.ERROR->
            {
                showErrorSnackBar(it.data!!.message)
            }
            else ->{

            }
        }
    }

    private fun setStateData(it: Resource<CommonResponse>?) {
        val stateList = ArrayList<String>()
        for (state in it!!.data!!.records) {
            stateList.add(state.name)
        }
        val adapter = ArrayAdapter(this, R.layout.spinner_state, stateList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewDataBinding.state.adapter = adapter
    }

    private fun initlize() {
        viewDataBinding.state.setOnItemSelectedListener(this)
        viewDataBinding.btSubmit.setOnClickListener(this)
        if (Utils.isNetworkConnected(this)) {
            mViewModel!!.getStateApi()
        } else {
            showErrorSnackBar("Check internet connection")
        }

    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.bt_submit -> {
                validation()
            }
        }
    }

    private fun validation() {
        if (viewDataBinding.etFullName.text!!.isNotEmpty()) {
            if (viewDataBinding.etMobileNumber.text!!.isNotEmpty()) {
                if (viewDataBinding.chTerms.isChecked) {
                    if (Utils.isNetworkConnected(this)) {
                        showProgressBar()
                        mViewModel!!.getResigterApi(viewDataBinding.etFullName.text.toString(),
                            viewDataBinding.etMobileNumber.text.toString() ,stateID)
                    } else {
                        showErrorSnackBar("Check internet connection")
                    }
                } else {
                    showErrorSnackBar(getString(R.string.ch_terms))
                }
            } else {
                showErrorSnackBar(getString(R.string.enter_mobile_number))
            }
        } else {
            showErrorSnackBar(getString(R.string.enter_full_name))
        }

    }

    override fun getLayout(): Int = R.layout.activity_register

    override fun getViewModelClass(): Class<RegisterViewModel> {
        return RegisterViewModel::class.java
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        stateID = stateList.get(position).id
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        stateID = stateList.get(0).id
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }
}