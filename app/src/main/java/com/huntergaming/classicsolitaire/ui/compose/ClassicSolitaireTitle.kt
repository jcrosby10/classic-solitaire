package com.huntergaming.classicsolitaire.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.ui.composable.HunterGamingColumn
import com.huntergaming.ui.composable.HunterGamingRow

@Composable
fun ClassicSolitaireTitle() {
    HunterGamingColumn {

        HunterGamingRow {
            Image(painter = painterResource(id = R.drawable.c_c), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.l), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.a), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.s), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.s), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.i), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.c), contentDescription = "")
        }

        HunterGamingRow {
            Image(painter = painterResource(id = R.drawable.s_s), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.o), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.l), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.i), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.t), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.a), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.i), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.r), contentDescription = "")
            Image(painter = painterResource(id = R.drawable.e), contentDescription = "")
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