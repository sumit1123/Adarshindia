package com.example.adarshindia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.adarshindia.data.repository.AdarshIndiaRepository
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.ui.base.BaseViewModel
import com.example.adarshindia.utils.PreferenceKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(val adarshIndiaRepository: AdarshIndiaRepository) : BaseViewModel() {

    var loanLivedata = MutableLiveData<Resource<CommonResponse>>()


    fun getLoanApplyApi(loanamount: String, reasonfor: String, runningloan:Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiRequest = ApiRequest()
            apiRequest.user_id = adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.userid.name)!!
            apiRequest.loan_amount = loanamount
            apiRequest.reason_for = reasonfor
            apiRequest.any_running_loan = runningloan.toString()
            viewModelScope.launch {
                adarshIndiaRepository.getLoanApplyApi(apiRequest).collect {
                    loanLivedata.value = it
                }
            }
        }
    }
}