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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.piconemarc.personalaccountmanager.R

@Composable
fun BasePopUp(
    title: String,
    onAcceptButtonClicked: () -> Unit,
    showPopUp_: Boolean,
    body: @Composable () -> Unit,
) {
    var showPopUp: Boolean by remember {
        mutableStateOf(showPopUp_)
    }
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
                onAcceptButtonClicked = {
                    onAcceptButtonClicked()
                    showPopUp = false
                },
                onDismissButtonClicked = { showPopUp = false }
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
            .padding(top = 10.dp, bottom = 10.dp)
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = selectedItem,
            color = Color.White,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            imageVector = if (expanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
            contentDescription = "Pop up expanded or collapsed icon",
            tint = Color.White
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
    Column {
        PamIconButton(
            iconButton = IconButtons.OPERATION,
            onIconButtonClicked = {
                onIconButtonClicked("Operation")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        PamIconButton(
            iconButton = IconButtons.PAYMENT,
            onIconButtonClicked = {
                onIconButtonClicked("Payment Operation")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        PamIconButton(
            iconButton = IconButtons.TRANSFER,
            onIconButtonClicked = {
                onIconButtonClicked("Transfer Operation")
            }
        )
    }
}

@Composable
fun BaseDeletePopUp(
    elementToDelete: String,
    onAcceptButtonClicked: () -> Unit,
    showPopUp: Boolean,
    body: @Composable () -> Unit
) {
    BasePopUp(
        title = stringResource(R.string.deleteBaseMsg) + elementToDelete,
        onAcceptButtonClicked = onAcceptButtonClicked,
        showPopUp_ = showPopUp,
        body = body
    )
}

@Composable
fun BasePopUpTextFieldItem(
    title: String,
    onTextChange: (text: String) -> Unit,
    modifier: Modifier,
    textColor: Color = Color.White
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