package com.example.adarshindia.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKey {
    val userid = stringPreferencesKey("UserId")
    val mobile_number = stringPreferencesKey("Mobile")
    val ISPIN_SET = stringPreferencesKey("ISPIN_SET")
    val is_kyc_applied = stringPreferencesKey("is_kyc_applied")
    val kyc_status = stringPreferencesKey("kyc_status")
    val is_set_mpin = stringPreferencesKey("is_set_mpin")

    val kyc_id = stringPreferencesKey("kyc_id")
    val donation_status = stringPreferencesKey("donation_status")
    val kyc_doc_status = stringPreferencesKey("kyc_doc_status")
    val kyc_address_status = stringPreferencesKey("kyc_address_status")
}