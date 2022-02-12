package com.huntergaming.classicsolitaire.ui.compose.screens

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Login
import androidx.compose.material.icons.twotone.Logout
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.SportsEsports
import androidx.compose.material.icons.twotone.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.huntergaming.authentication.viewmodel.AuthenticationViewModel
import com.huntergaming.classicsolitaire.ComposableRoutes
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.gamedata.viewmodel.PlayerViewModel
import com.huntergaming.ui.composable.HunterGamingBackgroundImage
import com.huntergaming.ui.composable.HunterGamingButton
import com.huntergaming.ui.composable.HunterGamingFieldRow
import com.huntergaming.ui.composable.HunterGamingHorizontalImageRadioButton
import com.huntergaming.ui.composable.HunterGamingRadioButtonRow
import com.huntergaming.ui.composable.HunterGamingSettingsRow
import com.huntergaming.ui.composable.HunterGamingTabs
import com.huntergaming.ui.composable.HunterGamingTextButton
import com.huntergaming.ui.composable.HunterGamingTitleText
import kotlinx.coroutines.launch

// composables

@ExperimentalPagerApi
@Composable
internal fun SettingsScreen(
    navController: NavHostController,
    authViewModel: AuthenticationViewModel,
    lifecycleOwner: LifecycleOwner,
    playerViewModel: PlayerViewModel?,
    dialogTitle: MutableState<Int>,
    dialogText: MutableState<String>,
    dialogState: MutableState<Boolean>,
    onConfirm: MutableState<() -> Unit>
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        HunterGamingBackgroundImage(image = R.drawable.menu_bg)

        HunterGamingTabs(

            tabIcons = listOf(
                if (isSystemInDarkTheme()) Icons.TwoTone.VolumeUp else Icons.Outlined.VolumeUp,
                if (isSystemInDarkTheme()) Icons.TwoTone.SportsEsports else Icons.Outlined.SportsEsports,
                if (isSystemInDarkTheme()) Icons.TwoTone.Person else Icons.Outlined.Person
            ),

            contentDescriptions = listOf(
                R.string.content_description_sound,
                R.string.content_description_gameplay,
                R.string.content_description_profile
            ),

            pagerState = rememberPagerState(
                pageCount = 3,
                initialOffscreenLimit = 2,
                infiniteLoop = true,
                initialPage = 0,
            ),

            { SoundSettings() },
            { GameSettings() },
            {
                if (playerViewModel != null) {
                    ProfileSettings(
                        authViewModel = authViewModel,
                        dialogState = dialogState,
                        dialogText = dialogText,
                        lifecycleOwner = lifecycleOwner,
                        playerViewModel = playerViewModel,
                        dialogTitle = dialogTitle,
                        onConfirm = onConfirm,
                        navController = navController
                    )
                }
            }
        )

        HunterGamingButton(

            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_xlarge),
                    top = dimensionResource(id = R.dimen.padding_xxlarge)
                ),

            onClick = { navController.popBackStack() },
            icon = if (isSystemInDarkTheme()) Icons.TwoTone.ArrowBack else Icons.Outlined.ArrowBack,
            contentDescription = R.string.content_description_quit
        )
    }
}

@Composable
private fun SoundSettings() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        HunterGamingSettingsRow(

            modifier = Modifier
                .fillMaxWidth(),

            onCheckChange = {},
            onSliderChange = {},
            settingName = R.string.audio
        )

        HunterGamingSettingsRow(

            modifier = Modifier
                .fillMaxWidth(),

            onCheckChange = {},
            onSliderChange = {},
            settingName = R.string.sfx
        )
    }
}

@Composable
private fun GameSettings() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        HunterGamingRadioButtonRow(
            rowText = R.string.number_of_decks,

            radioButtonNames = listOf(
                stringResource(R.string.one),
                stringResource(R.string.two),
                stringResource(R.string.three)
            ),

            onSelect = {  },
            selectedIndex = 0
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            HunterGamingTitleText(text = R.string.deck_background)

            HunterGamingHorizontalImageRadioButton(
                images = listOf(
                    R.drawable.card_back_red,
                    R.drawable.card_back_yellow,
                    R.drawable.card_back_orange,
                    R.drawable.card_back_dark_red,
                    R.drawable.card_back_blue,
                    R.drawable.card_back_dark_blue
                ),

                imageWidth = com.huntergaming.ui.R.dimen.image_radio_width,
                imageHeight = com.huntergaming.ui.R.dimen.image_radio_height,
                contentDescriptions = stringArrayResource(R.array.content_descriptions_card_backs).toList(),
                onSelect = { },
                selectedIndex = 0
            )
        }
    }
}

