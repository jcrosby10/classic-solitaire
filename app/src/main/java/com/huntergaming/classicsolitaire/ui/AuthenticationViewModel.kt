package com.huntergaming.classicsolitaire.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.huntergaming.gamedata.model.Player
import com.huntergaming.gamedata.repository.AuthenticationRepository
import com.huntergaming.gamedata.repository.SolitaireRepository
import com.huntergaming.web.InternetStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepo: AuthenticationRepository,
    private val solitaireRepository: SolitaireRepository,
    private val internetStatus: InternetStatus,
    @ApplicationContext private val context: Context
): ViewModel() {

    /*
    * ViewModels should convert flows to LiveData because LIveData is lifecycle aware.
    * */

    val isLoggedIn = authRepo.isLoggedIn
    private val emailVerifiedFlow = authRepo.emailVerifiedFlow

    init {
        CoroutineScope(Dispatchers.Default).launch {
            emailVerifiedFlow.collect {
                if (it == true) {
                    context
                        .getSharedPreferences("temp", Context.MODE_PRIVATE).apply {
                            solitaireRepository.create(
                                Player(
                                    getString("f", "")!!, getString("l", "")!!, 0, 0
                                )
                            )
                        }
                }
            }
        }
    }

    fun validateStrongPassword(password: String): Boolean = authRepo.validateStrongPassword(password)
    fun validateEmailAddress(email: String): Boolean = authRepo.validateEmailAddress(email)

    fun resetPassword(email: String): LiveData<com.huntergaming.gamedata.RequestState> = flow {
//        emit(com.huntergaming.gamedata.RequestState.InProgress)

        authRepo.resetPassword(email).collect {
            emit(it)
        }
    }.asLiveData()

    fun signIn(email: String, password: String): LiveData<com.huntergaming.gamedata.RequestState> = flow {
//        emit(com.huntergaming.gamedata.RequestState.InProgress)

        authRepo.sighIn(email, password).collect {
            emit(it)
        }
    }.asLiveData()

    fun createAccount(email: String, password: String): LiveData<com.huntergaming.gamedata.RequestState> = flow {
//        emit(com.huntergaming.gamedata.RequestState.InProgress)
        authRepo.createAccount(email, password).collect { emit(it) }
    }.asLiveData()
}