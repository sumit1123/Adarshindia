package com.example.adarshindia.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.SpinnerStateBinding
import com.example.adarshindia.model.Records

class StateAdapter(val context : Activity, val statelist : ArrayList<Records>) : BaseAdapter() {

    override fun getCount(): Int {
        return statelist.size
    }

    override fun getItem(position: Int): Records {
        return statelist.get(position)
    }

    override fun getItemId(position: Int): Long {
        return statelist.get(position).id.toLong()
    }

    override fun getView(position: Int, view: View?, p2: ViewGroup?): View {
        val spinnerStateBinding : SpinnerStateBinding = DataBindingUtil.setContentView(context, R.layout.spinner_state)
        spinnerStateBinding.tvState.text = statelist.get(position).name
        return spinnerStateBinding.root
    }
}