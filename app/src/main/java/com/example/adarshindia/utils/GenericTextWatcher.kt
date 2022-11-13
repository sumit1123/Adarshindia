package com.example.adarshindia.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.adarshindia.bytebuddy.R


class GenericTextWatcher internal constructor(
    private val context: Context,
    private val view : View,
    private val editext : EditText
) : TextWatcher {
    override fun afterTextChanged(editable: Editable) {
         when(view.id)
         {
             R.id.et_otp1 ->
             {
                 editext.requestFocus()
             }
             R.id.et_otp2 ->
             {
                 editext.requestFocus()
             }
             R.id.et_otp3 ->
             {
                 editext.requestFocus()
             }
             R.id.et_otp4 ->
             {
                 editext.requestFocus()
             }
         }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) { // TODO Auto-generated method stub
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) { // TODO Auto-generated method stub
    }
}
