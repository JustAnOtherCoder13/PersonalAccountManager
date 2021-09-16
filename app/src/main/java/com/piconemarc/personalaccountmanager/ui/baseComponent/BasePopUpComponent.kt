package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
        shape = MaterialTheme.shapes.large
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
fun OperationPopUpLeftSideIcon(
    onIconButtonClicked: (popUpTitle: String) -> Unit
) {
    val operation = stringResource(R.string.operation)
    val payment = stringResource(R.string.payment)
    val transfer = stringResource(R.string.transfer)
    Column {
        PamIconButton(
            iconButton = IconButtons.OPERATION,
            onIconButtonClicked = {
                onIconButtonClicked(operation)
            }
        )
        Spacer(modifier = Modifier.height(RegularMarge))
        PamIconButton(
            iconButton = IconButtons.PAYMENT,
            onIconButtonClicked = {
                onIconButtonClicked(payment)
            }
        )
        Spacer(modifier = Modifier.height(RegularMarge))
        PamIconButton(
            iconButton = IconButtons.TRANSFER,
            onIconButtonClicked = {
                onIconButtonClicked(transfer)
            }
        )
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
    Text(
        text = title,
        style = MaterialTheme.typography.h3
    )
    Column(modifier = Modifier.popUpClickableItemModifier()) {
        TextField(
            value = textValue,
            onValueChange = { text ->
                onTextChange(text)
                textValue = text
            },
            textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onPrimary),
            label = { Text(text = title) },
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
    Text(
        text = title,
        style = MaterialTheme.typography.h3
    )
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
            modifier = Modifier.background(Color.Transparent)
        )
    }
}

@Composable
fun SwitchButton(
    onButtonSelected: () -> Unit,
    isSelected: Boolean,
    title: String,
    switchShape: Shape,
    bottomPadding : Dp = RegularMarge
) {
    Button(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
                shape = switchShape
            )
            .padding(end = RegularMarge),
        onClick = onButtonSelected,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        elevation = ButtonDefaults.elevation(0.dp)

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(top = RegularMarge,bottom = bottomPadding, end = RegularMarge),
            color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
fun PunctualOrRecurrentSwitchButton(
    onEndDateSelected : (Pair<String,String>)-> Unit
) {
    var selectedButton: Int by remember {
        mutableStateOf(0)
    }
    var selectedDate : Pair<String, String> by remember {
        mutableStateOf(Pair("",""))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = RegularMarge)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = RegularMarge, bottom = if (selectedButton == 0) BigMarge else 0.dp)
        ) {
            SwitchButton(
                onButtonSelected = { selectedButton = 0 },
                isSelected = selectedButton == 0,
                title = stringResource(R.string.punctualSwitchButton),
                switchShape = LeftSwitchShape
            )
            SwitchButton(
                onButtonSelected = { selectedButton = 1 },
                isSelected = selectedButton == 1,
                title = stringResource(R.string.recurrentSwitchButton),
                switchShape = if (selectedButton == 1) RightSwitchShape.copy(
                    bottomStart = CornerSize(
                        0.dp
                    )
                ) else RightSwitchShape,
                bottomPadding = if (selectedButton == 1) BigMarge else RegularMarge
            )
        }
        //Recurrent options
        if (selectedButton == 1)
            RecurrentOptionPanel(
                onMonthSelected = {month->
                    selectedDate = selectedDate.copy(first = month)
                    onEndDateSelected(selectedDate)
                },
                onYearSelected = {year ->
                    selectedDate = selectedDate.copy(second = year)
                    onEndDateSelected(selectedDate)
                }
            )
    }

}
@Composable
private fun RecurrentOptionPanel(
    onMonthSelected : (month : String)->Unit,
    onYearSelected : (year : String)-> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.primary,
                shape = RecurrentOptionPanelShape
            )
    ) {
        Text(
            text = "End Date",
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
                onItemSelected = {month -> onMonthSelected(month)})
            BaseDropDownMenu(
                hint = stringResource(R.string.year),
                itemList = mutableListOf("2000", "2001"),
                onItemSelected = {year-> onYearSelected(year)})
        }
    }
}