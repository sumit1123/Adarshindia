package com.example.adarshindia.model


data class CommonResponse(
    var success : Boolean = false,
    var message : String = "",
    var records : ArrayList<Records> = ArrayList())
{

}
