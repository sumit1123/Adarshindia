package com.example.adarshindia.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityKycIdentityProofBinding
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.data.repository.Status
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.utils.Constant
import com.example.adarshindia.utils.PreferenceKey
import com.example.adarshindia.viewmodel.KycViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KycIdentityProofActivity : BaseActivity<KycViewModel, ActivityKycIdentityProofBinding>(),
    View.OnClickListener {

    var capture_type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initlize()
        oberver()
    }

    private fun oberver() {
        image_livedata.observe(this, {
            setPhoto(it)
        })

        mViewModel!!.loanLivedata.observe(this, {
            hideProgressBar()
            handleKycResponse(it)
        })
    }

    private fun setPhoto(uri: Uri) {
        when (capture_type) {
            Constant.img_voter_end -> {
                viewDataBinding.imgVoterEnd.setImageURI(uri)
            }
            Constant.img_voter_front -> {
                viewDataBinding.imgVoterFront.setImageURI(uri)
            }
            Constant.img_adhare_end -> {
                viewDataBinding.imgAdhareEnd.setImageURI(uri)
            }
            Constant.img_adhare_front -> {
                viewDataBinding.imgAdhareFront.setImageURI(uri)
            }
            Constant.img_pancard -> {
                viewDataBinding.imgPancard.setImageURI(uri)
            }
            Constant.img_passbook -> {
                viewDataBinding.imgPassbook.setImageURI(uri)
            }
            Constant.img_personal_photo -> {
                viewDataBinding.imgPersonalPhoto.setImageURI(uri)
            }
        }
    }

    private fun initlize() {
        viewDataBinding.btNext.setOnClickListener(this)
        viewDataBinding.imgAdhareEnd.setOnClickListener(this)
        viewDataBinding.imgAdhareFront.setOnClickListener(this)
        viewDataBinding.imgPancard.setOnClickListener(this)
        viewDataBinding.imgPassbook.setOnClickListener(this)
        viewDataBinding.imgPersonalPhoto.setOnClickListener(this)
        viewDataBinding.imgVoterEnd.setOnClickListener(this)
        viewDataBinding.imgVoterFront.setOnClickListener(this)
    }

    private fun handleKycResponse(it: Resource<VerifyOTPReponse>?) {
        when (it!!.status) {
            Status.SUCCESS -> {
                val intent = Intent(this, KycAddressProofActivity::class.java)
                startActivity(intent)
                showSuccessToast(it.data!!.message)
            }
            else -> {

            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_kyc_identity_proof
    }

    override fun getViewModelClass(): Class<KycViewModel> {
        return KycViewModel::class.java
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.bt_next -> {
                validation()

                val intent = Intent(this, KycAddressProofActivity::class.java)
                startActivity(intent)
            }
            R.id.img_voter_end -> {
                capture_type = Constant.img_voter_end
                if (checkPermission(android.Manifest.permission.CAMERA)) capturePhoto()
            }
            R.id.img_voter_front -> {
                capture_type = Constant.img_voter_front
                if (checkPermission(android.Manifest.permission.CAMERA)) capturePhoto()
            }
            R.id.img_adhare_end -> {
                capture_type = Constant.img_adhare_end
                if (checkPermission(android.Manifest.permission.CAMERA)) capturePhoto()
            }
            R.id.img_adhare_front -> {
                capture_type = Constant.img_adhare_front
                if (checkPermission(android.Manifest.permission.CAMERA)) capturePhoto()
            }
            R.id.img_pancard -> {
                capture_type = Constant.img_pancard
                if (checkPermission(android.Manifest.permission.CAMERA)) capturePhoto()
            }
            R.id.img_passbook -> {
                capture_type = Constant.img_passbook
                if (checkPermission(android.Manifest.permission.CAMERA)) capturePhoto()
            }
            R.id.img_personal_photo -> {
                capture_type = Constant.img_personal_photo
                if (checkPermission(android.Manifest.permission.CAMERA)) capturePhoto()
            }

        }
    }

    private fun validation() {
        if (viewDataBinding.etAdhar.text!!.isNotEmpty()) {
            if (viewDataBinding.etPancard.text!!.isNotEmpty()) {
                if (viewDataBinding.etBankaccount.text!!.isNotEmpty()) {
                    if (viewDataBinding.etIfsc.text!!.isNotEmpty()) {
                        if (viewDataBinding.etBankname.text!!.isNotEmpty()) {
                            if (viewDataBinding.etVoter.text!!.isNotEmpty()) {
                                showProgressBar()
                                CoroutineScope(Dispatchers.IO).launch {
                                    val apiRequest = ApiRequest(
                                        user_id = mViewModel!!.adarshIndiaRepository.getDataStoreContext()
                                            .getString(PreferenceKey.userid.name)!!,
                                        kyc_id = "3",
                                        aadhar_no = viewDataBinding.etAdhar.text.toString(),
                                        pan_no = viewDataBinding.etPancard.text.toString(),
                                        bank_ac_no = viewDataBinding.etBankaccount.text.toString(),
                                        ifsc_code = viewDataBinding.etIfsc.text.toString(),
                                        bank_name = viewDataBinding.etBankname.text.toString(),
                                        voter_id_no = viewDataBinding.etVoter.text.toString(),
                                        aadhar_back_pic = "",
                                        aadhar_front_pic = "",
                                        pan_card_pic = "",
                                        passbook_photo = "",
                                        voter_id_front_pic = "",
                                        voter_id_back_pic = "",
                                        passport_photo = ""
                                    )
                                    mViewModel!!.getIdentityKycApplyApi(apiRequest)
                                }
                            } else {
                                showErrorSnackBar(getString(R.string.entervoter))
                            }
                        } else {
                            showErrorSnackBar(getString(R.string.enter_bankname))
                        }
                    } else {
                        showErrorSnackBar(getString(R.string.enter_ifsc))
                    }
                } else {
                    showErrorSnackBar(getString(R.string.ente_bankaccount))
                }
            } else {
                showErrorSnackBar(getString(R.string.enterpan))
            }
        } else {
            showErrorSnackBar(getString(R.string.enter_adhar))
        }
    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }
}