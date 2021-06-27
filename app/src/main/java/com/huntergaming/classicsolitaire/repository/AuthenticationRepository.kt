package com.huntergaming.classicsolitaire.repository

import com.huntergaming.classicsolitaire.data.FirebaseAuthentication
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuthentication
) {

    val isLoggedIn = firebaseAuth.isLoggedIn

    fun validateStrongPassword(password: String): Boolean = firebaseAuth.validateStrongPassword(password)

    suspend fun createAccount(email: String, password: String) = firebaseAuth.createAccount(email, password)
}