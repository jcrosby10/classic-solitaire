package com.huntergaming.classicsolitaire.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    var passwordVisibility = remember { false }
    Text(
        text = stringResource(id = R.string.login_title),
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.container_edge_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding))) {
            val textState = remember { mutableStateOf(TextFieldValue()) }
            Text(
                text = stringResource(id = R.string.login_email),
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.child_edge_padding)) // equivalent to padding inside
                    .padding(end = dimensionResource(id = R.dimen.child_edge_padding)) // second padding acts as to margin putting space on the outside of the item
            )
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.child_edge_padding)),
                placeholder =  { Text(text = stringResource(id = R.string.login_email_hint)) },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
            )
        }

        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.container_edge_padding))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = passwordVisibility,
                onCheckedChange = { passwordVisibility = !passwordVisibility },
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.child_edge_padding))
            )
            Text(
                text = stringResource(id = R.string.login_show_password),
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.child_edge_padding))
            )
        }

        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding))) {
            val textState = remember { mutableStateOf(TextFieldValue()) }
            Text(
                text = stringResource(id = R.string.login_password),
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.child_edge_padding))
            )
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.child_edge_padding)),
                placeholder = { Text(text = stringResource(id = R.string.login_password_hint)) }
            )
        }

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.container_edge_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {  },
                content = { Text(text = stringResource(id = R.string.login)) },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.child_edge_padding))
            )
            Button(
                onClick = {  },
                content = { Text(text = stringResource(id = R.string.create_account)) },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.child_edge_padding))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ClassicSolitaireTheme {
        AppScreen(isLoggedIn = false)
    }
}