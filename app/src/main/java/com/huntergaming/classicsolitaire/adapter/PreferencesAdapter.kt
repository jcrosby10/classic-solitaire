package com.huntergaming.classicsolitaire.adapter

import androidx.lifecycle.ViewModel
import com.huntergaming.gamedata.preferences.DataConsentPreferences
import com.huntergaming.gamedata.preferences.FirebasePreferences
import javax.inject.Inject

class PreferencesAdapter @Inject constructor(
    private val firebasePreferences: FirebasePreferences,
    private val dataConsentPreferences: DataConsentPreferences
) : ViewModel() {

    fun shownDataConsent() = dataConsentPreferences.shownDataConsent()
    fun setCanUseFirebase(useFirebase: Boolean) = firebasePreferences.setCanUseFirebase(useFirebase)
    fun updateDataConsent() = dataConsentPreferences.updateDataConsent()
}