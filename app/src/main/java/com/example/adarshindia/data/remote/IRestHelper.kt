package com.coucouapp.data.remote

import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.model.VerifyOTPReponse

interface IRestHelper {

    suspend fun getStateApi(apiRequest: ApiRequest): Resource<CommonResponse>
    suspend fun getRegisterApi(apiRequest: ApiRequest): Resource<CommonResponse>
    suspend fun getLoginApi(apiRequest: ApiRequest): Resource<CommonResponse>
    suspend fun getVerifyApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse>
    suspend fun getLoanApplyApi(apiRequest: ApiRequest): Resource<CommonResponse>
    suspend fun getPinSetUpApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse>
    suspend fun getPinVerifyUpApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse>
    suspend fun getKycApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse>
    suspend fun getAddressKycApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse>
    suspend fun getCityApi(apiRequest: ApiRequest): Resource<CommonResponse>
    suspend fun getCheckMpinApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse>
    suspend fun getResendApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse>
    suspend fun getDonateApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse>
    suspend fun getIdentityKycApplyApi(apiRequest: ApiRequest): Resource<VerifyOTPReponse>
    suspend fun getSliderApi(apiRequest: ApiRequest): Resource<CommonResponse>

}