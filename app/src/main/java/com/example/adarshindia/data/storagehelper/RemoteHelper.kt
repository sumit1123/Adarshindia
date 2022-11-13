package com.coucouapp.data.storagehelper

import com.coucouapp.data.remote.IRestHelper
import com.coucouapp.data.remote.RestApis
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.model.VerifyOTPReponse
import javax.inject.Inject

class RemoteHelper @Inject constructor(private val restApi: RestApis) : IRestHelper, ApiCall {

    override suspend fun getStateApi(apiRequest: ApiRequest): Resource<CommonResponse> {
        return apiCall { (restApi.getStateApi(apiRequest)) }
    }

    override suspend fun getRegisterApi(apiRequest: ApiRequest): Resource<CommonResponse> {
        return apiCall { (restApi.getRegisterApi(apiRequest)) }
    }

    override suspend fun getLoginApi(apiRequest: ApiRequest): Resource<CommonResponse> {
        return apiCall { (restApi.getLoginApi(apiRequest)) }
    }

    override suspend fun getVerifyApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse> {
        return apiCall { (restApi.getVerifyApi(apiRequest)) }
    }

    override suspend fun getLoanApplyApi(apiRequest: ApiRequest): Resource<CommonResponse> {
        return apiCall { (restApi.getLoanApplyApi(apiRequest)) }
    }
    override suspend fun getPinSetUpApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse> {
        return apiCall { (restApi.getPinSetUpApi(apiRequest)) }
    }

    override suspend fun getPinVerifyUpApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse> {
        return apiCall { (restApi.getPinVerifyUpApi(apiRequest)) }
    }

    override suspend fun getKycApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse> {
        return apiCall { (restApi.getKycApi(apiRequest)) }
    }

    override suspend fun getCityApi(apiRequest: ApiRequest): Resource<CommonResponse> {
        return apiCall { (restApi.getCityApi(apiRequest)) }
    }

    override suspend fun getAddressKycApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse> {
        return apiCall { (restApi.getAddressKycApi(apiRequest)) }
    }

    override suspend fun getCheckMpinApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse> {
        return apiCall { (restApi.getCheckMPinApi(apiRequest)) }
    }

    override suspend fun getResendApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse> {
        return apiCall { (restApi.getResendApi(apiRequest)) }
    }

    override suspend fun getIdentityKycApplyApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse> {
        return apiCall { (restApi.getIdentityKycApplyApi(apiRequest)) }
    }

    override suspend fun getDonateApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse> {
        return apiCall { (restApi.getDonateApi(apiRequest)) }
    }

    override suspend fun getSliderApi(apiRequest: ApiRequest): Resource<CommonResponse> {
        return apiCall { (restApi.getSliderApi(apiRequest)) }
    }
}