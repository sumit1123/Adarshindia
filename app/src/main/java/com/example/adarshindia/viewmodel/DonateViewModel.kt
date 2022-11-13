package com.example.adarshindia.viewmodel

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.adarshindia.data.repository.AdarshIndiaRepository
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.CommonResponse
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.ui.base.BaseViewModel
import com.example.adarshindia.utils.Constant
import com.example.adarshindia.utils.PreferenceKey
import com.razorpay.Checkout
import com.razorpay.Order
import com.razorpay.RazorpayClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject constructor(val adarshIndiaRepository: AdarshIndiaRepository) :
    BaseViewModel() {

    var loanLivedata = MutableLiveData<Resource<CommonResponse>>()
    var donateLivedata = MutableLiveData<Resource<VerifyOTPReponse>>()


    fun getOrderIDFromRajorPay(context : Activity, amount: String, mobile: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val razorpay = RazorpayClient(Constant.RAZORPAY_APPID, Constant.RAZOEPAY_SECRETKEY)
            val orderRequest = JSONObject()
            orderRequest.put("amount", amount.toDouble()*100)
            orderRequest.put("currency", "INR")
            orderRequest.put("receipt", "receipt#1")
            orderRequest.put("payment_capture", "1")
            val notes = JSONObject()
            notes.put("notes_key_1", "")
            notes.put("notes_key_1", "")
            orderRequest.put("notes", notes)
            val order = razorpay.orders.create(orderRequest)
            startPayment(context ,order!!.get("id") , amount, mobile)
        }
    }

    fun startPayment(context : Activity , order_id: String?, amount: String, mobilenumber: String) {
        val co = Checkout()
        co.setKeyID(Constant.RAZORPAY_APPID)
        try {
            val options = JSONObject()
            options.put("name", "Adarshindia")
            options.put("description", "Donate")
            options.put("order_id", order_id)
            options.put("image", "")
            options.put("currency", "INR")
            options.put("amount", amount)
            val preFill = JSONObject()
            preFill.put("email", "a@gmail.com")
            preFill.put("contact", mobilenumber)
            options.put("prefill", preFill)
            co.open(context, options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


        fun getDonateApi(loanamount: String, payment: String) {
            viewModelScope.launch {
                val apiRequest = ApiRequest()
                apiRequest.user_id = adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.userid.name)!!
                apiRequest.amount = loanamount
                apiRequest.payment_id = payment
                apiRequest.transaction_date = System.currentTimeMillis().toString()
                apiRequest.status = "success"
                adarshIndiaRepository.getDonateApi(apiRequest).collect {
                    donateLivedata.value = it
                }
            }
        }
    }