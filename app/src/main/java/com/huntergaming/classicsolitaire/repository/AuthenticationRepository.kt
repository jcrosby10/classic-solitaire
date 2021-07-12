package com.huntergaming.classicsolitaire.repository

import com.google.firebase.auth.FirebaseUser
import com.huntergaming.classicsolitaire.data.Authentication
import com.huntergaming.classicsolitaire.data.AuthenticationState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthenticationRepository
@Inject constructor(
    private val firebaseAuth: Authentication
) {

    val isLoggedIn = firebaseAuth.isLoggedIn
    val emailVerifiedFlow = firebaseAuth.emailVerifiedFlow
    val currentUser: FirebaseUser? = firebaseAuth.currentUser

    fun validateStrongPassword(password: String): Boolean = firebaseAuth.validateStrongPassword(password)
    fun validateEmailAddress(email: String): Boolean = firebaseAuth.validateEmailAddress(email)

    fun resetPassword(email: String): Flow<AuthenticationState> = firebaseAuth.resetPassword(email)

    suspend fun createAccount(email: String, password: String): Flow<AuthenticationState> = firebaseAuth.createAccount(email, password)

    suspend fun sighIn(email: String, password: String): Flow<AuthenticationState> = firebaseAuth.signIn(email, password)
}