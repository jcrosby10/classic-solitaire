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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.huntergaming.classicsolitaire.R

@Composable
internal fun ClassicSolitaireButton(
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
        modifier = Modifier.padding(dimensionResource(id = R.dimen.edge_padding_5dp))
    )
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