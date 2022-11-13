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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(val adarshIndiaRepository: AdarshIndiaRepository) : BaseViewModel() {

    var sliderLivedata = MutableLiveData<Resource<CommonResponse>>()

    fun getSliderApi() {
        viewModelScope.launch {
            val apiRequest = ApiRequest()
            apiRequest.user_id = adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.userid.name)!!
            adarshIndiaRepository.getSliderApi(apiRequest).collect {
                sliderLivedata.value = it
            }
        }
    }
}