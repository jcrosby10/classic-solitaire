package com.huntergaming.classicsolitaire.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.compose.ClassicSolitaireButton
import com.huntergaming.classicsolitaire.ui.compose.CreateAccountDialog
import com.huntergaming.classicsolitaire.ui.compose.FieldRow
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.classicsolitaire.web.InternetStatus
import com.huntergaming.classicsolitaire.web.RequestState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var internetStatus: InternetStatus

    private val authViewModel: AuthenticationViewModel? by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        setContent {
            ClassicSolitaireTheme {
                AppScreen(
                    isLoggedIn = authViewModel?.isLoggedIn == true,
                    authViewModel = authViewModel,
                    activity = this
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build(),
            internetStatus.networkCallBack
        )
    }

    override fun onPause() {
        super.onPause()
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(internetStatus.networkCallBack)
    }
}

@ExperimentalCoroutinesApi
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ClassicSolitaireTheme(darkTheme = false) {
        AppScreen(isLoggedIn = false)
    }
}

@ExperimentalCoroutinesApi
@Composable
fun AppScreen(isLoggedIn: Boolean, authViewModel: AuthenticationViewModel? = null, activity: AppCompatActivity? = null) {
    if (!isLoggedIn) {
        LoginScreen(authViewModel, activity!!)
    } else {
       MainMenu()
    }
}

@Composable
fun MainMenu() {

}

@ExperimentalCoroutinesApi
@Composable
fun LoginScreen(authViewModel: AuthenticationViewModel?, activity: AppCompatActivity) {
    val passwordVisibility = remember { mutableStateOf(false) }
    val createAccount = remember { mutableStateOf(false) }

    Text(
        text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.edge_padding_10dp))
            .fillMaxWidth()
            .zIndex(1f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.login_title),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.edge_padding_10dp))
        )

        val textState = remember {
            listOf(
                mutableStateOf(TextFieldValue()),
                mutableStateOf(TextFieldValue())
            )
        }

        val isError = remember { mutableStateOf(false) }
        val isLoginEnabled = remember { mutableStateOf(false) }

        FieldRow(
            textResourceId = R.string.login_email,
            hintResourceId = R.string.login_email_hint,
            textState = textState[0],
            onValueChanged = {
                textState[0].value = it

                if (authViewModel?.validateEmailAddress(it.text) == true) {
                    isError.value = false
                    isLoginEnabled.value = true
                }
                else {
                    isError.value = true
                    isLoginEnabled.value = false
                }
            }
        )

        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.edge_padding_10dp))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = passwordVisibility.value,
                onCheckedChange = { passwordVisibility.value = !passwordVisibility.value },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.secondary,
                    uncheckedColor = MaterialTheme.colors.primaryVariant,
                    checkmarkColor = MaterialTheme.colors.onPrimary
                ),
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.edge_padding_5dp))
            )
            Text(
                text = stringResource(id = R.string.login_show_password),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.edge_padding_5dp))
            )
        }

        FieldRow(
            textResourceId = R.string.login_password,
            hintResourceId = R.string.login_password_hint,
            hideText = !passwordVisibility.value,
            textState = textState[1],
            onValueChanged = { textState[1].value = it }
        )

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.edge_padding_10dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClassicSolitaireButton(
                isEnabled = isLoginEnabled.value,
                onClick = {
                    val resultObserver = Observer<RequestState> {
                        //todo invalid password - The password is invalid or the user does not have a password.
                        // todo invalid email - There is no user record corresponding to this identifier. The user may have been deleted.
                    }
                    authViewModel?.signIn(textState[0].value.text, textState[1].value.text)//?.observe(activity, resultObserver)
                },
                text = R.string.button_login
            )

            ClassicSolitaireButton(
                onClick = { createAccount.value = true },
                text = R.string.button_create_account
            )
        }

        if (createAccount.value) CreateAccountDialog(
            authViewModel = authViewModel,
            state = createAccount,
            activity = LocalContext.current as AppCompatActivity
        )
    }
}