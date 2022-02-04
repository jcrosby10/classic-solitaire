package com.huntergaming.classicsolitaire.ui.compose.screens

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
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
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.classicsolitaire.viewmodel.PlayerViewModel
import com.huntergaming.gamedata.DataRequestState
import com.huntergaming.ui.composable.HunterGamingAlertDialog
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
    lifecycleOwner: LifecycleOwner?,
    playerViewModel: PlayerViewModel?
) {

    val dialogState = remember { mutableStateOf(false) }
    val dialogText = remember { mutableStateOf(R.string.default_string) }
    val dialogTitle = remember { mutableStateOf(R.string.default_string) }
    val onConfirm = remember { mutableStateOf({}) }

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
                if (lifecycleOwner != null && playerViewModel != null) {
                    ProfileSettings(
                        authViewModel = authViewModel,
                        dialogState = dialogState,
                        dialogText = dialogText,
                        lifecycleOwner = lifecycleOwner,
                        playerViewModel = playerViewModel,
                        dialogTitle = dialogTitle
                    )
                }
            }
        )

        HunterGamingAlertDialog(
            onConfirm = { onConfirm.value },
            title = dialogTitle.value,
            text = dialogText.value,
            backgroundImage = R.drawable.dialog_bg,
            state = dialogState,
            onDismiss = {}
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
    dialogText: MutableState<Int>,
    dialogState: MutableState<Boolean>
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            val name = remember { mutableStateOf(TextFieldValue(text =  "")) }

            rememberCoroutineScope().launch {
                playerViewModel.getPlayerName().observe(lifecycleOwner) {
                    when (it) {
                        DataRequestState.InProgress -> {}
                        DataRequestState.NoInternet -> {}
                        is DataRequestState.Success<*> ->{}
                        is DataRequestState.Error ->{}
                    }
                }
            }

            HunterGamingFieldRow(
                fieldNameString = R.string.change_name,
                hintString = R.string.change_name,
                onValueChanged = {},
                textState = name
            )

            val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }

            SnackbarHost(hostState = snackbarHostState.value)

            HunterGamingTextButton(onClick = { /*TODO*/ }, text = R.string.button_reset_password)
        }

        HunterGamingButton(
            onClick = { /*TODO*/ },

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
            lifecycleOwner = null,
            playerViewModel = null
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
            lifecycleOwner = null,
            playerViewModel = null
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
            lifecycleOwner = null,
            playerViewModel = null
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
            lifecycleOwner = null,
            playerViewModel = null
        )
    }
}