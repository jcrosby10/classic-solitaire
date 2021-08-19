package com.huntergaming.classicsolitaire.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreviewClassicSolitaireButton() {
    ClassicSolitaireTheme {
        ClassicSolitaireButton(onClick = {  }, text = R.string.button_quit)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewClassicSolitaireHeaderText() {
    ClassicSolitaireTheme {
        ClassicSolitaireHeaderText(modifier = Modifier, text = R.string.app_name)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewClassicSolitaireAlertDialog() {
    ClassicSolitaireTheme {
        ClassicSolitaireAlertDialog(confirmButton = {}, dismissButton = {}, title = @Composable {}, text = @Composable {})
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewClassicSolitaireCircularProgressIndicator() {
    ClassicSolitaireTheme {
        ClassicSolitaireCircularProgressIndicator()
    }
}

@Composable
internal fun ClassicSolitaireButton(
    modifier: Modifier = Modifier.padding(dimensionResource(id = R.dimen.edge_padding_5dp)),
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    text: Int
) {
    Button(
        enabled = isEnabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            disabledBackgroundColor = MaterialTheme.colors.error,
            disabledContentColor = MaterialTheme.colors.onError
        ),
        content = {
            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.button,
            )
        },
        modifier = modifier
    )
}

@Composable
internal fun ClassicSolitaireBodyText() {

}

@Composable
internal fun ClassicSolitaireHeaderText(modifier: Modifier, text: Int) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.edge_padding_5dp))
            .composed { modifier },
        textAlign = TextAlign.Center
    )
}

@Composable
internal fun ClassicSolitaireTitleText() {

}

@Composable
internal fun ClassicSolitaireAlertDialog(
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit),
    text: @Composable (() -> Unit)
) {
    AlertDialog(
        onDismissRequest = {  },
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        title = title,
        text = text,
        backgroundColor = MaterialTheme.colors.primaryVariant
    )
}

@Composable
internal fun ClassicSolitaireCircularProgressIndicator() {
    CircularProgressIndicator(
        color = MaterialTheme.colors.secondary
    )
}