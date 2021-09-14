package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R


@Composable
private fun PopUpTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black, shape = RoundedCornerShape(20.dp))
    ) {
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BasePopUp(
    title: String,
    onAcceptButtonClicked: () -> Unit,
    onDismissButtonClicked: () -> Unit,
    showPopUp: Boolean,
    body: @Composable () -> Unit,
) {
    if (showPopUp)
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .padding(2.dp)
        ) {
            PopUpTitle(title)
            body()
            AcceptOrDismissButtons(
                onAcceptButtonClicked = onAcceptButtonClicked,
                onDismissButtonClicked = onDismissButtonClicked
            )
        }
}

@Composable
fun BaseDeletePopUp(
    elementToDelete: String,
    onAcceptButtonClicked: () -> Unit,
    onDismissButtonClicked: () -> Unit,
    showPopUp: Boolean,
    body: @Composable () -> Unit
) {
    BasePopUp(
        title = stringResource(R.string.deleteBaseMsg) + elementToDelete ,
        onAcceptButtonClicked = onAcceptButtonClicked,
        onDismissButtonClicked = onDismissButtonClicked,
        showPopUp = showPopUp,
        body = body
    )
}

private val deleteOperationTextModifier = Modifier.padding(horizontal = 0.dp, vertical = 10.dp)

@Composable
fun DeleteOperationPopUp(
    onAcceptButtonClicked: () -> Unit,
    onDismissButtonClicked: () -> Unit,
    showDeleteOperationPopUp: Boolean,
    operationName: String,
    operationAmount: Double
) {
    BaseDeletePopUp(
        elementToDelete = stringResource(R.string.operation),
        onAcceptButtonClicked = onAcceptButtonClicked,
        onDismissButtonClicked = onDismissButtonClicked,
        showPopUp = showDeleteOperationPopUp,
        body = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = operationName,
                    modifier = deleteOperationTextModifier
                )
                Text(
                    modifier = deleteOperationTextModifier,
                    text = operationAmount.toString(),
                    color = if (operationAmount < 0) Color.Red else Color.Green
                )
            }
        }
    )

}

@Preview
@Composable
fun PopUpPreview() {
    DeleteOperationPopUp(
        onAcceptButtonClicked = {},
        onDismissButtonClicked = {},
        showDeleteOperationPopUp = true,
        operationName = "test operation",
        operationAmount = -250.55
    )
}