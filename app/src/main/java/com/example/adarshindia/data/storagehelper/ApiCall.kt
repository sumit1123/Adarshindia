package com.coucouapp.data.storagehelper

import com.example.adarshindia.data.repository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface ApiCall {
    suspend fun <T> apiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.success(apiCall.invoke())
            }
            catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.success(apiCall.invoke())
                    }
                    else -> {
                        Resource.success(apiCall.invoke())
                    }
                }
            }
        }
    }
}