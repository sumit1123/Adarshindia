package com.example.adarshindia.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivitySplashBinding
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.utils.PreferenceKey
import com.example.adarshindia.viewmodel.LoginViewModel
import com.example.adarshindia.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            mViewModel!!.adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.userid.name)?.let {
                mViewModel!!.checkMPinApi()
            } ?: kotlin.run {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        observer()

    }

    private fun observer() {
        mViewModel!!.pinLivedata.observe(this ,{
            if(it.data!!.success)
            {
                val intent = Intent(this@SplashActivity, PINActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Handler(Looper.myLooper()!!).postDelayed(Runnable {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)
            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModelClass(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }
}