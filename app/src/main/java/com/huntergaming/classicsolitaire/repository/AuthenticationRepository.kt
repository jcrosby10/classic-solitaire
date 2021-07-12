package com.huntergaming.classicsolitaire.repository

import com.google.firebase.auth.FirebaseUser
import com.huntergaming.classicsolitaire.data.Authentication
import com.huntergaming.classicsolitaire.web.InternetStatus
import com.huntergaming.classicsolitaire.web.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthenticationRepository
@Inject constructor(
    private val firebaseAuth: Authentication,
    private val internetStatus: InternetStatus
) {

    val isLoggedIn = firebaseAuth.isLoggedIn
    val emailVerifiedFlow = firebaseAuth.emailVerifiedFlow
    val currentUser: FirebaseUser? = firebaseAuth.currentUser

    fun validateStrongPassword(password: String): Boolean = firebaseAuth.validateStrongPassword(password)
    fun validateEmailAddress(email: String): Boolean = firebaseAuth.validateEmailAddress(email)

    fun resetPassword(email: String): Flow<RequestState> {
        if (!internetStatus.isConnected) return flowOf(RequestState.NoInternet)
        return firebaseAuth.resetPassword(email)
    }

    suspend fun createAccount(email: String, password: String): Flow<RequestState> {
        if (!internetStatus.isConnected) return flowOf(RequestState.NoInternet)
        return firebaseAuth.createAccount(email, password)
    }

    suspend fun sighIn(email: String, password: String): Flow<RequestState> {
        if (!internetStatus.isConnected) return flowOf(RequestState.NoInternet)
        return firebaseAuth.signIn(email, password)
    }
}