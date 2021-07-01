package com.huntergaming.classicsolitaire.ui

import androidx.lifecycle.ViewModel
import com.huntergaming.classicsolitaire.data.AuthenticationState
import com.huntergaming.classicsolitaire.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepo: AuthenticationRepository
): ViewModel() {

    /*
    * ViewModels should convert flows to LiveData because LIveData is lifecycle aware.
    * */

    var isLoggedIn = authRepo.isLoggedIn

    fun createAccount(email: String, password: String): Flow<AuthenticationState> = flow {
        emit(AuthenticationState.IN_PROGRESS)

        when (authRepo.createAccount(email, password)) {
            AuthenticationState.ERROR -> { emit(AuthenticationState.ERROR) }
            AuthenticationState.FAILED -> { emit(AuthenticationState.FAILED) }
            AuthenticationState.SUCCESS -> { emit(AuthenticationState.SUCCESS) }
            else -> { throw IllegalStateException("The AuthenticationState provided was not valid.") }
        }
    }
}