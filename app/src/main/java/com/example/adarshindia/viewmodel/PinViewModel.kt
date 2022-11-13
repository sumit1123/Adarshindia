package com.example.adarshindia.viewmodel

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.adarshindia.data.repository.AdarshIndiaRepository
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.ui.base.BaseViewModel
import com.example.adarshindia.utils.Constant
import com.example.adarshindia.utils.PreferenceKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(val adarshIndiaRepository: AdarshIndiaRepository) : BaseViewModel() {

    var pinLivedata = MutableLiveData<Resource<VerifyOTPReponse>>()

    fun getPinSetUpApi(mpin: String) {
        viewModelScope.launch {
            val apiRequest = ApiRequest()
            apiRequest.mobile_number = adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.mobile_number.name)!!
            apiRequest.mpin = mpin
            apiRequest.device_model = "dummy"
            apiRequest.android_token = "dummy"
            apiRequest.device_id = "dummy"
            apiRequest.device_os_version = "dummy"
            adarshIndiaRepository.getPinSetUpApi(apiRequest).collect {
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.ISPIN_SET.name , "true")
                pinLivedata.value = it
            }
        }
    }

    fun getPinVerifyUpApi(mpin: String) {
        viewModelScope.launch {
            val apiRequest = ApiRequest()
            apiRequest.user_id = adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.userid.name)!!
            apiRequest.mpin = mpin
            adarshIndiaRepository.getPinVerifyUpApi(apiRequest).collect {
                pinLivedata.value = it
            }
        }
    }
}