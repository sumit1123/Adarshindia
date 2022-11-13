package com.example.adarshindia.ui

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityDashBoardBinding
import com.example.adarshindia.ui.adapter.ImageSlider
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.utils.PreferenceKey
import com.example.adarshindia.viewmodel.DashBoardViewModel
import com.example.adarshindia.viewmodel.VerifyOTPViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@AndroidEntryPoint
class DashBoardActivity : BaseActivity<DashBoardViewModel, ActivityDashBoardBinding>(), View.OnClickListener ,
    NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initlize()
        observer()
    }

    private fun observer() {
        mViewModel!!.sliderLivedata.observe(this, {
            viewDataBinding.viewpager.adapter = ImageSlider(this, it.data!!.records)
            viewDataBinding.indicator.setViewPager(viewDataBinding.viewpager)
        })
    }

    private fun initlize() {
        mViewModel!!.getSliderApi()
        viewDataBinding.homeIcon.setOnClickListener(this)
        viewDataBinding.imgLoan.setOnClickListener(this)
        viewDataBinding.btKyc.setOnClickListener(this)
        viewDataBinding.btDonate.setOnClickListener(this)
        viewDataBinding.navview.setNavigationItemSelectedListener(this)

    }

    override fun getLayout(): Int {
        return R.layout.activity_dash_board
    }

    override fun getViewModelClass(): Class<DashBoardViewModel> {
        return DashBoardViewModel::class.java
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.home_icon ->
            {
                if( viewDataBinding.myDrawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    viewDataBinding.myDrawerLayout.openDrawer(GravityCompat.END)
                }
                else{
                    viewDataBinding.myDrawerLayout.openDrawer(GravityCompat.START)
                }

            }
            R.id.img_loan ->
            {
                CoroutineScope(Dispatchers.IO).launch {
                    if(mViewModel!!.adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.kyc_id.name).equals("0"))
                    {
                        val intent = Intent(this@DashBoardActivity , KycActivity::class.java)
                        startActivity(intent)
                    }
                    else if(mViewModel!!.adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.donation_status.name).equals("success")){
                        val intent = Intent(this@DashBoardActivity , LoanActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        val intent = Intent(this@DashBoardActivity , DonateActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
            R.id.bt_kyc->
            {
                val intent = Intent(this , KycActivity::class.java)
                startActivity(intent)
            }
            R.id.bt_donate->
            {
                val intent = Intent(this , DonateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return false
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}