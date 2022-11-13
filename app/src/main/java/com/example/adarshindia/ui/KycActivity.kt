package com.example.adarshindia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.adarshindia.bytebuddy.R
import com.adarshindia.bytebuddy.databinding.ActivityKycBinding
import com.example.adarshindia.data.repository.Resource
import com.example.adarshindia.data.repository.Status
import com.example.adarshindia.model.ApiRequest
import com.example.adarshindia.model.VerifyOTPReponse
import com.example.adarshindia.ui.base.BaseActivity
import com.example.adarshindia.utils.Constant
import com.example.adarshindia.utils.PreferenceKey
import com.example.adarshindia.viewmodel.KycViewModel
import com.example.adarshindia.viewmodel.LoanViewModel
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KycActivity : BaseActivity<KycViewModel, ActivityKycBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initlize()
        observer()
    }



    private fun initlize() {
        viewDataBinding.btNext.setOnClickListener(this)
    }

    private fun observer() {
        mViewModel!!.loanLivedata.observe(this, {
            hideProgressBar()
            handleKycResponse(it)
        })
    }

    private fun handleKycResponse(it: Resource<VerifyOTPReponse>?) {
        when(it!!.status)
        {
            Status.SUCCESS->
            {
                val intent = Intent(this, KycIdentityProofActivity::class.java)
                startActivity(intent)
                showSuccessToast(it.data!!.message)
            }
            else ->{

            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_kyc
    }

    override fun getViewModelClass(): Class<KycViewModel> {
        return KycViewModel::class.java
    }

    override fun onClick(view: View?) {
        when (view!!.id) {

            R.id.bt_next -> {
                validation()
            }

        }
    }

    private fun validation() {
        if (viewDataBinding.etFirstname.text!!.isNotEmpty()) {
            if (viewDataBinding.etLastname.text!!.isNotEmpty()) {
                if (viewDataBinding.etFathername.text!!.isNotEmpty()) {
                    if (viewDataBinding.etFathersurname.text!!.isNotEmpty()) {
                        if (viewDataBinding.etFatheroccupation.text!!.isNotEmpty()) {
                            if (viewDataBinding.etEmail.text!!.isNotEmpty()) {
                                if (viewDataBinding.etPhone.text!!.isNotEmpty()) {
                                    if (viewDataBinding.etDob.text!!.isNotEmpty()) {
                                        if (viewDataBinding.etAlternate.text!!.isNotEmpty()) {
                                            if (viewDataBinding.etGuardian.text!!.isNotEmpty()) {
                                                if (viewDataBinding.etGuarsdiansurname.text!!.isNotEmpty()) {
                                                    if (viewDataBinding.etRelationGuardian.text!!.isNotEmpty()) {
                                                        if (viewDataBinding.etGuardianMobile.text!!.isNotEmpty()) {
                                                            if (viewDataBinding.etGuardianOccupation.text!!.isNotEmpty()) {
                                                                if (viewDataBinding.etMonthlyIncome.text!!.isNotEmpty()) {
                                                                    if (viewDataBinding.etNoOfFamily.text!!.isNotEmpty()) {
                                                                        if (viewDataBinding.etWhoAreinFamily.text!!.isNotEmpty()) {
                                                                            setRequestData()
                                                                        } else {
                                                                            showErrorSnackBar("Enter who are in family")
                                                                        }

                                                                    } else {
                                                                        showErrorSnackBar("Enter no of family members")
                                                                    }
                                                                } else {
                                                                    showErrorSnackBar("Enter monthly occupation")
                                                                }
                                                            } else {
                                                                showErrorSnackBar("Enter guardian occupation")
                                                            }
                                                        } else {
                                                            showErrorSnackBar("Enter guardian number")
                                                        }
                                                    } else {
                                                        showErrorSnackBar("Enter relation with guardian")
                                                    }
                                                } else {
                                                    showErrorSnackBar(getString(R.string.enter_gaudian_sur))
                                                }
                                            } else {
                                                showErrorSnackBar(getString(R.string.enter_gua))
                                            }
                                        } else {
                                            showErrorSnackBar(getString(R.string.enter_alternate))
                                        }
                                    } else {
                                        showErrorSnackBar(getString(R.string.entedob))
                                    }
                                } else {
                                    showErrorSnackBar(getString(R.string.enter_phone))
                                }
                            } else {
                                showErrorSnackBar(getString(R.string.enter_email))
                            }
                        } else {
                            showErrorSnackBar(getString(R.string.father_occ))
                        }
                    } else {
                        showErrorSnackBar(getString(R.string.enter_fathersurname))
                    }
                } else {
                    showErrorSnackBar(getString(R.string.enter_father))
                }
            } else {
                showErrorSnackBar(getString(R.string.enter_lastname))
            }
        } else {
            showErrorSnackBar(getString(R.string.enter_first))
        }

    }

    private fun setRequestData() {
        showProgressBar()
        CoroutineScope(Dispatchers.IO).launch {
            var apiRequest = ApiRequest(
                user_id = mViewModel!!.adarshIndiaRepository.getDataStoreContext().getString(PreferenceKey.userid.name)!!,
                first_name = viewDataBinding.etFirstname.text.toString(),
                last_name = viewDataBinding.etLastname.text.toString(),
                father_first_name = viewDataBinding.etFathername.text.toString(),
                father_last_name = viewDataBinding.etFathersurname.text.toString(),
                father_occupation = viewDataBinding.etFatheroccupation.text.toString(),
                email = viewDataBinding.etEmail.text.toString(),
                mobile_no = viewDataBinding.etPhone.text.toString(),
                dob = viewDataBinding.etDob.text.toString(),
                alternet_mobile_no = viewDataBinding.etAlternate.text.toString(),
                guardian_first_name = viewDataBinding.etGuardian.text.toString(),
                guardian_last_name = viewDataBinding.etGuarsdiansurname.text.toString(),
                guardian_mobile_no = viewDataBinding.etGuardianMobile.text.toString(),
                guardian_relationship = viewDataBinding.etRelationGuardian.text.toString(),
                occupation = viewDataBinding.etGuardianOccupation.text.toString(),
                monthly_income = viewDataBinding.etMonthlyIncome.text.toString(),
                family_members = viewDataBinding.etNoOfFamily.text.toString(),
                who_are_in_family = viewDataBinding.etWhoAreinFamily.text.toString(),
                gender = Constant.FEMALE
            )
            mViewModel!!.getKycApplyApi(apiRequest)
        }

    }

    override fun showProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewDataBinding.constraintProgressbar.constraintProgressbar.visibility = View.GONE
    }
}