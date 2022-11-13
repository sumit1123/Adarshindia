package com.example.adarshindia.viewmodel

import android.text.Editable
import androidx.datastore.preferences.preferencesDataStore
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
class SplashViewModel @Inject constructor(val adarshIndiaRepository: AdarshIndiaRepository) :
    BaseViewModel() {

    var pinLivedata = MutableLiveData<Resource<VerifyOTPReponse>>()


    fun checkUserId(): Boolean {
        var isLoggedIn = false
        viewModelScope.launch {
            if (adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.userid.name).equals("")) {
                isLoggedIn = false
            } else {
                isLoggedIn = true
            }
        }
        return isLoggedIn
    }


    fun checkMPinApi() {
        viewModelScope.launch {
            val apiRequest = ApiRequest(
                user_id = adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.userid.name)!!
            )
            adarshIndiaRepository.getCheckMPinApi(apiRequest).collect {
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.is_kyc_applied.name, it.data!!.records.is_kyc_applied.toString())
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.kyc_status.name, it.data.records.kyc_status.toString())
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.is_set_mpin.name, it.data.records.is_set_mpin.toString())
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.kyc_id.name, it.data.records.kyc_id.toString())
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.donation_status.name, it.data.records.donation_status.toString())
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.kyc_doc_status.name, it.data.records.kyc_doc_status.toString())
                adarshIndiaRepository.getDataStoreContext().putString(PreferenceKey.kyc_address_status.name, it.data.records.kyc_address_status.toString())
                pinLivedata.value = it
            }
        }
    }
}