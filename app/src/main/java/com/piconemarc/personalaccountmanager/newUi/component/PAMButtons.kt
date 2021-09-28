package com.piconemarc.personalaccountmanager.newUi.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.ButtonShape

@Composable
fun PAMBaseButton(
    text: String,
    onButtonClicked: () -> Unit
) {
    Button(
        onClick = onButtonClicked,
        shape = ButtonShape,
        modifier = Modifier
            .width(120.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.paddingFromBaseline(bottom = BigMarge)
        )
    }
}

@Composable
fun PAMAcceptOrDismissButtons(
    onAcceptButtonClicked: () -> Unit,
    onDismissButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary, MaterialTheme.shapes.large)
            .fillMaxWidth()
            .padding(start = BigMarge, end = BigMarge, top = BigMarge),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PAMBaseButton(text = stringResource(R.string.ok)) { onAcceptButtonClicked() }
        PAMBaseButton(text = stringResource(R.string.cancel)) { onDismissButtonClicked() }
    }
}