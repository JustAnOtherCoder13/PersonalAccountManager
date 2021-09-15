package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R


@Composable
fun DeleteOperationPopUp(
    onAcceptButtonClicked: () -> Unit,
    showDeleteOperationPopUp: Boolean,
    operationName: String,
    operationAmount: Double
) {
    BaseDeletePopUp(
        elementToDelete = stringResource(R.string.operation),
        onAcceptButtonClicked = onAcceptButtonClicked,
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

@Composable
fun AddOperationPopUp(
    showAddOperationPopUp: Boolean,
) {
    var popUpTitle: String by remember { mutableStateOf("Operation") }

    Row {
        OperationPopUpLeftSideIcon(
            onIconButtonClicked = { popUpTitle_ ->
                popUpTitle = popUpTitle_
            }
        )
        BasePopUp(
            title = popUpTitle,
            onAcceptButtonClicked = {
                //todo send addOperationEvent
            },
            showPopUp_ = showAddOperationPopUp
        ) {
            Row(
                modifier = Modifier.popUpClickableItemModifier(),
                horizontalArrangement = Arrangement.Center
            ) {
                BaseDropDownMenu(
                    hint = "Category",
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
                    title = "Operation name",
                    onTextChange = {
                        //todo consume text
                    },
                    modifier = Modifier.popUpClickableItemModifier()
                )
                var operationAmount: Double by remember {
                    mutableStateOf(0.00)
                }
                BasePopUpTextFieldItem(
                    title = "Operation Amount",
                    onTextChange = { amount ->
                        operationAmount = amount.toDouble()
                    },
                    modifier = Modifier.popUpClickableItemModifier(
                        isAmount = true,
                        amount = operationAmount
                    ),
                    textColor = Color.Black
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
            operationName = "operation name",
            operationAmount = 100.00
        )
        Spacer(modifier = Modifier.height(10.dp))
        AddOperationPopUp(
            showAddOperationPopUp = true
        )
    }
}