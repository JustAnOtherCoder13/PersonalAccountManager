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
    onAcceptButtonClicked: () -> Unit,
    showDeleteOperationPopUp: Boolean,
    operationName: String,
    operationAmount: Double
) {
    var showPopUp: Boolean by remember {
        mutableStateOf(showDeleteOperationPopUp)
    }
    if (showPopUp)
        BaseDeletePopUp(
            elementToDelete = stringResource(R.string.operation),
            onAcceptButtonClicked = {
                showPopUp = false
                onAcceptButtonClicked()
            },
            onCancelButtonClicked = {
                showPopUp = false
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
                    //todo send addOperationEvent
                },
                onCancelButtonClicked = {
                    onDismiss()
                }
            ) {
                Row(
                    modifier = Modifier.popUpClickableItemModifier(popUpTextFieldColorSelector()),
                    horizontalArrangement = Arrangement.Center
                ) {
                    BaseDropDownMenu(
                        hint = stringResource(R.string.category),
                        itemList = testList,
                        onItemSelected = { item ->
                            //todo consume item
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BasePopUpTextFieldItem(
                        title = stringResource(R.string.operationName),
                        onTextChange = {
                            //todo consume text
                        },
                        modifier = Modifier.popUpClickableItemModifier(popUpTextFieldColorSelector())
                    )
                    var operationAmount: Double by remember {
                        mutableStateOf(0.00)
                    }
                    BasePopUpTextFieldItem(
                        title = stringResource(R.string.operationAmount),
                        onTextChange = { amount ->
                            operationAmount = amount.toDouble()
                        },
                        modifier = Modifier.popUpClickableItemModifier(
                            popUpTextFieldColorSelector(
                                isAmount = true,
                                amount = operationAmount
                            )

                        ),
                        textColor = MaterialTheme.colors.primary
                    )
                }
            }
        }
}

@Preview
@Composable
fun PopUpPreview() {
    Column {
        DeleteOperationPopUp(
            onAcceptButtonClicked = { },
            showDeleteOperationPopUp = true,
            operationName = stringResource(R.string.operationName),
            operationAmount = 100.00
        )
        Spacer(modifier = Modifier.height(10.dp))
        AddOperationPopUp(
            showAddOperationPopUp = true,
            onDismiss = {}
        )
    }
}