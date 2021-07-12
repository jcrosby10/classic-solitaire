package com.huntergaming.classicsolitaire.ui.compose

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Observer
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.data.AuthenticationState
import com.huntergaming.classicsolitaire.ui.AuthenticationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun ClassicSolitaireButton(
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    text: Int
) {
    Button(
        enabled = isEnabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            disabledBackgroundColor = MaterialTheme.colors.error,
            disabledContentColor = MaterialTheme.colors.onError
        ),
        content = {
            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.button,
            )
        },
        modifier = Modifier.padding(dimensionResource(id = R.dimen.edge_padding_5dp))
    )
}

@ExperimentalCoroutinesApi
@Composable
fun CreateAccountDialog(
    state: MutableState<Boolean>,
    authViewModel: AuthenticationViewModel?,
    activity: AppCompatActivity
) {
    Dialog(onDismissRequest = { state.value = false }) {
        val textState = remember {
            listOf(
                mutableStateOf(TextFieldValue()),
                mutableStateOf(TextFieldValue()),
                mutableStateOf(TextFieldValue()),
                mutableStateOf(TextFieldValue())
            )
        }

        val isError = remember { mutableStateOf(false) }
        val validEmail = remember { mutableStateOf(false) }
        val validPassword = remember { mutableStateOf(false) }
        val validFirstName = remember { mutableStateOf(false) }
        val validLastName = remember { mutableStateOf(false) }

        Column(modifier = Modifier
            .background(MaterialTheme.colors.primaryVariant)
            .padding(dimensionResource(id = R.dimen.edge_padding_10dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            FieldRow(
                textResourceId = R.string.create_account_firstname,
                hintResourceId = R.string.create_account_firstname_hint,
                textState = textState[0],
                onValueChanged = {
                    textState[0].value = it
                    validFirstName.value = it.text != ""
                }
            )

            FieldRow(
                textResourceId = R.string.create_account_lastname,
                hintResourceId = R.string.create_account_lastname_hint,
                textState = textState[1],
                onValueChanged = {
                    textState[1].value = it
                    validLastName.value = it.text != ""
                }
            )

            FieldRow(
                textResourceId = R.string.create_account_email,
                hintResourceId = R.string.create_account_email_hint,
                textState = textState[2],
                isError = isError.value,
                onValueChanged = {
                    textState[2].value = it

                    if (authViewModel?.validateEmailAddress(it.text) == true) {
                        isError.value = false
                        validEmail.value = true
                    }
                    else {
                        isError.value = true
                        validEmail.value = false
                    }
                }
            )

            FieldRow(
                textResourceId = R.string.create_account_password,
                hintResourceId = R.string.create_account_password_hint,
                textState = textState[3],
                isError = isError.value,
                onValueChanged = {
                    textState[3].value = it

                    if (authViewModel?.validateStrongPassword(it.text) == true) {
                        isError.value = false
                        validPassword.value = true
                    }
                    else {
                        isError.value = true
                        validPassword.value = false
                    }
                },
                hideText = true
            )

            val showProgressIndicator = remember { mutableStateOf(false) }
            val showAlertDialog = remember { mutableStateOf(false) }
            val resetPassword = remember { mutableStateOf(false) }
            val createAccountMessage = remember { mutableStateOf(-1) }

            if (showProgressIndicator.value) ClassicSolitaireCircularProgressIndicator()
            if (showAlertDialog.value) {
                ClassicSolitaireAlertDialog(
                    confirmButton = {
                        ClassicSolitaireButton(
                            onClick = { showAlertDialog.value = false },
                            text = R.string.button_ok
                        )
                    },
                    dismissButton = {
                        if (resetPassword.value) {
                            ClassicSolitaireButton(
                                isEnabled = validEmail.value,
                                onClick = {
                                    val resultObserver = Observer<AuthenticationState> { result ->
                                        when (result) {
                                            is AuthenticationState.NoInternet -> {
                                                createAccountMessage.value = R.string.error_no_internet
                                                showAlertDialog.value = true
                                            }

                                            is AuthenticationState.InProgress -> { showProgressIndicator.value = true }

                                            is AuthenticationState.Error -> {
                                                resetPassword.value = true
                                                createAccountMessage.value = R.string.create_account_error_message
                                                showAlertDialog.value = true
                                            }

                                            is AuthenticationState.Success -> {
                                                showProgressIndicator.value = false
                                                showAlertDialog.value = false
                                            }

                                            else -> { throw IllegalStateException("The AuthenticationState provided was not valid.") }
                                        }
                                    }
                                    authViewModel?.resetPassword(textState[2].value.text)?.observe(activity, resultObserver)
                                },
                                text = R.string.create_account_reset_password)
                        }
                    },
                    title = { Text(text = stringResource(id = R.string.create_account_title)) }
                ) { Text(text = stringResource(id = createAccountMessage.value)) }
            }

            Row(Modifier.padding(dimensionResource(id = R.dimen.edge_padding_10dp))) {
                ClassicSolitaireButton(
                    isEnabled = validPassword.value && validEmail.value && validFirstName.value && validLastName.value,
                    onClick = {
                        val resultObserver = Observer<AuthenticationState> { result ->
                            when (result) {
                                is AuthenticationState.NoInternet -> {
                                    createAccountMessage.value = R.string.error_no_internet
                                    showProgressIndicator.value = false
                                    showAlertDialog.value = true
                                }

                                is AuthenticationState.InProgress -> { showProgressIndicator.value = true }

                                is AuthenticationState.Error -> {
                                    resetPassword.value = true
                                    val errorMessage =
                                        if (result.message == "The email address is already in use by another account.") R.string.create_account_email_already_in_use
                                        else R.string.create_account_error_message
                                    createAccountMessage.value = errorMessage
                                    showProgressIndicator.value = false
                                    showAlertDialog.value = true
                                }

                                is AuthenticationState.Success -> {
                                    createAccountMessage.value = R.string.create_account_success_message
                                    showProgressIndicator.value = false
                                    showAlertDialog.value = true
                                }

                                else -> { throw IllegalStateException("The AuthenticationState provided was not valid.") }
                            }
                        }

                        activity
                            .getSharedPreferences("temp", Context.MODE_PRIVATE).edit()
                            .putString("f", textState[0].value.text)
                            .putString("l", textState[1].value.text)
                            .apply()

                        authViewModel?.createAccount(
                            textState[2].value.text,
                            textState[3].value.text
                        )?.observe(activity, resultObserver)
                    },
                    text = R.string.button_create
                )

                ClassicSolitaireButton(
                    onClick = { state.value = false },
                    text = R.string.button_cancel
                )
            }
        }
    }
}

@Composable
fun ClassicSolitaireAlertDialog(
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit),
    text: @Composable (() -> Unit)
) {
    AlertDialog(
        onDismissRequest = {  },
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        title = title,
        text = text,
        backgroundColor = MaterialTheme.colors.primaryVariant
    )
}

@Composable
fun ClassicSolitaireCircularProgressIndicator() {
    CircularProgressIndicator(
        color = MaterialTheme.colors.secondary
    )
}