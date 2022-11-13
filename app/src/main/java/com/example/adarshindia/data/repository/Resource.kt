package com.example.adarshindia.data.repository

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(data: T?): Resource<T> {
            return Resource(Status.ERROR, null, null)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> empty(): Resource<T> {
            return Resource(Status.EMPTY, null, null)
        }

        fun <T> noInternet(): Resource<T> {
            return Resource(Status.NOINTERNET, null, null)
        }
    }
}