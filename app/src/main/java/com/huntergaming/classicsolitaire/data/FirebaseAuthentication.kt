package com.huntergaming.classicsolitaire.data

import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthentication @Inject constructor(
    private val auth: FirebaseAuth
): Authentication {

    override val isLoggedIn: Boolean
        get() = auth.currentUser != null

    override fun validateStrongPassword(password: String): Boolean =
        """^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@${'$'}!%*?&])[A-Za-z\d@${'$'}!%*?&]{8,}${'$'}""".toRegex().matches(password)

    override suspend fun createAccount(email: String, password: String): AuthenticationState = suspendCoroutine {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { createAccount ->
                if (createAccount.isSuccessful) {
                     it.resume(AuthenticationState.SUCCESS)
                }
                else {
                    it.resume(AuthenticationState.FAILED)
                }
            }
            .addOnFailureListener { e ->
                Timber.e(e)
                it.resume(AuthenticationState.FAILED)
            }
    }

    override fun deleteAccount(): AuthenticationState {
        TODO("Not yet implemented")
    }

    override fun signIn(): AuthenticationState {
        TODO("Not yet implemented")
    }

    override fun signOut(): AuthenticationState {
        TODO("Not yet implemented")
    }
}

enum class AuthenticationState {
    IN_PROGRESS, SUCCESS, FAILED, ERROR
}