@Composable
private fun ProfileSettings(
    authViewModel: AuthenticationViewModel,
    playerViewModel: PlayerViewModel,
    lifecycleOwner: LifecycleOwner,
    dialogTitle: MutableState<Int>,
    dialogText: MutableState<String>,
    dialogState: MutableState<Boolean>,
    onConfirm: MutableState<() -> Unit>,
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        val scope = rememberCoroutineScope()
        val showProgress = remember { mutableStateOf(false) }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            val name = remember { mutableStateOf(TextFieldValue(text =  "")) }

            scope.launch {
                playerViewModel.observePlayer().observe(lifecycleOwner) {
                    if (it != null) {
                        name.value = TextFieldValue(text = it.name)
                    }
                }
            }

            Column {

                HunterGamingFieldRow(
                    fieldNameString = R.string.change_name,
                    hintString = R.string.change_name,
                    onValueChanged = {},
                    textState = name
                )

                HunterGamingTextButton(
                    onClick = {
                        scope.launch { playerViewModel.updatePlayerName(name.value.text).observe(lifecycleOwner) {} }
                    },
                    text = R.string.button_submit
                )
            }

            if (showProgress.value) CircularProgressIndicator()

            val textString = LocalContext.current.getString(R.string.dialog_reset_password)

            HunterGamingTextButton(
                onClick = {
                    dialogState.value = true
                    dialogTitle.value = R.string.dialog_title_confirm
                    dialogText.value = textString

                    onConfirm.value = { scope.launch { authViewModel.resetPassword() } }
                },

                text = R.string.button_reset_password
            )
        }

        // todo add top ten games

        // todo data state has to be handled individually

        HunterGamingButton(
            onClick = {// todo dialog are you sure
                scope.launch {
                    authViewModel.logout()

                    navController.navigate(route = ComposableRoutes.AUTHENTICATION_SCREEN_NAV.route) {
                        popUpTo(route = ComposableRoutes.AUTHENTICATION_SCREEN_NAV.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            },

            icon =
                if (authViewModel.isLoggedIn() == true) {
                    if (isSystemInDarkTheme()) Icons.TwoTone.Logout else Icons.Outlined.Logout
                }
                else if (isSystemInDarkTheme()) Icons.TwoTone.Login else Icons.Outlined.Login,

            contentDescription = R.string.content_description_logout
        )
    }
}

// previews

@ExperimentalPagerApi
@Preview(showBackground = true, widthDp = 1280, heightDp = 720, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    ClassicSolitaireTheme {
        SettingsScreen(
            navController = rememberNavController(),
            authViewModel = AuthenticationViewModel(null, LocalContext.current),
            lifecycleOwner = ComponentActivity(),
            playerViewModel = null,
            dialogState = remember { mutableStateOf(false) },
            dialogTitle = remember { mutableStateOf(R.drawable.dialog_bg) },
            dialogText = remember { mutableStateOf("") },
            onConfirm = remember { mutableStateOf({}) }
        )
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true, widthDp = 1920, heightDp = 1080)
@Composable
private fun DefaultPreview2() {
    ClassicSolitaireTheme {
        SettingsScreen(
            navController = rememberNavController(),
            authViewModel = AuthenticationViewModel(null, LocalContext.current),
            lifecycleOwner = ComponentActivity(),
            playerViewModel = null,
            dialogState = remember { mutableStateOf(false) },
            dialogTitle = remember { mutableStateOf(R.drawable.dialog_bg) },
            dialogText = remember { mutableStateOf("") },
            onConfirm = remember { mutableStateOf({}) }
        )
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true, widthDp = 800, heightDp = 480, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview3() {
    ClassicSolitaireTheme {
        SettingsScreen(
            navController = rememberNavController(),
            authViewModel = AuthenticationViewModel(null, LocalContext.current),
            lifecycleOwner = ComponentActivity(),
            playerViewModel = null,
            dialogState = remember { mutableStateOf(false) },
            dialogTitle = remember { mutableStateOf(R.drawable.dialog_bg) },
            dialogText = remember { mutableStateOf("") },
            onConfirm = remember { mutableStateOf({}) }
        )
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true, widthDp = 854, heightDp = 480)
@Composable
private fun DefaultPreview4() {
    ClassicSolitaireTheme {
        SettingsScreen(
            navController = rememberNavController(),
            authViewModel = AuthenticationViewModel(null, LocalContext.current),
            lifecycleOwner = ComponentActivity(),
            playerViewModel = null,
            dialogState = remember { mutableStateOf(false) },
            dialogTitle = remember { mutableStateOf(R.drawable.dialog_bg) },
            dialogText = remember { mutableStateOf("") },
            onConfirm = remember { mutableStateOf({}) }
        )
    }
}