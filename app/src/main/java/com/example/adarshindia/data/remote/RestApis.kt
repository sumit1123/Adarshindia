package com.coucouapp.data.remote

import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.utils.ApiUrls
import retrofit2.http.Body
import retrofit2.http.POST

interface RestApis {

    @POST(ApiUrls.STATELIST)
    suspend fun getStateApi(@Body apiRequest: ApiRequest): CommonResponse

    @POST(ApiUrls.REGISTER)
    suspend fun getRegisterApi(@Body apiRequest: ApiRequest): CommonResponse

    @POST(ApiUrls.LOGIN)
    suspend fun getLoginApi(@Body apiRequest: ApiRequest): CommonResponse

    @POST(ApiUrls.VERIFYOTP)
    suspend fun getVerifyApi(@Body apiRequest: ApiRequest): VerifyOTPReponse

    @POST(ApiUrls.APPLYLOAN)
    suspend fun getLoanApplyApi(@Body apiRequest: ApiRequest): CommonResponse

    @POST(ApiUrls.SETPIN)
    suspend fun getPinSetUpApi(@Body apiRequest: ApiRequest): VerifyOTPReponse

    @POST(ApiUrls.VERIFYPIN)
    suspend fun getPinVerifyUpApi(@Body apiRequest: ApiRequest): VerifyOTPReponse

    @POST(ApiUrls.STOREKYC)
    suspend fun getKycApi(@Body apiRequest: ApiRequest): VerifyOTPReponse

    @POST(ApiUrls.CITYAPI)
    suspend fun getCityApi(@Body apiRequest: ApiRequest): CommonResponse

    @POST(ApiUrls.ADDRESSKYC)
    suspend fun getAddressKycApi(@Body apiRequest: ApiRequest): VerifyOTPReponse

    @POST(ApiUrls.IDENTITY)
    suspend fun getIdentityKycApplyApi(@Body apiRequest: ApiRequest): VerifyOTPReponse

    @POST(ApiUrls.CEHCKMPIN)
    suspend fun getCheckMPinApi(@Body apiRequest: ApiRequest): VerifyOTPReponse

    @POST(ApiUrls.RESEND)
    suspend fun getResendApi(@Body apiRequest: ApiRequest): VerifyOTPReponse

    @POST(ApiUrls.DONATE)
    suspend fun getDonateApi(@Body apiRequest: ApiRequest): VerifyOTPReponse

    @POST(ApiUrls.SLIDER)
    suspend fun getSliderApi(@Body apiRequest: ApiRequest): CommonResponse

}