package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.ui.theme.BigMarge
import com.piconemarc.personalaccountmanager.ui.theme.ButtonShape
import com.piconemarc.personalaccountmanager.ui.theme.PersonalAccountManagerTheme
import com.piconemarc.personalaccountmanager.ui.theme.XlMarge


@Composable
fun BaseButton(
    text: String,
    onButtonClicked: () -> Unit
) {
    Button(
        onClick = onButtonClicked,
        shape = ButtonShape,
        modifier = Modifier
            .width(130.dp),
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
fun AcceptOrDismissButtons(
    onAcceptButtonClicked: () -> Unit,
    onDismissButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary, MaterialTheme.shapes.large)
            .fillMaxWidth()
            .padding(start = XlMarge, end = XlMarge, top = BigMarge),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BaseButton(text = stringResource(android.R.string.ok)) { onAcceptButtonClicked() }
        BaseButton(text = stringResource(android.R.string.cancel).uppercase()) { onDismissButtonClicked() }
    }
}

@Preview
@Composable
fun AcceptOrDismissPreview() {
    PersonalAccountManagerTheme {
        AcceptOrDismissButtons(
            onAcceptButtonClicked = { },
            onDismissButtonClicked = { }
        )
    }
}


