package com.huntergaming.classicsolitaire.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.UserInfo

import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClassicSolitaireTheme {
//                AppScreen(userInfo = )
            }
        }
    }
}

@Composable
fun AppScreen(userInfo: UserInfo) {
//    if (!userInfo.userLoggedIn) {
//        LoginScreen(userInfo)
//    } else {
//        UserInfoScreen(userInfo)
//    }
}

@Composable
fun MainMenu(userInfo: UserInfo) {
//    Column(modifier = Spacing(8.dp)) {
//        Text(text = "")
//        Text(
//            text = "Username: ${userInfo.userName}",
//            modifier = Spacing(8.dp),
//            style = (+MaterialTheme.typography()).h6
//        )

//    }
}

@Composable
fun LoginScreen(/*userInfo: UserInfo = UserInfo()*/) {
    Column() {

    }
//    Column {
//        val userNameState = + state { "" }
//        Text(
//            text = "Username:",
//            modifier = Spacing(8.dp),
//            style = (+MaterialTheme.typography()).h4
//        )
//        Surface(border = Border(Color.Gray, 1.dp), modifier = Spacing(8.dp)) {
//            Padding(8.dp) {
//                TextField(
//                    value = userNameState.value,
//                    onValueChange = { userNameState.value = it }
//                )
//
//            }
//        }
//
//        val passwordState = + state { "" }
//        Text(
//            text = "Password:",
//            modifier = Spacing(8.dp),
//            style = (+MaterialTheme.typography()).h4
//        )
//        Surface(border = Border(Color.Gray, 1.dp), modifier = Spacing(8.dp)) {
//            Padding(padding = 8.dp) {
//                PasswordTextField(
//                    value = passwordState.value,
//                    onValueChange = { passwordState.value = it }
//                )
//            }
//        }
//
//        if (userNameState.value.isNotEmpty()
//            && passwordState.value.isNotEmpty()
//        ) {
//            Row(arrangement = Arrangement.End) {
//                Button(
//                    text = "Login",
//                    modifier = Spacing(8.dp),
//                    onClick = {
//                        println("Logged in!")
//                        userInfo.userName = userNameState.value
//                        userInfo.userLoggedIn = true
//                    }
//                )
//            }
//        } else {
//            Text(
//                text = "Please enter both username and password!",
//                modifier = Spacing(8.dp),
//                style = (+MaterialTheme.typography()).h6
//            )
//        }
//    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ClassicSolitaireTheme {
//        AppScreen(userInfo = )
    }
}