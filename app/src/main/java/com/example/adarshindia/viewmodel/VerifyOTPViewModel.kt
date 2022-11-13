package com.example.adarshindia.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.adarshindia.data.repository.AdarshIndiaRepository
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.ui.base.BaseViewModel
import com.example.adarshindia.utils.PreferenceKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOTPViewModel @Inject constructor(val adarshIndiaRepository: AdarshIndiaRepository) : BaseViewModel() {

    var otpLivedata = MutableLiveData<Resource<VerifyOTPReponse>>()


    fun getVerifyOTPApi(mobilenumber: String, otp: String, from: String) {
        val apiRequest = ApiRequest()
        apiRequest.mobile_no = mobilenumber
        apiRequest.otp = otp
        apiRequest.from = from
        apiRequest.device_model = "dummy"
        apiRequest.android_token = "dummy"
        apiRequest.device_id = "dummy"
        apiRequest.device_os_version = "dummy"
        viewModelScope.launch {
            adarshIndiaRepository.getVerifyApi(apiRequest).collect {
                setLocal(it)
            }
        }
    }

    fun getResendOTPApi(mobilenumber: String,from: String) {
        val apiRequest = ApiRequest()
        apiRequest.mobile_no = mobilenumber
        apiRequest.from = from
        viewModelScope.launch {
            adarshIndiaRepository.getResendApi(apiRequest).collect {

            }
        }
    }

    private fun setLocal(resource: Resource<VerifyOTPReponse>) {
        viewModelScope.launch {
            if(resource.data!!.success)
            {
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.userid.name , resource.data.records.id)
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.mobile_number.name , resource.data.records.mobile_no)
                otpLivedata.value = resource
            }
            else
            {
                otpLivedata.value = Resource.error(resource.data)
            }

        }
    }
}