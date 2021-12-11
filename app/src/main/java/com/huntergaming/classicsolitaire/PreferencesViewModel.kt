package com.huntergaming.classicsolitaire

import androidx.lifecycle.ViewModel
import com.huntergaming.gamedata.preferences.DataConsentPreferences
import com.huntergaming.gamedata.preferences.FirebasePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val firebasePreferences: FirebasePreferences,
    private val dataConsentPreferences: DataConsentPreferences
) : ViewModel() {

    fun shownDataConsent() = dataConsentPreferences.shownDataConsent()
    fun setCanUseFirebase(useFirebase: Boolean) = firebasePreferences.setCanUseFirebase(useFirebase)
    fun updateDataConsent() = dataConsentPreferences.updateDataConsent()
}