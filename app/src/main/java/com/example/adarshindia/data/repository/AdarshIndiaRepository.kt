package com.example.adarshindia.data.repository

import android.content.Context
import com.coucouapp.data.remote.IRestHelper
import com.coucouapp.data.storagehelper.ApiCall
import com.coucouapp.data.storagehelper.RemoteHelper
import com.coucouapp.database.datastore.AdarshIndiaDataStore
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.model.VerifyOTPReponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AdarshIndiaRepository @Inject constructor(@ApplicationContext val context: Context,
                                                private val remoteRepository: RemoteHelper,
                                                private val adarshIndiaDataStore: AdarshIndiaDataStore) : ApiCall {



    fun getDataStoreContext() = adarshIndiaDataStore
    fun getAppContext() = context

    private fun provideLocalRemoteRepository(): IRestHelper {
        return remoteRepository
    }

    suspend fun getStateApi(apiRequest: ApiRequest): Flow<Resource<CommonResponse>> {
        val stateResponse = provideLocalRemoteRepository().getStateApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRegisterApi(apiRequest: ApiRequest): Flow<Resource<CommonResponse>> {
        val stateResponse = provideLocalRemoteRepository().getRegisterApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getLoginApi(apiRequest: ApiRequest): Flow<Resource<CommonResponse>> {
        val stateResponse = provideLocalRemoteRepository().getLoginApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getVerifyApi(apiRequest: ApiRequest): Flow<Resource<VerifyOTPReponse>> {
        val stateResponse = provideLocalRemoteRepository().getVerifyApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getLoanApplyApi(apiRequest: ApiRequest): Flow<Resource<CommonResponse>> {
        val stateResponse = provideLocalRemoteRepository().getLoanApplyApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPinSetUpApi(apiRequest: ApiRequest): Flow<Resource<VerifyOTPReponse>> {
        val stateResponse = provideLocalRemoteRepository().getPinSetUpApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPinVerifyUpApi(apiRequest: ApiRequest): Flow<Resource<VerifyOTPReponse>> {
        val stateResponse = provideLocalRemoteRepository().getPinVerifyUpApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getKycApi(apiRequest: ApiRequest): Flow<Resource<VerifyOTPReponse>> {
        val stateResponse = provideLocalRemoteRepository().getKycApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAddressKycApi(apiRequest: ApiRequest): Flow<Resource<VerifyOTPReponse>> {
        val stateResponse = provideLocalRemoteRepository().getAddressKycApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getIdentityKycApplyApi(apiRequest: ApiRequest): Flow<Resource<VerifyOTPReponse>> {
        val stateResponse = provideLocalRemoteRepository().getIdentityKycApplyApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCityApi(apiRequest: ApiRequest): Flow<Resource<CommonResponse>> {
        val stateResponse = provideLocalRemoteRepository().getCityApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCheckMPinApi(apiRequest: ApiRequest): Flow<Resource<VerifyOTPReponse>> {
        val stateResponse = provideLocalRemoteRepository().getCheckMpinApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getResendApi(apiRequest: ApiRequest): Flow<Resource<VerifyOTPReponse>> {
        val stateResponse = provideLocalRemoteRepository().getResendApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDonateApi(apiRequest: ApiRequest): Flow<Resource<VerifyOTPReponse>> {
        val stateResponse = provideLocalRemoteRepository().getDonateApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getSliderApi(apiRequest: ApiRequest): Flow<Resource<CommonResponse>> {
        val stateResponse = provideLocalRemoteRepository().getSliderApi(apiRequest)
        return flow {
            emit(stateResponse)
        }.flowOn(Dispatchers.IO)
    }
}