package com.example.adarshindia.model

data class VerifyOTPReponse(
    val message: String,
    val records: Records,
    val success: Boolean,
    val token: String
)