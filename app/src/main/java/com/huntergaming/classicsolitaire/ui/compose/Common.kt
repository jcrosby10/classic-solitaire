package com.huntergaming.classicsolitaire.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.huntergaming.classicsolitaire.R

@Composable
internal fun FieldRow(
    textResourceId: Int,
    hintResourceId: Int,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.edge_padding_10dp))
        .padding(start = dimensionResource(id = R.dimen.edge_padding_5dp)),
    hideText: Boolean = false,
    isError: Boolean = false,
    onValueChanged: (TextFieldValue) -> Unit,
    textState: MutableState<TextFieldValue>
) {
    Row(modifier = modifier) {

        Text(
            text = stringResource(id = textResourceId),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.edge_padding_5dp), top = dimensionResource(id = R.dimen.edge_padding_15dp))
                .defaultMinSize(minWidth = 70.dp),
            textAlign = TextAlign.Center
        )

        TextField(
            value = textState.value,
            onValueChange = onValueChanged,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.background,
                errorIndicatorColor = MaterialTheme.colors.error
            ),
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.edge_padding_5dp)),
            placeholder = {
                Text(
                    text = stringResource(id = hintResourceId),
                    style = MaterialTheme.typography.body1
                )
            },

            visualTransformation = if (hideText) PasswordVisualTransformation() else VisualTransformation.None,
            isError = isError
        )
    }
}