package com.piconemarc.personalaccountmanager.ui.baseComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.piconemarc.personalaccountmanager.R
import com.piconemarc.personalaccountmanager.ui.theme.LittleMarge
import com.piconemarc.personalaccountmanager.ui.theme.RegularMarge
import com.piconemarc.personalaccountmanager.ui.theme.ThinBorder
import com.piconemarc.personalaccountmanager.ui.theme.ThinMarge

@Composable
fun BasePopUp(
    title: String,
    onAcceptButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    body: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(color = MaterialTheme.colors.secondary, shape = MaterialTheme.shapes.large)
            .padding(ThinMarge)
    ) {
        PopUpTitle(title)
        body()
        AcceptOrDismissButtons(
            onAcceptButtonClicked = {
                onAcceptButtonClicked()
            },
            onDismissButtonClicked = onCancelButtonClicked
        )
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
            modifier = Modifier.padding(start = RegularMarge, end = RegularMarge)
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
                }) {
                    Text(text = item)
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
    modifier: Modifier,
    textColor: Color = MaterialTheme.colors.onPrimary
) {
    Text(text = title)
    Column(modifier = modifier) {
        TextField(
            value = title,
            onValueChange = { text ->
                onTextChange(text)
            },
            textStyle = TextStyle(color = textColor),
        )
    }
}