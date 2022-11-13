package com.example.adarshindia.viewmodel

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.adarshindia.data.repository.AdarshIndiaRepository
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.ui.base.BaseViewModel
import com.example.adarshindia.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val adarshIndiaRepository: AdarshIndiaRepository) : BaseViewModel() {

    var loginLivedata = MutableLiveData<Resource<CommonResponse>>()


    fun getLoginApi(mobilenumber: String) {
        val apiRequest = ApiRequest()
        apiRequest.mobile_no = mobilenumber
        apiRequest.device_model = "dummy"
        apiRequest.android_token = "dummy"
        apiRequest.device_id = "dummy"
        apiRequest.device_os_version = "dummy"
        viewModelScope.launch {
            adarshIndiaRepository.getLoginApi(apiRequest).collect {
                loginLivedata.value = it
            }
        }
    }
}