package com.huntergaming.classicsolitaire.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme

@Composable
fun ClassicSolitaireTitle(
    modifier: Modifier = Modifier,
    scale: Float = 1f
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .scale(scale = scale)
    ) {

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.cc),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.l),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.a),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.s),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.s),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.i),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.c),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
        }

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.ss),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.o),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.l),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.i),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.t),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.a),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.i),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.r),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
            Image(
                painter = painterResource(id = R.drawable.e),
                contentDescription = "@null",
                modifier = Modifier.height(dimensionResource(id = R.dimen.letter_height))
            )
        }
    }
}

// PREVIEWS

@Preview(showBackground = true, widthDp = 1280, heightDp = 720)
@Composable
private fun DefaultPreview() {
    ClassicSolitaireTheme {
        ClassicSolitaireTitle()
    }
}

@Preview(showBackground = true, widthDp = 1920, heightDp = 1080)
@Composable
private fun DefaultPreview2() {
    ClassicSolitaireTheme {
        ClassicSolitaireTitle()
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 480)
@Composable
private fun DefaultPreview3() {
    ClassicSolitaireTheme {
        ClassicSolitaireTitle()
    }
}

@Preview(showBackground = true, widthDp = 854, heightDp = 480)
@Composable
private fun DefaultPreview4() {
    ClassicSolitaireTheme {
        ClassicSolitaireTitle()
    }
}