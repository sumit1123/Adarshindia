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
class RegisterViewModel @Inject constructor(val adarshIndiaRepository: AdarshIndiaRepository) : BaseViewModel() {

    var stateListLivedata = MutableLiveData<Resource<CommonResponse>>()
    var registerLivedata = MutableLiveData<Resource<CommonResponse>>()


    fun getStateApi() {
            val apiRequest = ApiRequest()
            viewModelScope.launch {
                adarshIndiaRepository.getStateApi(apiRequest).collect {
                   if(it.data!!.success)
                   {
                       stateListLivedata.value = it
                   }
                    else{
                       showToast(adarshIndiaRepository.getAppContext(), it.data.message)
                    }
                }
            }
    }


    fun getResigterApi(fullname: String, mobilenumber: String, stateID: String) {
        val apiRequest = ApiRequest()
        apiRequest.full_name = fullname
        apiRequest.mobile_no = mobilenumber
        apiRequest.state_id = stateID
        apiRequest.gender =Constant.FEMALE
        apiRequest.device_model = "dummy"
        apiRequest.android_token = "dummy"
        apiRequest.device_id = "dummy"
        apiRequest.device_os_version = "dummy"
        viewModelScope.launch {
            adarshIndiaRepository.getRegisterApi(apiRequest).collect {
                registerLivedata.value = it
            }
        }
    }
}