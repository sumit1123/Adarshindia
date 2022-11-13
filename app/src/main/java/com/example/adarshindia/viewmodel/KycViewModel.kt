package com.example.adarshindia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.adarshindia.data.repository.AdarshIndiaRepository
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KycViewModel @Inject constructor(val adarshIndiaRepository: AdarshIndiaRepository) : BaseViewModel() {

    var loanLivedata = MutableLiveData<Resource<VerifyOTPReponse>>()
    var stateLivedata = MutableLiveData<Resource<CommonResponse>>()
    var cityLivedata = MutableLiveData<Resource<CommonResponse>>()

    fun getKycApplyApi(apiRequest:ApiRequest) {
        viewModelScope.launch {
            adarshIndiaRepository.getKycApi(apiRequest).collect {
                loanLivedata.value = it
            }
        }
    }

    fun getAddressKycApplyApi(apiRequest:ApiRequest) {
        viewModelScope.launch {
            adarshIndiaRepository.getAddressKycApi(apiRequest).collect {
                loanLivedata.value = it
            }
        }
    }

    fun getIdentityKycApplyApi(apiRequest:ApiRequest) {
        viewModelScope.launch {
            adarshIndiaRepository.getIdentityKycApplyApi(apiRequest).collect {
                loanLivedata.value = it
            }
        }
    }


    fun getStateApi() {
        val apiRequest = ApiRequest()
        viewModelScope.launch {
            adarshIndiaRepository.getStateApi(apiRequest).collect {
                if(it.data!!.success)
                {
                    stateLivedata.value = it
                }
                else{
                    showToast(adarshIndiaRepository.getAppContext(), it.data.message)
                }
            }
        }
    }


    fun getCityApi(stateID: String) {
        val apiRequest = ApiRequest()
        apiRequest.state_id = stateID
        viewModelScope.launch {
            adarshIndiaRepository.getCityApi(apiRequest).collect {
                if(it.data!!.success)
                {
                    cityLivedata.value = it
                }
                else{
                    showToast(adarshIndiaRepository.getAppContext(), it.data.message)
                }
            }
        }
    }
}