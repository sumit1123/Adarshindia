package com.example.adarshindia.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityAddressKycBinding
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.data.repository.Status
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.model.Records
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.utils.PreferenceKey
import com.example.adarshindia.utils.Utils
import com.example.adarshindia.viewmodel.KycViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KycAddressProofActivity : BaseActivity<KycViewModel, ActivityAddressKycBinding>(), View.OnClickListener {

    var stateList = ArrayList<Records>()
    var stateID : String = ""
    var cityID : String = ""
    var address_proof_id :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initlize()
        observer()
    }

    private fun initlize() {
        viewDataBinding.btNext.setOnClickListener(this)
        viewDataBinding.imgAddress.setOnClickListener(this)
        setAddressProofData()
        if (Utils.isNetworkConnected(this)) {
            mViewModel!!.getStateApi()
        } else {
            showErrorSnackBar("Check internet connection")
        }

    }

    private fun observer() {
        mViewModel!!.stateLivedata.observe(this, {
            stateList = it.data!!.records
            setStateData(it)
        })
        mViewModel!!.cityLivedata.observe(this, {
            setCityData(it)
        })

        image_livedata.observe(this, {
            viewDataBinding.imgAddress.setImageURI(it as Uri)
        })
        mViewModel!!.loanLivedata.observe(this, {
            hideProgressBar()
            handleKycResponse(it)
        })
    }

    private fun handleKycResponse(it: Resource<VerifyOTPReponse>?) {
        when(it!!.status)
        {
            Status.SUCCESS->
            {
                val intent = Intent(this, DonateActivity::class.java)
                startActivity(intent)
                showSuccessToast(it.data!!.message)
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
        viewDataBinding.state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                stateID = it.data!!.records.get(0).id
                mViewModel!!.getCityApi(stateID)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                stateID = it.data!!.records.get(position).id
                mViewModel!!.getCityApi(stateID)
            }

        }
    }

    private fun setAddressProofData() {
        val addressprooflist = arrayListOf<String>("Bank statement", "Adhar card", "passport")
        val adapter = ArrayAdapter(this, R.layout.spinner_state, addressprooflist)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewDataBinding.addressSpinner.adapter = adapter
        viewDataBinding.addressSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                address_proof_id = addressprooflist.get(0)

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                address_proof_id = addressprooflist.get(position)
            }

        }
    }

    private fun setCityData(it: Resource<CommonResponse>?) {
        val citylist = ArrayList<String>()
        for (city in it!!.data!!.records) {
            citylist.add(city.name)
        }
        val adapter = ArrayAdapter(this, R.layout.spinner_state, citylist)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewDataBinding.city.adapter = adapter
        viewDataBinding.city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                cityID = it.data!!.records.get(0).id
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cityID = it.data!!.records.get(position).id
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_address_kyc
    }

    override fun getViewModelClass(): Class<KycViewModel> {
        return KycViewModel::class.java
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(view: View?) {
        when (view!!.id) {

            R.id.bt_next -> {
                validation()
            }
            R.id.img_address->
            {
                if(checkPermission(android.Manifest.permission.CAMERA)) capturePhoto()
            }

        }
    }

    private fun validation() {
        if(viewDataBinding.etResidentail.text!!.isNotEmpty())
        {
            if(viewDataBinding.etPostalCode.text!!.isNotEmpty())
            {
                showProgressBar()
                CoroutineScope(Dispatchers.IO).launch {
                    val apiRequest = ApiRequest(
                        user_id = mViewModel!!.adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.userid.name)!!,
                        kyc_id = "3",
                        address_type = viewDataBinding.etResidentail.text.toString(),
                        state_id = stateID,
                        city_id = cityID,
                        pin_code = viewDataBinding.etPostalCode.text.toString(),
                        address_proof_type = "passport",
                        address_proof = ""
                    )
                    mViewModel!!.getAddressKycApplyApi(apiRequest)
                }
            }
            else{
                showErrorSnackBar(getString(R.string.enter_postal))
            }
        }
        else{
            showErrorSnackBar(getString(R.string.enter_residentail))
        }
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }
}