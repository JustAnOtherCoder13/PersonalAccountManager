package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
        //Left menu-----------------------------------------------------
        Row {
            OperationPopUpLeftSideIcon(
                onIconButtonClicked = { popUpTitle_ ->
                    popUpTitle = popUpTitle_
                }
            )
            //base operation --------------------------------------------
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
                    //category drop down -----------------------------------
                    BaseDropDownMenu(
                        hint = stringResource(R.string.category),
                        itemList = testList,
                        onItemSelected = { item ->

                        }
                    )
                }
                // operation and amount text field--------------------------
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
                    //Payment Operation-------------------------------------
                    if (popUpTitle != stringResource(R.string.operation)) {
                        PunctualOrRecurrentSwitchButton(
                            onEndDateSelected = {month_year ->

                            }
                        )
                    }
                    //Transfer Operation------------------------------------
                    if (popUpTitle == stringResource(R.string.transfer)){
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
        AddOperationPopUp(
            showAddOperationPopUp = true,
            onDismiss = {},
            onAddOperation = {}
        )
    }
}