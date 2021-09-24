package com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.deletePopUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.BaseDeletePopUp
import com.piconemarc.personalaccountmanager.ui.theme.Positive
import com.piconemarc.personalaccountmanager.ui.theme.deleteOperationTextModifier


@Composable
fun DeleteOperationPopUp(
    showDeleteOperationPopUp: Boolean,
    operationName: String,
    operationAmount: Double,
    onDeleteOperation: () -> Unit,
    onDismiss: () -> Unit
) {

    if (showDeleteOperationPopUp)
        BaseDeletePopUp(
            elementToDelete = stringResource(R.string.operation),
            onAcceptButtonClicked = {
                onDismiss()
                onDeleteOperation()
            },
            onCancelButtonClicked = {
                onDismiss()
            },
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = operationName,
                        modifier = Modifier.deleteOperationTextModifier()
                    )
                    Text(
                        modifier = Modifier.deleteOperationTextModifier(),
                        text = operationAmount.toString(),
                        color = if (operationAmount < 0) MaterialTheme.colors.error else Positive
                    )
                }
            }
        )

}