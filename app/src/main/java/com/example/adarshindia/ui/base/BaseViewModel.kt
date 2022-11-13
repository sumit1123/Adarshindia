package com.example.adarshindia.ui.base

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel()
{

    fun showToast(context : Context, msg: String) {
        Toast.makeText(context, msg , Toast.LENGTH_LONG).show()
    }

}