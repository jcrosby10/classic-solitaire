package com.huntergaming.classicsolitaire.ui.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.huntergaming.classicsolitaire.ComposableRoutes
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.ui.composable.HunterGamingButton
import com.huntergaming.ui.composable.HunterGamingHorizontalImageRadioButton
import com.huntergaming.ui.composable.HunterGamingHorizontalRadioButton
import com.huntergaming.ui.composable.HunterGamingHorizontalSlider
import com.huntergaming.ui.composable.HunterGamingTabs
import com.huntergaming.ui.composable.HunterGamingTitleText

@ExperimentalPagerApi
@Preview(showBackground = true, widthDp = 720, heightDp = 360)
@Composable
private fun DefaultPreviewSettingsScreen() {
    ClassicSolitaireTheme {
        SettingsScreen(
            navController = rememberNavController()
        )
    }
}

@ExperimentalPagerApi
@Composable
internal fun SettingsScreen(navController: NavHostController) {
    val tabIcons = remember {
        mutableStateOf(
            listOf(
                R.drawable.sound_on,
                R.drawable.controller,
                R.drawable.lock
            )
        )
    }

    val tabTitles = remember {
        mutableStateOf(
            listOf(
                R.string.sound,
                R.string.game,
                R.string.privacy
            )
        )
    }

    HunterGamingTabs(
        tabIcons = tabIcons.value,
        tabTitles = tabTitles.value,
        pagerState = rememberPagerState(
            pageCount = tabIcons.value.size,
            initialOffscreenLimit = 2,
            infiniteLoop = true,
            initialPage = 1,
        ),
        { SoundSettings() },  { GameSettings() }, { PrivacySettings() }
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(
                zIndex = -1f
            )
    ) {
        val (back) = createRefs()
        HunterGamingButton(
            modifier = Modifier
                .constrainAs(back) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
                .padding(
                    start = dimensionResource(R.dimen.padding_large),
                    bottom = dimensionResource(R.dimen.padding_large)
                )
                .zIndex(
                    zIndex = 1f
                ),
            onClick = { navController.navigate(ComposableRoutes.MAIN_MENU_NAV.route) },
            text = R.string.button_back
        )
    }
}

@Composable
private fun SoundSettings() {
    val onOff = listOf(
        stringResource(R.string.on),
        stringResource(R.string.off)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_large)
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HunterGamingTitleText(
                text = R.string.audio,
                modifier = Modifier
                    .weight(1f)
            )

            HunterGamingHorizontalRadioButton(
                texts = onOff,
                onSelect = { },
                selectedIndex = 0
            )

            HunterGamingHorizontalSlider(
                modifier = Modifier
                    .requiredWidth(dimensionResource(R.dimen.width_300))
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                initialValue = .8f,
                onValueChange = { },
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_large)
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HunterGamingTitleText(
                text = R.string.sfx,
                modifier = Modifier
                    .weight(1f)
            )

            HunterGamingHorizontalRadioButton(
                texts = onOff,
                onSelect = { },
                selectedIndex = 0
            )

            HunterGamingHorizontalSlider(
                modifier = Modifier
                    .requiredWidth(dimensionResource(R.dimen.width_300))
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                initialValue = .8f,
                onValueChange = { },
            )
        }
    }
}

@Composable
private fun GameSettings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = dimensionResource(R.dimen.padding_medium),
                    start = dimensionResource(R.dimen.padding_large)
                )
        ) {
            HunterGamingTitleText(
                text = R.string.number_of_decks
            )

            val numberOfDecksValues = listOf(
                stringResource(R.string.one),
                stringResource(R.string.two),
                stringResource(R.string.three)
            )

            HunterGamingHorizontalRadioButton(
                texts = numberOfDecksValues,
                onSelect = { },
                selectedIndex = 0
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    start = dimensionResource(R.dimen.padding_large)
                )
        ) {
            HunterGamingTitleText(
                text = R.string.deck_background
            )

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
private fun PrivacySettings() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(
                start = dimensionResource(R.dimen.padding_large),
                end = dimensionResource(R.dimen.padding_large)
            ),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        val yesNo = listOf(
            stringResource(R.string.yes),
            stringResource(R.string.no)
        )

        HunterGamingTitleText(
            text = R.string.data_consent
        )

        HunterGamingHorizontalRadioButton(
            texts = yesNo,
            onSelect = { },
            selectedIndex = 1
        )
    }
}