package com.huntergaming.classicsolitaire.data

import android.text.TextUtils
import android.util.Patterns.EMAIL_ADDRESS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FirebaseAuthentication @Inject constructor(
    private val auth: FirebaseAuth
): Authentication {

    override val emailVerifiedFlow: Flow<Boolean?> = callbackFlow {
        auth.addAuthStateListener {
            if (it.currentUser != null) {
                trySend(it.currentUser?.isEmailVerified)
            }
        }

        awaitClose { cancel() }
    }

    override val isLoggedIn: Boolean
        get() = auth.currentUser != null

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun validateStrongPassword(password: String): Boolean =
        """^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@${'$'}!%*?&])[A-Za-z\d@${'$'}!%*?&]{8,}${'$'}""".toRegex().matches(password)

    override fun validateEmailAddress(email: String): Boolean =
        if (TextUtils.isEmpty(email)) false else EMAIL_ADDRESS.matcher(email).matches()

    override fun resetPassword(email: String): Flow<AuthenticationState> = callbackFlow {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener { trySend(AuthenticationState.Success) }
            .addOnFailureListener {
                Timber.e(it)
                trySend(AuthenticationState.Error(it.message))
            }

        awaitClose { cancel() }
    }

    override suspend fun createAccount(email: String, password: String): Flow<AuthenticationState> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                auth.currentUser?.sendEmailVerification()
                    ?.addOnSuccessListener {
                        signOut()
                        trySend(AuthenticationState.Success)
                    }
                    ?.addOnFailureListener {
                        signOut()
                        Timber.e(it)
                        trySend(AuthenticationState.Error(it.message))
                    }
            }
            .addOnFailureListener {
                signOut()
                Timber.e(it)
                trySend(AuthenticationState.Error(it.message))
            }

        awaitClose { cancel() }
    }

    override fun deleteAccount(): Flow<AuthenticationState> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String): Flow<AuthenticationState> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                trySend(AuthenticationState.Success)
            }
            .addOnFailureListener {
                Timber.e(it)
                trySend(AuthenticationState.Error(it.message))
            }

        awaitClose { cancel() }
    }

    override fun signOut() {
        auth.signOut()
    }
}

sealed class AuthenticationState {// pull to separate file
    object NoInternet: AuthenticationState()
    object InProgress: AuthenticationState()
    object Success : AuthenticationState()
    class Error(val message: String?) : AuthenticationState()
}