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
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Login
import androidx.compose.material.icons.twotone.Logout
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.Public
import androidx.compose.material.icons.twotone.SportsEsports
import androidx.compose.material.icons.twotone.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.huntergaming.classicsolitairedata.viewmodel.PlayerSettingsViewModel
import com.huntergaming.gamedata.model.Game
import com.huntergaming.gamedata.viewmodel.GameViewModel
import com.huntergaming.gamedata.viewmodel.PlayerViewModel
import com.huntergaming.ui.composable.HunterGamingBackgroundImage
import com.huntergaming.ui.composable.HunterGamingBodyText
import com.huntergaming.ui.composable.HunterGamingButton
import com.huntergaming.ui.composable.HunterGamingFieldRow
import com.huntergaming.ui.composable.HunterGamingHorizontalImageRadioButton
import com.huntergaming.ui.composable.HunterGamingRadioButtonRow
import com.huntergaming.ui.composable.HunterGamingSettingsRow
import com.huntergaming.ui.composable.HunterGamingTabs
import com.huntergaming.ui.composable.HunterGamingTextButton
import com.huntergaming.ui.composable.HunterGamingTitleText
import com.huntergaming.ui.uitl.DataRequestState
import com.huntergaming.ui.uitl.isValidField
import kotlinx.coroutines.launch

// composables

@ExperimentalPagerApi
@Composable
internal fun SettingsScreen(
    navController: NavHostController,
    authViewModel: AuthenticationViewModel,
    lifecycleOwner: LifecycleOwner,
    playerViewModel: PlayerViewModel?,
    gameViewModel: GameViewModel?,
    playerSettingsViewModel: PlayerSettingsViewModel?,
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
                if (isSystemInDarkTheme()) Icons.TwoTone.Public else Icons.Outlined.Public,
                if (isSystemInDarkTheme()) Icons.TwoTone.Person else Icons.Outlined.Person
            ),

            contentDescriptions = listOf(
                R.string.content_description_sound,
                R.string.content_description_gameplay,
                R.string.content_description_high_score,
                R.string.content_description_profile
            ),

            pagerState = rememberPagerState(
                pageCount = 4,
                initialOffscreenLimit = 2,
                infiniteLoop = true,
                initialPage = 0,
            ),

            {
                SoundSettings(
                    playerSettingsViewModel = playerSettingsViewModel,
                    lifecycleOwner = lifecycleOwner
                )
            },
            {
                GameSettings(
                    playerSettingsViewModel = playerSettingsViewModel,
                    lifecycleOwner = lifecycleOwner
                )
            },
            { HighScores(
                gameViewModel = gameViewModel,
                lifecycleOwner = lifecycleOwner
            )
            },
            {
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
private fun SoundSettings(
    playerSettingsViewModel: PlayerSettingsViewModel?,
    lifecycleOwner: LifecycleOwner
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        val audio = remember { mutableStateOf(.8f) }
        val audioOn = remember { mutableStateOf(true) }
        val sfx = remember { mutableStateOf(.8f) }
        val sfxOn = remember { mutableStateOf(true) }

        LaunchedEffect(key1 = true) {
            playerSettingsViewModel?.getAudio()?.observe(lifecycleOwner) {
                when (it) {
                    is DataRequestState.Success<*> -> audio.value = it.data as Float? ?: audio.value
                    DataRequestState.InProgress -> {}
                }
            }

            playerSettingsViewModel?.getAudioOn()?.observe(lifecycleOwner) {
                when (it) {
                    is DataRequestState.Success<*> -> audioOn.value = it.data as Boolean? ?: audioOn.value
                    DataRequestState.InProgress -> {}
                }
            }

            playerSettingsViewModel?.getSfx()?.observe(lifecycleOwner) {
                when (it) {
                    is DataRequestState.Success<*> -> sfx.value = it.data as Float? ?: sfx.value
                    DataRequestState.InProgress -> {}
                }
            }

            playerSettingsViewModel?.getSfxOn()?.observe(lifecycleOwner) {
                when (it) {
                    is DataRequestState.Success<*> -> sfxOn.value = it.data as Boolean? ?: sfxOn.value
                    DataRequestState.InProgress -> {}
                }
            }
        }

        HunterGamingSettingsRow(
            modifier = Modifier
                .fillMaxWidth(),

            onCheckChange = {},
            onSliderChange = {},
            settingName = R.string.audio,
            slider = audio,
            checkbox = audioOn
        )

        HunterGamingSettingsRow(
            modifier = Modifier
                .fillMaxWidth(),

            onCheckChange = {},
            onSliderChange = {},
            settingName = R.string.sfx,
            slider = sfx,
            checkbox = sfxOn
        )
    }
}

