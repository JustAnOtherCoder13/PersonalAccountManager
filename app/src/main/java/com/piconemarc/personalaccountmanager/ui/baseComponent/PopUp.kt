package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.Positive
import com.piconemarc.personalaccountmanager.ui.theme.deleteOperationTextModifier
import com.piconemarc.personalaccountmanager.ui.theme.popUpClickableItemModifier


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

@Composable
fun AddOperationPopUp(
    showAddOperationPopUp: Boolean,
    onAddOperation: () -> Unit,
    onDismiss: () -> Unit
) {
    val operationTitle = stringResource(id = R.string.operation)
    var popUpTitle: String by remember { mutableStateOf(operationTitle) }


    if (showAddOperationPopUp)
        Row {
            OperationPopUpLeftSideIcon(
                onIconButtonClicked = { popUpTitle_ ->
                    popUpTitle = popUpTitle_
                }
            )
            BasePopUp(
                title = popUpTitle,
                onAcceptButtonClicked = {
                    onDismiss()
                    onAddOperation()
                },
                onCancelButtonClicked = onDismiss
            ) {
                Row(
                    modifier = Modifier.popUpClickableItemModifier(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BaseDropDownMenu(
                        hint = stringResource(R.string.category),
                        itemList = testList,
                        onItemSelected = { item ->

                        }
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasePopUpTextFieldItem(
                        title = stringResource(R.string.operationName),
                        onTextChange = { operationName ->

                        }
                    )
                    BasePopUpAmountTextFieldItem(
                        title = stringResource(R.string.operationAmount),
                        onTextChange = { amount ->

                        },
                    )

                    if (popUpTitle != stringResource(R.string.operation)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                        ) {
                            //todo switch between punctual and recurent
                        }
                        Text(text = "test")
                    }
                }
            }
        }
}

@Preview
@Composable
fun PopUpPreview() {
    Column {
        DeleteOperationPopUp(
            onDeleteOperation = { },
            onDismiss = {},
            showDeleteOperationPopUp = true,
            operationName = stringResource(R.string.operationName),
            operationAmount = 100.00
        )
        Spacer(modifier = Modifier.height(10.dp))
        AddOperationPopUp(
            showAddOperationPopUp = true,
            onDismiss = {},
            onAddOperation = {}
        )
    }
}