package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.*

@Composable
fun BasePopUp(
    title: String,
    onAcceptButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    body: @Composable () -> Unit,
) {
    Card(
        elevation = BigMarge,
        backgroundColor = MaterialTheme.colors.secondary,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(LittleMarge, MaterialTheme.colors.primaryVariant),
    ) {
        Column() {
            PopUpTitle(title)
            body()
            AcceptOrDismissButtons(
                onAcceptButtonClicked = onAcceptButtonClicked,
                onDismissButtonClicked = onCancelButtonClicked
            )
        }
    }
}

@Composable
fun BaseDropDownMenu(
    hint: String,
    itemList: List<String>,
    onItemSelected: (item: String) -> Unit
) {
    var expanded: Boolean by remember {
        mutableStateOf(false)
    }
    var selectedItem: String by remember {
        mutableStateOf(hint)
    }
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(top = RegularMarge, bottom = RegularMarge)
            .border(
                width = ThinBorder,
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(RegularMarge)
            )
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = selectedItem,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(start = RegularMarge, end = RegularMarge),
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.width(LittleMarge))
        Icon(
            imageVector = if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
            contentDescription = stringResource(R.string.collapseIconContentDescription),
            tint = MaterialTheme.colors.onPrimary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize()

        ) {
            itemList.forEachIndexed { _, item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    selectedItem = item
                    expanded = false
                }) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Composable
fun BaseDropDownMenuWithBackGround(
    hint: String,
    itemList: List<String>,
    onItemSelected: (item: String) -> Unit
) {
    Row(
        modifier = Modifier.popUpClickableItemModifier(),
        horizontalArrangement = Arrangement.Center
    ) {
        BaseDropDownMenu(
            hint = hint,
            itemList = itemList,
            onItemSelected = onItemSelected
        )
    }
}


@Composable
fun OperationPopUpLeftSideIcon(
    onIconButtonClicked: (popUpTitle: String) -> Unit
) {
    val operation = stringResource(R.string.operation)
    val payment = stringResource(R.string.payment)
    val transfer = stringResource(R.string.transfer)

    var selectedOperationOption: String by remember {
        mutableStateOf(operation)
    }

    Box() {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(topStart = BigMarge, bottomStart = BigMarge)
                )
                .height(48.dp)
                .width(48.dp)
                .align(when (selectedOperationOption) {
                        payment -> Alignment.Center
                        transfer -> Alignment.BottomCenter
                        else -> Alignment.TopCenter
                    }
                ),


        )
        Column {
            PamIconButton(
                iconButton = IconButtons.OPERATION,
                onIconButtonClicked = {
                    selectedOperationOption = operation
                    onIconButtonClicked(operation)
                }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PamIconButton(
                iconButton = IconButtons.PAYMENT,
                onIconButtonClicked = {
                    selectedOperationOption = payment
                    onIconButtonClicked(payment)
                }
            )
            Spacer(modifier = Modifier.height(RegularMarge))
            PamIconButton(
                iconButton = IconButtons.TRANSFER,
                onIconButtonClicked = {
                    selectedOperationOption = transfer
                    onIconButtonClicked(transfer)
                }
            )
        }
    }
}

@Composable
fun BaseDeletePopUp(
    elementToDelete: String,
    onAcceptButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    body: @Composable () -> Unit
) {
    BasePopUp(
        title = stringResource(R.string.deleteBaseMsg) + elementToDelete,
        onAcceptButtonClicked = onAcceptButtonClicked,
        onCancelButtonClicked = onCancelButtonClicked,
        body = body
    )
}

@Composable
fun BasePopUpTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
) {
    var textValue: String by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.popUpClickableItemModifier()) {
        TextField(
            value = textValue,
            onValueChange = { text ->
                onTextChange(text)
                textValue = text
            },
            textStyle = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary
            ),
            label = { Text(text = title) },
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colors.onPrimary,
                unfocusedLabelColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Composable
fun BasePopUpAmountTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
) {
    var amountValue: String by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.popUpAmountItemModifier(amountValue)
    ) {
        TextField(
            value = amountValue,
            onValueChange = { amount ->
                amountValue = amount
                onTextChange(amountValue)
            },
            textStyle = MaterialTheme.typography.body1,
            label = { Text(text = title) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.primary,
                backgroundColor = Color.Transparent
            )
        )
    }
}


@Composable
fun RecurrentOptionPanel(
    onMonthSelected: (month: String) -> Unit,
    onYearSelected: (year: String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.primary,
                shape = RecurrentOptionPanelShape
            )
    ) {
        Text(
            text = stringResource(R.string.endDate),
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = RegularMarge),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BigMarge),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BaseDropDownMenu(
                hint = stringResource(R.string.month),
                itemList = mutableListOf("Janvier", "Fevrier"),
                onItemSelected = { month -> onMonthSelected(month) })
            BaseDropDownMenu(
                hint = stringResource(R.string.year),
                itemList = mutableListOf("2000", "2001"),
                onItemSelected = { year -> onYearSelected(year) })
        }
    }
}