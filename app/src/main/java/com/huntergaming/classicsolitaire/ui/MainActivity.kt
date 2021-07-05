package com.huntergaming.classicsolitaire.ui

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val authViewModel: AuthenticationViewModel by viewModels()

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
                AppScreen(isLoggedIn = authViewModel.isLoggedIn)
            }
        }
    }
}

@Composable
fun AppScreen(isLoggedIn: Boolean) {
    if (!isLoggedIn) {
        LoginScreen()
    } else {
       MainMenu()
    }
}

@Composable
fun MainMenu() {

}

@Composable
fun LoginScreen() {
    val passwordVisibility = remember { mutableStateOf(false) }
    val createAccount = remember { mutableStateOf(false) }

    Text(
        text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.container_edge_padding))
            .fillMaxWidth()
            .zIndex(1f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(dimensionResource(id = R.dimen.container_edge_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.login_title),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding))
        )

        FieldRow(
            textResourceId = R.string.login_email,
            hintResourceId = R.string.login_email_hint,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding))
        )

        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.container_edge_padding))
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
                    .padding(end = dimensionResource(id = R.dimen.child_edge_padding))
            )
            Text(
                text = stringResource(id = R.string.login_show_password),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.child_edge_padding))
            )
        }

        FieldRow(
            textResourceId = R.string.login_password,
            hintResourceId = R.string.login_password_hint,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding)),
            showPassword = passwordVisibility.value
        )

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                content = {
                    Text(
                        text = stringResource(id = R.string.button_login),
                        style = MaterialTheme.typography.button
                    )
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.child_edge_padding))
            )
            Button(
                onClick = { createAccount.value = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                content = {
                    Text(
                        text = stringResource(id = R.string.button_create_account),
                        style = MaterialTheme.typography.button,
                    )
                },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.child_edge_padding))
            )
        }

        if (createAccount.value) CreateAccount()
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ClassicSolitaireTheme(darkTheme = false) {
        AppScreen(isLoggedIn = false)
    }
}

@Composable
private fun CreateAccount() {
    Dialog(onDismissRequest = {  }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(dimensionResource(id = R.dimen.container_edge_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            FieldRow(
                textResourceId = R.string.create_account_firstname,
                hintResourceId = R.string.create_account_firstname_hint,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding))
            )

            FieldRow(
                textResourceId = R.string.create_account_lastname,
                hintResourceId = R.string.create_account_lastname_hint,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding))
            )

            FieldRow(
                textResourceId = R.string.create_account_email,
                hintResourceId = R.string.create_account_email_hint,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding))
            )

            FieldRow(
                textResourceId = R.string.create_account_password,
                hintResourceId = R.string.create_account_password_hint,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding))
            )
        }
    }
}

@Composable
private fun FieldRow(
    textResourceId: Int,
    hintResourceId: Int,
    modifier: Modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding)),
    showPassword: Boolean = false
) {
    Row(modifier = modifier) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        Text(
            text = stringResource(id = textResourceId),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.child_edge_padding)),
            textAlign = TextAlign.Center
        )
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.background,
                errorIndicatorColor = MaterialTheme.colors.error
            ),
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.child_edge_padding)),
            placeholder = {
                Text(
                    text = stringResource(id = hintResourceId),
                    style = MaterialTheme.typography.body1
                )
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
        )
    }
}