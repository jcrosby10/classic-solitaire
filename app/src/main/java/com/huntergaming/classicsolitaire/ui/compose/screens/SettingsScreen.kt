package com.huntergaming.classicsolitaire.ui.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.huntergaming.classicsolitaire.ComposableRoutes
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.composables.HunterGamingButton
import com.huntergaming.composables.HunterGamingColumn
import com.huntergaming.composables.HunterGamingHorizontalImageRadioButton
import com.huntergaming.composables.HunterGamingHorizontalRadioButton
import com.huntergaming.composables.HunterGamingHorizontalSlider
import com.huntergaming.composables.HunterGamingRow
import com.huntergaming.composables.HunterGamingTitleText

@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 720, heightDp = 360)
@Composable
private fun DefaultPreviewSettingsScreen() {
    ClassicSolitaireTheme {
        SettingsScreen(rememberNavController())
    }
}

@Composable
internal fun SettingsScreen(navController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
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
                ),
            onClick = { navController.navigate(ComposableRoutes.MAIN_MENU_NAV.route) },
            text = R.string.button_back
        )
    }

    HunterGamingColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val onOff = listOf(
            stringResource(R.string.on),
            stringResource(R.string.off)
        )

        HunterGamingRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
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

        HunterGamingRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
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

        HunterGamingRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_medium),
                    bottom = dimensionResource(R.dimen.padding_medium)
                ),
            horizontalArrangement = Arrangement.SpaceAround
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

        HunterGamingRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.SpaceAround
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
                contentDescriptions = stringArrayResource(R.array.content_descriptions_card_backs).toList(),
                onSelect = { },
                selectedIndex = 0
            )
        }
    }
}