@Composable
private fun GameSettings(
    playerSettingsViewModel: PlayerSettingsViewModel?,
    lifecycleOwner: LifecycleOwner
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        val numberOfDecks = remember { mutableStateOf(0) }
        LaunchedEffect(key1 = true) {
            playerSettingsViewModel?.getNumberOfDecks()?.observe(lifecycleOwner) {
                when (it) {
                    is DataRequestState.Success<*> -> numberOfDecks.value = it.data as Int? ?: 0
                    DataRequestState.InProgress -> {}
                }
            }
        }

        HunterGamingRadioButtonRow(
            rowText = R.string.number_of_decks,

            radioButtonNames = listOf(
                stringResource(R.string.one),
                stringResource(R.string.two),
                stringResource(R.string.three)
            ),

            onSelect = { numberOfDecks.value = it.toInt() },
            selectedIndex = numberOfDecks.value
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            HunterGamingTitleText(text = R.string.deck_background)

            val deckBackground = remember { mutableStateOf(0) }
            LaunchedEffect(key1 = true) {
                playerSettingsViewModel?.getDeckBackground()?.observe(lifecycleOwner) {
                    when (it) {
                        is DataRequestState.Success<*> -> deckBackground.value = it.data as Int? ?: 0
                        DataRequestState.InProgress -> {}
                    }
                }
            }

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
                onSelect = { deckBackground.value = it },
                selectedIndex = deckBackground.value
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
@Composable
private fun HighScores(
    gameViewModel: GameViewModel?,
    lifecycleOwner: LifecycleOwner
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        val games = remember { mutableStateOf(listOf<Game>()) }

        for (game in games.value) {
            Row {
               HunterGamingBodyText(text = game.score)
            }
        }

        LaunchedEffect(key1 = true) {
            gameViewModel?.getHighScoreGames()?.observe(lifecycleOwner) {
                when (it) {
                    is DataRequestState.Success<*> -> { games.value = (it.data as List<Game>) }
                    DataRequestState.InProgress -> {}
                }
            }
        }
    }
}

@Composable
private fun ProfileSettings(
    authViewModel: AuthenticationViewModel?,
    playerViewModel: PlayerViewModel?,
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
                playerViewModel?.observePlayer()?.observe(lifecycleOwner) {
                    if (it != null) {
                        name.value = TextFieldValue(text = it.name)
                    }
                }
            }

            Column {

                val enabled = remember { mutableStateOf(false) }
                HunterGamingFieldRow(
                    fieldNameString = R.string.change_name,
                    hintString = R.string.change_name,
                    onValueChanged = { enabled.value = isValidField(it.text) },
                    textState = name
                )

                HunterGamingTextButton(
                    onClick = {
                        scope.launch { playerViewModel?.updatePlayerName(name.value.text)?.observe(lifecycleOwner) {} }
                    },
                    isEnabled = enabled.value,
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

                    onConfirm.value = { scope.launch { authViewModel?.resetPassword() } }
                },

                text = R.string.button_reset_password
            )
        }

        val textString = LocalContext.current.getString(R.string.dialog_logout_confirm)
        HunterGamingButton(
            onClick = {
                dialogState.value = true
                dialogTitle.value = R.string.dialog_title_confirm
                dialogText.value = textString

                onConfirm.value = {
                    scope.launch {
                        authViewModel?.logout()

                        navController.navigate(route = ComposableRoutes.AUTHENTICATION_SCREEN_NAV.route) {
                            popUpTo(route = ComposableRoutes.AUTHENTICATION_SCREEN_NAV.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            },

            icon =
                if (authViewModel?.isLoggedIn() == true) {
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
            playerSettingsViewModel = null,
            gameViewModel = null,
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
            gameViewModel = null,
            playerSettingsViewModel = null,
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
            gameViewModel = null,
            playerSettingsViewModel = null,
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
            gameViewModel = null,
            playerSettingsViewModel = null,
            dialogState = remember { mutableStateOf(false) },
            dialogTitle = remember { mutableStateOf(R.drawable.dialog_bg) },
            dialogText = remember { mutableStateOf("") },
            onConfirm = remember { mutableStateOf({}) }
        )
    }
}