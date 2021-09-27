package com.piconemarc.personalaccountmanager.ui.baseComponent.popUp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.baseComponent.popUp.animation.amountTextFieldAnimation
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.UiState
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.events.PopUpTitleStates
import com.piconemarc.personalaccountmanager.ui.baseComponent.stateManager.states.UiStates
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
        shape = MaterialTheme.shapes.large.copy(topStart = CornerSize(0.dp)),
        border = BorderStroke(LittleMarge, MaterialTheme.colors.primaryVariant),
    ) {
        Column{
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
    selectedItem : String,
    itemList: List<String>,
    onItemSelected: (item: String) -> Unit
) {
    var expanded: Boolean by remember {
        mutableStateOf(false)
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
fun BaseDropDownMenuWithBackground(
    selectedItem : String,
    itemList: List<String>,
    onItemSelected: (item: String) -> Unit
) {
    Row(
        modifier = Modifier.popUpClickableItemModifier(),
        horizontalArrangement = Arrangement.Center
    ) {
        BaseDropDownMenu(
            selectedItem = selectedItem,
            itemList = itemList,
            onItemSelected = onItemSelected
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
    textValue :String,
    addOperationPopUpState: UiStates.AddOperationPopUpState
) {
    val focusManager = LocalFocusManager.current
    if (addOperationPopUpState== UiStates.AddOperationPopUpState.COLLAPSED)focusManager.clearFocus()
    Column(modifier = Modifier.popUpClickableItemModifier()) {
        TextField(
            value = textValue,
            onValueChange =  onTextChange,
            textStyle = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary
            ),
            label = { Text(text = title) },
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colors.onPrimary,
                unfocusedLabelColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary,
                backgroundColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions (onNext = { focusManager.moveFocus(FocusDirection.Down)
            } )
        )

    }
}


@Composable
fun BasePopUpAmountTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
    amountValue : String,
    addOperationPopUpState: UiStates.AddOperationPopUpState
) {
    val focusManager = LocalFocusManager.current
    val transition = amountTextFieldAnimation(amountValue)
    if (addOperationPopUpState== UiStates.AddOperationPopUpState.COLLAPSED)focusManager.clearFocus()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = RegularMarge, bottom = RegularMarge, end = RegularMarge)
            .background(
                color = transition.backgroundColor,
                shape = PopUpFieldBackgroundShape
            )
    ) {
        TextField(
            value = amountValue,
            onValueChange = onTextChange,
            textStyle = MaterialTheme.typography.body1,
            label = { Text(text = title) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = transition.textColor,
                unfocusedLabelColor = transition.textColor,
                backgroundColor = Color.Transparent,
                textColor = transition.textColor
            ),
            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
        )
    }
}


@Composable
fun RecurrentOptionPanel(
    modifier: Modifier,
    onMonthSelected: (month: String) -> Unit,
    onYearSelected: (year: String) -> Unit,
    selectedMonth : String,
    selectedYear : String
) {
    Column(
        modifier = modifier
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
                .padding(horizontal = BigMarge),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BaseDropDownMenu(
                selectedItem = selectedMonth,
                itemList = mutableListOf("Janvier", "Fevrier"),
                onItemSelected = { month -> onMonthSelected(month) })
            BaseDropDownMenu(
                selectedItem = selectedYear,
                itemList = mutableListOf("2000", "2001"),
                onItemSelected = { year -> onYearSelected(year) })
        }
    }
}


@Composable
fun PopUpTitle(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary, shape = MaterialTheme.shapes.large)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = RegularMarge),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h2
        )
    }
}